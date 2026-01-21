package fer.leprogi.dvoranko;

import fer.leprogi.dvoranko.controller.AdminController;
import fer.leprogi.dvoranko.service.AdminService;
import fer.leprogi.dvoranko.service.DvoranaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc
public class SecurityApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private DvoranaService dvoranaService;

    @Test
    void correctAPICallButUnauthorized() throws Exception {
        mockMvc.perform(get("/api/public/admin/getall/users"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    void correctAPICallButForbidden() throws Exception {
        mockMvc.perform(get("/api/public/admin/getall/users"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void correctAPICallAndAuthorized() throws Exception {
        mockMvc.perform(get("/api/public/admin/getall/users"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

