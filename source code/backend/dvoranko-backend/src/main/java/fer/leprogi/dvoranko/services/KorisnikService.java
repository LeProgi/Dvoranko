package fer.leprogi.dvoranko.services;

import fer.leprogi.dvoranko.domain.entity.KorisnikEnitity;

import java.util.Optional;

public interface KorisnikService {
    KorisnikEnitity createKorisnik(KorisnikEnitity korisnik);
    Optional<KorisnikEnitity> getKorisnikById(Long id);
}
