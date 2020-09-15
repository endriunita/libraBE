package endriu.projects.libra.controllers;


import endriu.projects.libra.model.Preference;
import endriu.projects.libra.model.Requests.AddPrefRequest;
import endriu.projects.libra.model.Requests.DeletePrefRequest;
import endriu.projects.libra.model.Responses.*;
import endriu.projects.libra.model.Validator;
import endriu.projects.libra.services.MyUserDetailsService;
import endriu.projects.libra.services.PreferencesService;
import endriu.projects.libra.util.JwtUtil;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preferences")
public class PreferenceController {

    @Autowired
    private PreferencesService preferencesService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public JSONArray getPreferences(@RequestHeader("Authorization")  String auth){
        int userid = userDetailsService.getID(jwtUtil.extractUsername(auth.substring(7)));
        // get id of user passed through jwt

        List<Preference> preferenceList = preferencesService.getAll(userid);
        JSONArray result = new JSONArray();
        result.addAll(preferenceList);
        // prepare list of preferences and send

        return result;
    }

    @RequestMapping(value = "/addpref", method = RequestMethod.POST)
    public PreferenceAddedResponse addPreference(@RequestHeader("Authorization")  String auth, @RequestBody AddPrefRequest addPrefRequest) throws Exception {
        int userid = userDetailsService.getID(jwtUtil.extractUsername(auth.substring(7)));
        // id of user who sent the request
        if(this.preferencesService.exists(userid, addPrefRequest.getPreference())){
            throw new Exception("Preference is already stored");
        }

        if (Validator.checkLegal(addPrefRequest.getPreference())){
            throw new Exception("Preference title is not valid");
        }

        this.preferencesService.addPreference(userid, addPrefRequest.getPreference());
        return new PreferenceAddedResponse("Preference added successfully");
    }

    @RequestMapping(value = "/getrand", method = RequestMethod.GET)
    public PreferenceResponse getRandomPreference(@RequestHeader("Authorization") String auth) throws Exception {
        int userid = userDetailsService.getID(jwtUtil.extractUsername(auth.substring(7)));

        Preference result = this.preferencesService.getRandomPreference(userid);
        if (result == null){
            throw new Exception("You have no preferences");
        }

        return new RandomPreferenceResponse(result.getPreference());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public PreferenceResponse deletePreference(@RequestHeader("Authorization") String auth, @RequestBody DeletePrefRequest deletePrefRequest){
        System.out.println("Got delete request from some user to delete" + deletePrefRequest.getPreference());
        int userid = userDetailsService.getID(jwtUtil.extractUsername(auth.substring(7)));

        this.preferencesService.deletePreference(userid, deletePrefRequest.getPreference());
        return new DeletePrefResponse("Preference deleted successfully");
    }
}
