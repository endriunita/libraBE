package endriu.projects.libra.controllers;

import endriu.projects.libra.dao.MatchQueue;
import endriu.projects.libra.model.Pair;
import endriu.projects.libra.model.Requests.MatchRequest;
import endriu.projects.libra.model.Responses.MatchResponse;
import endriu.projects.libra.model.Topic;
import endriu.projects.libra.services.MyUserDetailsService;
import endriu.projects.libra.services.PairsService;
import endriu.projects.libra.services.TopicsService;
import endriu.projects.libra.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins="*")
public class MatchController {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TopicsService topicsService;

    @Autowired
    private PairsService pairsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping(value = "/match", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> hello(@RequestHeader("Authorization")  String auth, @RequestBody MatchRequest matchRequest) {
        // check if sender matches the jwt

        if(!matchRequest.getUsername().equals(jwtTokenUtil.extractUsername(auth.substring(7)))){
            return new ResponseEntity<>(
                    "Username does not match Jwt",
                    HttpStatus.BAD_REQUEST);
        }

        // get id of sender
        int currentuserid = this.userDetailsService.getID(matchRequest.getUsername());
        String topic = matchRequest.getTopic();
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        String currentuser = matchRequest.getUsername();

        // we keep searching for 2 minutes for matches
        // if there are no matches found, after the elapsed
        // time a response will be sent to the user informing him that no pairs were found
        while (elapsedTime < 2*60*1000){
            elapsedTime = (new Date().getTime() - startTime);
            // get the elapsed time

           // if (MatchQueue.isInQueue(currentuser)){
            if (this.pairsService.pairExists(currentuser)){
                // if the user is in the queue, it means that someone already found him in the database
                // and added him to the queue, so we need to delete the queue entry and return the pair along with the topic of discussion

                Pair pair = this.pairsService.getPair(currentuser);
                String paireduser = pair.getPair(currentuser);
                int deletedid = pair.getId();

                this.pairsService.delete(deletedid);

                return ResponseEntity.ok(new MatchResponse(paireduser, topic, pair.getId()));
            }
            else {
                // the user is not in the queue, so we check the database for pairs
                // if no pairs are found, register user in db and keep waiting

                Topic result = this.topicsService.getMatch(currentuserid, topic);
                if( result != null ){
                    // if there is a potential match in the database, delete that entry and add it
                    // along with the current user to the queue so the other user gets their pair as well
                    this.topicsService.delete(result);

                    this.pairsService.addPair(new Pair(currentuser, this.userDetailsService.getUserById(result.getUserid()), result.getTopicname()));

                    int roomid = this.pairsService.getPair(currentuser).getId();

                    // also, return the pair to the current user
                    return ResponseEntity.ok(new MatchResponse(this.userDetailsService.getUserById(result.getUserid()), topic, roomid));
                }
                else{
                    // there is no match in the database, so we insert the current user along with its topic
                    // in the table(if he is not already present) so another user can find him
                    if ( !(this.topicsService.exists(currentuserid, topic)) ){
                        // add the current user to the "waiting list"
                        this.topicsService.addTopic(currentuserid, topic);
                    }
                }
            }
        }

        // delete user from the topic queue if he didnt get any matches
        this.topicsService.delete(currentuserid);

        // time expired
        return new ResponseEntity<>(
                "Found no matches",
                HttpStatus.BAD_REQUEST
        );
    }


}
