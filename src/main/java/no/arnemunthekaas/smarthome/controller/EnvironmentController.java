package no.arnemunthekaas.smarthome.controller;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import no.arnemunthekaas.smarthome.db.EnvironmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("environment")
@Log4j2
public class EnvironmentController {

    private final Gson gson = new Gson();
    @Autowired
    private EnvironmentDAO environmentDAO;
}
