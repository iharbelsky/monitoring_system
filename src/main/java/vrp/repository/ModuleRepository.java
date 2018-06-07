package vrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vrp.domain.Module;
import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByProjectId(Long Id);

}
