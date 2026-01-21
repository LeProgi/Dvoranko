package fer.leprogi.dvoranko;

import fer.leprogi.dvoranko.controller.AdminController;
import fer.leprogi.dvoranko.controller.CloudinaryController;
import fer.leprogi.dvoranko.security.SecurityConfig;
import fer.leprogi.dvoranko.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class SecurityApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private DvoranaService dvoranaService;

    @MockBean
    private CloudinaryService cloudinaryService;

    @MockBean
    private CloudinaryController cloudinaryController;

    @MockBean
    private SecurityConfig securityConfig;

    @MockBean
    private MailService mailService;

    @MockBean
    private SessionAdminService  sessionAdminService;

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

