package vrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vrp.domain.Project;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectName(String projectName);
    void deleteByProjectName(String projectName);

    @Modifying
    @Query("Update Project project set project.projectName = ?2, project.description = ?3 where project.id = ?1")
    void updateProjectById(Long id, String projectName, String description);
}
