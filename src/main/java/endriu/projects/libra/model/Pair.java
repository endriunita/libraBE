package endriu.projects.libra.model;

import javax.persistence.*;

@Entity
@Table(name="pairs")
public class Pair {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstuser;
    private String seconduser;
    private String topic;

    public Pair() {}

    public Pair(String a, String b, String topic){
        this.firstuser = a;
        this.seconduser = b;
        this.topic = topic;
    }

    public Pair(int id, String a, String b, String topic){
        this.id = id;
        this.firstuser = a;
        this.seconduser = b;
        this.topic = topic;
    }

    public int getId() {
        return this.id;
    }

    public boolean containsUser(String user){
        return user.equals(this.firstuser) || user.equals(this.seconduser);
    }

    public String getFirstuser() {
        return firstuser;
    }

    public String getSeconduser() {
        return seconduser;
    }

    public String getTopic() {
        return topic;
    }

    public String getPair(String username){
        if (username.equals(firstuser))
            return this.seconduser;
        else return this.firstuser;
    }

    public String toString(){
        return " first user: " + firstuser + " second user: " + seconduser + " topic:" + topic + " id: " + id;
    }
}
