package fer.leprogi.dvoranko.services.impl;

import fer.leprogi.dvoranko.domain.entity.KorisnikEnitity;
import fer.leprogi.dvoranko.repositories.KorisnikRepository;
import fer.leprogi.dvoranko.services.KorisnikService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    private KorisnikRepository korisnikRepository;

    public KorisnikServiceImpl(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public KorisnikEnitity createKorisnik(KorisnikEnitity korisnik) {
        return korisnikRepository.save(korisnik);
    }

    @Override
    public Optional<KorisnikEnitity> getKorisnikById(Long id) {
        return korisnikRepository.findById(id);
    }
}
