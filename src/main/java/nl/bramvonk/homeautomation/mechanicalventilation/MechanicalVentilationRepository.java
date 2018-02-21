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

    /**
     * Normally*, the mechanical ventilation box is connected as such:
     * 
     * <pre>
     *               3-way-switch    perilex outlet for mechanical ventilation box
     *               
     *                   /o1
     *                  /
     * L ------o------o/  o2----------- L3
     *         |
     *         |          o3----------- L1
     *         |
     *         ------------------------ L2          
     *                    
     * N ------------------------------ N
     * 
     * G ------------------------------ PE
     * </pre>
     * 
     * * well, mine is, but I doubt if that is correct, as the internet says that Itho/Daalderop should be connected
     * differently...
     * 
     * 
     * I've added 2 relays to this schematic:
     * 
     * <pre>
     * 
     *               3-way-switch      perilex outlet for relays                   perilex outlet for mechanical ventilation box
     *                                         relay 1         relay 2
     *                   /o1                       oNC----\        /oNC----\
     *                  /                         /        \      /         \
     * L ------o------o/  o2----------- L3 -----o/          \---o/           \------ L3
     *         |                             
     *         |          o3--\                   
     *         |               \                   oNO              oNO--\
     *         |                \                                         \
     *         |                 \----- L1 --------------------------------o-------- L1
     *         |                             
     *         |                             
     *         |
     *         ------------------------ L2 ----------------------------------------- L2          
     *                    
     * N ------------------------------ N  ----------------------------------------- N
     * 
     * G ------------------------------ PE ----------------------------------------- PE
     * </pre>
     * 
     * This way, when the 3-way-switch is set to speed 2, the relays influence the fan speed, just as the 3-way-switch
     * would:
     * <ul>
     * <li>relay 1 set (NO): neither L3 and L1 are connected, so the same as fan speed 1.
     * <li>relay 1 unset and relay 2 unset (so both NC): only L3 is connected, so the same as fan speed 2.
     * <li>relay 1 unset and relay 2 set (resp NC and NO): only L1 is connected, so the same as fan speed 3.
     * </ul>
     * 
     * Note that:
     * <ul>
     * <li>When the 3-way-switch is set to speed 1, both L3 and L1 are not connected, so that is still speed 1
     * (overriding any relay settings).
     * <li>When the 3-way-switch is set to speed 3, L1 is connected, so that is speed 3 (overriding any relay setting).
     * Note that when relay 2 is set (NO) and relay 1 is unset (NC), current can flow back to the 3 way switch port 2.
     * This port is however not connected (see premise at the start if this paragraph), so that is no problem.
     * </ul>
     */

    private static final PinState RELAY_OFF = PinState.HIGH;
    private static final PinState RELAY_ON = PinState.LOW;

    private GpioController gpio = GpioFactory.getInstance();

    private GpioPinDigitalOutput firstRelay;
    private GpioPinDigitalOutput secondRelay;

    public MechanicalVentilationRepository() {
        firstRelay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "FirstRelay", RELAY_OFF);
        secondRelay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "SecondRelay", RELAY_OFF);

        firstRelay.setShutdownOptions(true, RELAY_OFF);
        secondRelay.setShutdownOptions(true, RELAY_OFF);
    }

    public MechanicalVentilationSetting getMechanicalVentilationState() {
        if (firstRelay.getState() == RELAY_ON) {
            return MechanicalVentilationSetting.LOW;
        } else if (secondRelay.getState() == RELAY_OFF) {
            return MechanicalVentilationSetting.MEDIUM;
        } else {
            return MechanicalVentilationSetting.HIGH;
        }
    }

    public void setMechanicalVentilationState(MechanicalVentilationSetting newSetting) {
        switch (newSetting) {
        case LOW:
            // second relay does not matter
            firstRelay.setState(RELAY_ON);
            break;

        case MEDIUM:
            // set the second relay first, as that means the end state (for the fan box) will only change once: either
            // the first relay already is already OFF, then the first line might change the end result, but the second
            // line of code won't, or the first relay is ON, not connecting the second relay, so the end state is only
            // set after the second line of code.
            secondRelay.setState(RELAY_OFF);
            firstRelay.setState(RELAY_OFF);
            break;

        case HIGH:
            // see above
            secondRelay.setState(RELAY_ON);
            firstRelay.setState(RELAY_OFF);
            break;
        }
    }
}
