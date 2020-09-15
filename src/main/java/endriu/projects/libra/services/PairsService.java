package endriu.projects.libra.services;

import endriu.projects.libra.dao.PairsRepository;
import endriu.projects.libra.model.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
public class PairsService {

    @Autowired
    private PairsRepository pairsRepository;

    public void addPair(Pair pair){
        pairsRepository.save(pair);
    }

    public boolean pairExists(String username){
        return pairsRepository.existsByFirstuserOrSeconduser(username, username);
    }

    public Pair getPair(String username){
        return pairsRepository.findByFirstuserOrSeconduser(username, username);
    }

    public void deletePair(String username){
        this.pairsRepository.deleteByFirstuserOrSeconduser(username, username);
    }

    public void delete(int id){
        this.pairsRepository.deleteById(id);
    }

}
