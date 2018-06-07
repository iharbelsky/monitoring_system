package vrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vrp.domain.Module;
import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByProjectId(Long Id);

}
