package nl.bramvonk.homeautomation.mechanicalventilation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class MechanicalVentilationServiceTest {

    @Test
    public void testSet() {
        MechanicalVentilationRepository mockRepository = mock(MechanicalVentilationRepository.class);
        MechanicalVentilationService service = new MechanicalVentilationService(mockRepository);

        service.setSetting(MechanicalVentilationSetting.HIGH);

        // verify
        verify(mockRepository).setMechanicalVentilationState(MechanicalVentilationSetting.HIGH);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    public void testGet() {
        MechanicalVentilationRepository mockRepository = mock(MechanicalVentilationRepository.class);
        MechanicalVentilationService service = new MechanicalVentilationService(mockRepository);

        when(mockRepository.getMechanicalVentilationState()).thenReturn(MechanicalVentilationSetting.HIGH);

        assertEquals("Getter should return value we from repo", MechanicalVentilationSetting.HIGH, service.getSetting());
    }

}
