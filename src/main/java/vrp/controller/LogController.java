package vrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vrp.dto.LogDTO;
import vrp.dto.StatusOperation;
import vrp.service.LogService;

@RestController
@RequestMapping("/log")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(final LogService logService) {
        this.logService = logService;
    }

    @RequestMapping(value = "/write"
                  , method = RequestMethod.POST)
    public StatusOperation createLog(@RequestBody final LogDTO logDTO){
        try {
            logService.writeLog(logDTO);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return new StatusOperation(200,"Completed successfully");
    }




}
