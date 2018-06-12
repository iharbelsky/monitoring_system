package vrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vrp.domain.ModuleEventLog;

public interface ModuleEventLogRepository extends JpaRepository<ModuleEventLog, Long> {
}
