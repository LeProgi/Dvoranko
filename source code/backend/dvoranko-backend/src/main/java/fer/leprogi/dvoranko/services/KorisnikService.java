package fer.leprogi.dvoranko.services;

import fer.leprogi.dvoranko.domain.entity.KorisnikEntity;

import java.util.List;
import java.util.Optional;

public interface KorisnikService {
    KorisnikEntity createKorisnik(KorisnikEntity korisnik);

    Optional<KorisnikEntity> getKorisnikByIdKorisnik(Long idKorinsik);
    Optional<KorisnikEntity> getKorisnikByEmail(String email);
    List<KorisnikEntity> getAllKorisnik();
}
