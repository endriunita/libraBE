package endriu.projects.libra.services;

import endriu.projects.libra.dao.PreferenceRepository;
import endriu.projects.libra.model.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PreferencesService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    public void addPreference(int uid, String preference){
        this.preferenceRepository.save(new Preference(uid, preference));
    }

    public Preference getRandomPreference(int uid){
        List<Preference> prefs = this.preferenceRepository.getAllByUserid(uid);

        if (prefs.size() == 0){
            return null;
        }
        //get random preference index from the preferences of the user
        int randomIndex = ThreadLocalRandom.current().nextInt(0, prefs.size());

        return prefs.get(randomIndex);
    }

    public boolean exists(int uid, String preference) throws Exception {

        return this.preferenceRepository.getByUseridAndPreference(uid, preference) != null;

    }

    public List<Preference> getAll(int uid){
        return this.preferenceRepository.getAllByUserid(uid);
    }

    public void deletePreference(int userid, String preference){
        Preference culprit = this.preferenceRepository.getByUseridAndPreference(userid, preference);

        this.preferenceRepository.deleteById(culprit.getUserprefid());
    }
}
