package nl.bramvonk.homeautomation.mechanicalventilation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MechanicalVentilationService {

    private static final Logger logger = LoggerFactory.getLogger(MechanicalVentilationService.class);

    private MechanicalVentilationRepository repository;

    @Autowired
    public MechanicalVentilationService(MechanicalVentilationRepository repository) {
        this.repository = repository;
    }

    public MechanicalVentilationSetting getSetting() {
        return repository.getMechanicalVentilationState();
    }

    public void setSetting(MechanicalVentilationSetting newSetting) {
        repository.setMechanicalVentilationState(newSetting);
        logger.info("Set mechanical ventilation to setting " + newSetting);
    }
}
