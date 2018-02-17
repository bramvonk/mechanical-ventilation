package nl.bramvonk.homeautomation.mechanicalventilation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MechanicalVentilationServiceTest {
    @Test
    public void testGetAndSet() {
        MechanicalVentilationService service = new MechanicalVentilationService();
        service.setSetting(MechanicalVentilationSetting.HIGH);

        assertEquals("Getter should return value we just set", MechanicalVentilationSetting.HIGH, service.getSetting());
    }

}
