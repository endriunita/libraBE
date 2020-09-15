package endriu.projects.libra.dao;

import endriu.projects.libra.model.Pair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PairsRepository extends JpaRepository<Pair, Integer>
{

    boolean existsByFirstuserOrSeconduser(String firstuser, String seconduser);

    Pair findByFirstuserOrSeconduser(String firstuser, String seconduser);

    void deleteByFirstuserOrSeconduser(String firstuser, String seconduser);

    void deleteById(int id);

}
