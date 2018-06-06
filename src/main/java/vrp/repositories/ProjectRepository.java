package vrp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vrp.domain.Project;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    Optional<Project> findByNameProject(String nameProject);


}
