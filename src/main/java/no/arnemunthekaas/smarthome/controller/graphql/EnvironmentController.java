package no.arnemunthekaas.smarthome.controller.graphql;

import lombok.extern.log4j.Log4j2;
import no.arnemunthekaas.smarthome.db.EnvironmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@Log4j2
public class EnvironmentController {

    @Autowired
    private EnvironmentDAO environmentDAO;
}
