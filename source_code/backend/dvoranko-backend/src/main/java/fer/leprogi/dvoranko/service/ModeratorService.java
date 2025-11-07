package fer.leprogi.dvoranko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import fer.leprogi.dvoranko.dto.ZahtjevOglasDTO;
import fer.leprogi.dvoranko.model.ZahtjevOglas;
import fer.leprogi.dvoranko.repository.*;



@Service
public class ModeratorService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ZahtjevOglasRepository zahtjevRepository;



    public ZahtjevOglas createAddRequest(ZahtjevOglasDTO dto) {
        ZahtjevOglas zahtjev = new ZahtjevOglas();
        zahtjev.setNaziv(dto.getNaziv());
        zahtjev.setOpis(dto.getOpis());
        zahtjev.setKapacitet(dto.getKapacitet());
        zahtjev.setAdresa(dto.getAdresa());
        //zahtjev.setKategorija(dto.getKategorija());
        zahtjev.setLatitude(dto.getLatitude());
        zahtjev.setLongitude(dto.getLongitude());
        return zahtjevRepository.save(zahtjev);
    }
}
