package vrp.service;

import vrp.dto.ModuleEventLogDTO;
import java.util.List;

public interface ModuleEventLogService {
    void saveLog(ModuleEventLogDTO moduleEventLogDTO);
    List<ModuleEventLogDTO> fetchAllLogsDTOByProjectName(String projectName);
}
