package vrp.service;

import org.springframework.stereotype.Service;
import vrp.dto.LogDTO;


@Service
public interface LogService {

    void writeLog(LogDTO logDTO);

}
