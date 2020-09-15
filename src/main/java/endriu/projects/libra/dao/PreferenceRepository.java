package endriu.projects.libra.dao;

import endriu.projects.libra.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Integer> {
    List<Preference> getAllByUserid(int id);

    Preference getByUseridAndPreference(int userid, String preference);
}
