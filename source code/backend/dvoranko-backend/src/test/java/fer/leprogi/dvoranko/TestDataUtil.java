package fer.leprogi.dvoranko;

import fer.leprogi.dvoranko.domain.entity.KorisnikEnitity;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.util.Date;

public class TestDataUtil {
    private TestDataUtil(){

    }

    public static KorisnikEnitity createTestKorisnikA() {
        return KorisnikEnitity.builder()
                .id(1L)
                .ime("VM")
                .prezime("L")
                .build();
    }
    public static KorisnikEnitity createTestKorisnikB() {
        return KorisnikEnitity.builder()
                .id(1L)
                .ime("VM")
                .prezime("L")
                .build();
    }
}
