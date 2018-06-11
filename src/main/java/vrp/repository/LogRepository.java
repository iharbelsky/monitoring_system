package vrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vrp.domain.Log;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByTextLog(String textLog);
}
