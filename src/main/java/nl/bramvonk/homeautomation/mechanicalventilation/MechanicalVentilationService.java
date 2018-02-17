package nl.bramvonk.homeautomation.mechanicalventilation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MechanicalVentilationService {

    private static final Logger logger = LoggerFactory.getLogger(MechanicalVentilationService.class);

    private MechanicalVentilationSetting currentSetting = null;

    public MechanicalVentilationSetting getSetting() {
        return currentSetting;
    }

    public void setSetting(MechanicalVentilationSetting newSetting) {
        currentSetting = newSetting;
        logger.info("Set mechanical ventilation to setting " + newSetting);
    }
}
