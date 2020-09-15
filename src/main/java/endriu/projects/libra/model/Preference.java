package endriu.projects.libra.model;

import javax.persistence.*;

@Entity
@Table(name="userpreference")
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userprefid;
    private int userid;
    private String preference;

    public Preference() {}

    public Preference(int userid, String preference) {
        this.userid = userid;
        this.preference = preference;
    }

    public Preference(int userprefid, int userid, String preference){
        this.userprefid = userprefid;
        this.userid = userid;
        this.preference = preference;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public int getUserprefid() { return this.userprefid; }

    public void setUserprefid() { this.userprefid = userprefid; }
}
