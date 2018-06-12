package vrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vrp.dto.ModuleEventLogDTO;
import vrp.dto.StatusOperation;
import vrp.exception.PreconditionFailed;
import vrp.service.ModuleEventLogService;

@RestController
@RequestMapping(value="/module_event")
public class ModuleEventLogController {

    private final ModuleEventLogService moduleEventLogService;

    @Autowired
    public ModuleEventLogController(final ModuleEventLogService moduleEventLogService) {
        this.moduleEventLogService = moduleEventLogService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public StatusOperation saveLog(@RequestBody final ModuleEventLogDTO moduleEventLogDTO) {
        try {
            moduleEventLogService.saveLog(moduleEventLogDTO);
        } catch (Exception e) {
            throw new PreconditionFailed(e.getMessage());
        }
        return new StatusOperation("Completed successfully");
    }
}
