package nl.bramvonk.homeautomation.mechanicalventilation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MechanicalVentilationRepository {

    private GpioController gpio = GpioFactory.getInstance();

    private GpioPinDigitalOutput firstRelay;
    private GpioPinDigitalOutput secondRelay;

    public MechanicalVentilationRepository() {
        firstRelay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "FirstRelay", PinState.HIGH);
        secondRelay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "FirstRelay", PinState.HIGH);

        firstRelay.setShutdownOptions(true, PinState.HIGH);
        secondRelay.setShutdownOptions(true, PinState.HIGH);
    }

    public void setMechanicalVentilationState(MechanicalVentilationSetting newSetting) {
        switch (newSetting) {
        case LOW:
            // second relay does not matter
            firstRelay.setState(PinState.LOW);
            break;

        case MEDIUM:
            secondRelay.setState(PinState.HIGH);
            firstRelay.setState(PinState.HIGH);
            break;

        case HIGH:
            secondRelay.setState(PinState.LOW);
            firstRelay.setState(PinState.HIGH);
            break;
        }
    }
}
