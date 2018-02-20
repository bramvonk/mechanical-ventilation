package nl.bramvonk.homeautomation.mechanicalventilation;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(MechanicalVentilationController.class)
public class MechanicalVentilationControllerWebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MechanicalVentilationService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        // test get
        when(service.getSetting()).thenReturn(MechanicalVentilationSetting.MEDIUM);
        this.mockMvc.perform(get("/mechanicalventilation")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{setting:'MEDIUM'}"));

        // test put
        this.mockMvc
                .perform(put("/mechanicalventilation")
                        .content(new ObjectMapper().writeValueAsString(new MechanicalVentilation(MechanicalVentilationSetting.HIGH)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().json("{setting:'HIGH'}"));
        verify(service, times(1)).setSetting(MechanicalVentilationSetting.HIGH);
    }
}
