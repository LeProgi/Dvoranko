package fer.leprogi.dvoranko;

import fer.leprogi.dvoranko.model.Role;
import fer.leprogi.dvoranko.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class UserTest {

    @Test
    void createUserTestAllGood() {
        User user = new User();
        user.setId(67L);
        user.setEmail("testiranjeemail123@gmail.com");
        user.setName("Dvoranko Dvorankić");
        user.setPictureUrl("link_na_sliku");
        user.setGoogleId("test_google_id123");
        LocalDateTime now = LocalDateTime.now(); //želimo isto vrijeme za CreatedAt i UpdatedAt
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setRole(Role.USER);

        assertEquals(67L, user.getId());
        assertEquals("testiranjeemail123@gmail.com", user.getEmail());
        assertEquals("Dvoranko Dvorankić", user.getName());
        assertEquals("link_na_sliku", user.getPictureUrl());
        assertEquals("test_google_id123", user.getGoogleId());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
        assertEquals(Role.USER, user.getRole());
    }
}
