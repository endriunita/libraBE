package endriu.projects.libra.services;

import endriu.projects.libra.dao.TopicRepository;
import endriu.projects.libra.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicsService {

    @Autowired
    TopicRepository topicRepository;

    public void addTopic(int uid, String topic){
        this.topicRepository.save(new Topic(uid, topic));
    }

    public Topic getMatch(int id, String topic){
        return this.topicRepository.getByTopicnameAndUseridIsNot(topic, id);
    }

    public boolean exists(int userid, String topic){
        return this.topicRepository.existsByUseridAndTopicname(userid, topic);
    }

    public void delete(Topic target){
        this.topicRepository.delete(target);
    }

    public void delete(int id){
        this.topicRepository.deleteById(id);
    }

}
