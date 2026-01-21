package fer.leprogi.dvoranko;
import fer.leprogi.dvoranko.model.Role;
import fer.leprogi.dvoranko.utils.FolderName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumTest {

    @Test
    @AutoConfigureTestDatabase
    void EnumTestRoles() {
        assertEquals("USER", Role.USER.name());
        assertEquals("MODERATOR", Role.MODERATOR.name());
        assertEquals("ADMIN", Role.ADMIN.name());
    }

    @Test
    @AutoConfigureTestDatabase
    void EnumTestFolderName() {
        assertEquals("zahtjevi", FolderName.zahtjevi.name());
        assertEquals("dvorane", FolderName.dvorane.name());
    }
}
