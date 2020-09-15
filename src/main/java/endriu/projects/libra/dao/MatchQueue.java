package endriu.projects.libra.dao;

import endriu.projects.libra.model.Pair;

import java.util.ArrayList;
import java.util.List;

public class MatchQueue {

    private static List<Pair> queue = new ArrayList<Pair>();

    public static void addToQueue(String user1, String user2, String topic){
        MatchQueue.queue.add(new Pair(user1, user2, topic));
    }

    public static boolean isInQueue(String user){
        for(Pair pair : MatchQueue.queue){
            if (pair.containsUser(user)){
                return true;
            }
        }
        return false;
    }

    public static String getPairName(String user){
        for(Pair pair : MatchQueue.queue){
            if (pair.containsUser(user)){
                return pair.getPair(user);
            }
        }
        return null;
    }

    public static void deletePair(String user){
        for(Pair pair : MatchQueue.queue){
            if (pair.containsUser(user)){
                queue.remove(pair);
            }
        }
    }
}
