package fer.leprogi.dvoranko;

import fer.leprogi.dvoranko.domain.entity.KorisnikEntity;

public class TestDataUtil {
    private TestDataUtil(){

    }

    public static KorisnikEntity createTestKorisnikA() {
        return KorisnikEntity.builder()
                .id(1L)
                .ime("VM")
                .prezime("L")
                .build();
    }
    public static KorisnikEntity createTestKorisnikB() {
        return KorisnikEntity.builder()
                .id(1L)
                .ime("VM")
                .prezime("L")
                .build();
    }
}
