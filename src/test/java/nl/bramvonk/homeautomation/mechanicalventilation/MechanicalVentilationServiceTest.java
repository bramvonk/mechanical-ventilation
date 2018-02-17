package nl.bramvonk.homeautomation.mechanicalventilation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;

public class MechanicalVentilationServiceTest {

    @Test
    public void testGetAndSet() {
        MechanicalVentilationRepository mockRepository = mock(MechanicalVentilationRepository.class);
        MechanicalVentilationService service = new MechanicalVentilationService(mockRepository);

        service.setSetting(MechanicalVentilationSetting.HIGH);

        assertEquals("Getter should return value we just set", MechanicalVentilationSetting.HIGH, service.getSetting());

        // verify
        verify(mockRepository).setMechanicalVentilationState(MechanicalVentilationSetting.HIGH);
        verifyNoMoreInteractions(mockRepository);
    }

}
