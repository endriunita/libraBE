package endriu.projects.libra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="topics")
public class Topic {

    @Id
    private int userid;
    private String topicname;

    public Topic(int id, String topic){
        this.userid = id;
        this.topicname = topic;
    }

    public Topic(){}

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }
}
