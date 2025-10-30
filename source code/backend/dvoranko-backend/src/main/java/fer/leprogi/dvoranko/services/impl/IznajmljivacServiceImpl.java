package fer.leprogi.dvoranko.services.impl;

import fer.leprogi.dvoranko.domain.entity.IznajmljivacEntity;
import fer.leprogi.dvoranko.domain.entity.KorisnikEntity;
import fer.leprogi.dvoranko.repositories.IznajmljivacRepository;
import fer.leprogi.dvoranko.repositories.KorisnikRepository;
import fer.leprogi.dvoranko.services.IznajmljivacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class IznajmljivacServiceImpl implements IznajmljivacService {

    @Autowired
    private IznajmljivacRepository iznajmljivacRepo;

    @Autowired
    private KorisnikRepository korisnikRepo;

    @Override
    public IznajmljivacEntity createIznajmljivac(String oib, Long korisnikId) {
        KorisnikEntity korisnik = korisnikRepo.findById(korisnikId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Korisnik nije pronađen"));

        IznajmljivacEntity iznajmljivac = IznajmljivacEntity.builder()
                .oibIznajmljivac(oib)
                .korisnik(korisnik)
                .build();

        return iznajmljivacRepo.save(iznajmljivac);
    }

    @Override
    public IznajmljivacEntity getIznajmljivacById(Long id) {
        return iznajmljivacRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Iznajmljivač nije pronađen"));
    }
}
