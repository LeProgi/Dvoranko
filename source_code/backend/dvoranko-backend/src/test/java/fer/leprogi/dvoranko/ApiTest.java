package fer.leprogi.dvoranko;

import fer.leprogi.dvoranko.controller.AdresaController;
import fer.leprogi.dvoranko.dto.AdresaDTO;
import fer.leprogi.dvoranko.service.AdresaService;
import fer.leprogi.dvoranko.utils.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdresaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdresaService adresaService;

    @Test
    void correctAPICall() throws Exception {
        AdresaDTO adresaDTO = new AdresaDTO();

        when(adresaService.getAllAdrese()).thenReturn(List.of(adresaDTO));

        mockMvc.perform(get("/api/public/adrese"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    void existingAPICallButNotFound() throws Exception {
        when(adresaService.getAdresaById(1L)).thenThrow(new ResourceNotFoundException("Adresa Not Found"));

        mockMvc.perform(get("/api/public/adrese/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
