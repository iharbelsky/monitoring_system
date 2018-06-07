package vrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vrp.domain.Project;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Optional<Project> findByNameProject(String nameProject);

}
