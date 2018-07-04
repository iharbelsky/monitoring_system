package vrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vrp.domain.ModuleEventLog;
import java.util.List;

public interface ModuleEventLogRepository extends JpaRepository<ModuleEventLog, Long> {

    @Query( "SELECT mel FROM ModuleEventLog mel " +
            "INNER JOIN mel.module module " +
            "INNER JOIN module.project project WHERE project.projectName =:projectName")
    List<ModuleEventLog> findAllByProjectName(@Param("projectName") String projectName);
}
