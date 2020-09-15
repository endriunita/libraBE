package endriu.projects.libra.dao;

import endriu.projects.libra.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    Topic getByTopicnameAndUseridIsNot(String topicname, int id);

    boolean existsByUseridAndTopicname(int id, String topicname);
}
