package fer.leprogi.dvoranko.services.impl;

import fer.leprogi.dvoranko.domain.entity.KorisnikEntity;
import fer.leprogi.dvoranko.repositories.KorisnikRepository;
import fer.leprogi.dvoranko.services.KorisnikService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    private final KorisnikRepository korisnikRepository;

    public KorisnikServiceImpl(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public KorisnikEntity createKorisnik(KorisnikEntity korisnik) {
        return korisnikRepository.save(korisnik);
    }

    @Override
    public Optional<KorisnikEntity> getKorisnikByIdKorisnik(Long id) {
        return korisnikRepository.findByIdKorisnik(id);
    }

    @Override
    public Optional<KorisnikEntity> getKorisnikByEmail(String email) {
        return korisnikRepository.findByEmail(email);
    }

    @Override
    public List<KorisnikEntity> getAllKorisnik() {
        return korisnikRepository.findAll();
    }


}
