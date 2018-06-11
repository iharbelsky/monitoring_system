package vrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vrp.domain.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
}
