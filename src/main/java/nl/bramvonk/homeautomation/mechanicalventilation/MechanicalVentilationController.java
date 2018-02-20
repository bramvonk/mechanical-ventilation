package nl.bramvonk.homeautomation.mechanicalventilation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MechanicalVentilationController {

    private static final Logger logger = LoggerFactory.getLogger(MechanicalVentilationController.class);

    private MechanicalVentilationService service;

    @Autowired
    public MechanicalVentilationController(MechanicalVentilationService service) {
        this.service = service;
    }

    @GetMapping(value = "/mechanicalventilation")
    public MechanicalVentilation getData() {
        MechanicalVentilation mechanicalVentilation = new MechanicalVentilation();
        mechanicalVentilation.setSetting(service.getSetting());
        return mechanicalVentilation;
    }

    @PutMapping(value = "/mechanicalventilation")
    public MechanicalVentilation putSetting(@RequestBody MechanicalVentilation mechanicalVentilation) {
        logger.info("PUT received with new mechanical ventilation setting {}", mechanicalVentilation.getSetting());
        service.setSetting(mechanicalVentilation.getSetting());
        return mechanicalVentilation;
    }

}
