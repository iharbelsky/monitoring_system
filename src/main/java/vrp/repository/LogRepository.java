package vrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vrp.domain.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

}
