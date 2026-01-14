package fer.leprogi.dvoranko.utils;

import fer.leprogi.dvoranko.dto.*;
import fer.leprogi.dvoranko.model.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public DvoranaDTO toDvoranaDTO(Dvorana dvorana) {
        if (dvorana == null) return null;

        DvoranaDTO dto = new DvoranaDTO();
        dto.setIdDvorana(dvorana.getIdDvorana());
        dto.setNazivDvorana(dvorana.getNazivDvorana());
        dto.setKapacitet(dvorana.getKapacitet());
        dto.setOpis(dvorana.getOpis());
        dto.setAdresa(toAdresaDTO(dvorana.getAdresa()));

//        if (dvorana.getKategorije() != null) {
//            Set<KategorijaDTO> kategorijeDTO = dvorana.getKategorije()
//                    .stream()
//                    .map(this::toKategorijaDTO)
//                    .collect(Collectors.toSet());
//            dto.setKategorije(kategorijeDTO);
//        }

        dto.setVlasnik(toUserDTO(dvorana.getVlasnik()));
        //ovo kasnije?
        //dto.setSlika(toSlikaDvoranaDTO(dvorana.getSlika()));

        return dto;
    }

    public AdresaDTO toAdresaDTO(Adresa adresa) {
        if (adresa == null) return null;

        AdresaDTO dto = new AdresaDTO();
        dto.setIdAdresa(adresa.getIdAdresa());
        dto.setLatitude(adresa.getLatitude());
        dto.setLongitude(adresa.getLongitude());
        dto.setUlica(adresa.getUlica());
        dto.setKucniBroj(adresa.getKucniBroj());
        dto.setMjesto(toMjestoDTO(adresa.getMjesto()));
        return dto;
    }

    public MjestoDTO toMjestoDTO(Mjesto mjesto) {
        if (mjesto == null) return null;
        
        MjestoDTO dto = new MjestoDTO();
        dto.setIdMjesto(mjesto.getIdMjesto());
        dto.setPostanskiBroj(mjesto.getPostanskiBroj());
        dto.setNazivMjesto(mjesto.getNazivMjesto());
        return dto;
    }

    public KategorijaDTO toKategorijaDTO(Kategorija kategorija) {
        if (kategorija == null) return null;

        KategorijaDTO dto = new KategorijaDTO();
        dto.setIdKategorija(kategorija.getIdKategorija());
        dto.setNazivKategorije(kategorija.getNazivKategorija());
        return dto;
    }

    public UserDTO toUserDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPictureUrl(user.getPictureUrl());
        dto.setRole(user.getRole());
        return dto;
    }

    public SlikaDvoranaDTO toSlikaDvoranaDTO(SlikaDvorana slikaDvorana) {
        if (slikaDvorana == null) return null;

        SlikaDvoranaDTO dto = new SlikaDvoranaDTO();
        dto.setIdSlika(slikaDvorana.getIdSlika());
        dto.setImageData(slikaDvorana.getImageData());
        return dto;
    }

    public ZahtjevIznajmljivacDTO toZahtjevIznajmljivacDTO(ZahtjevIznajmljivac zahtjev) {

        if (zahtjev == null) return null;

        ZahtjevIznajmljivacDTO dto = new ZahtjevIznajmljivacDTO();
        dto.setId(zahtjev.getId());

        if (zahtjev.getUser() != null) {
            UserDTO userDTO = new UserDTO();

            userDTO.setId(zahtjev.getUser().getId());
            userDTO.setName(zahtjev.getUser().getName());
            userDTO.setEmail(zahtjev.getUser().getEmail());
            userDTO.setPictureUrl(zahtjev.getUser().getPictureUrl());
            userDTO.setRole(zahtjev.getUser().getRole());

            dto.setUser(userDTO);
        }

        return dto;
    }

    public ZahtjevOglasDTO toZahtjevOglasDTO(ZahtjevOglas zahtjev) {
        if (zahtjev == null) return null;

        ZahtjevOglasDTO dto = new ZahtjevOglasDTO();

        dto.setId(zahtjev.getIdZahtjevOglas());
        dto.setOwner(toUserDTO(zahtjev.getOwner()));

        dto.setNaziv(zahtjev.getNaziv());
        dto.setOpis(zahtjev.getOpis());
        dto.setKapacitet(zahtjev.getKapacitet());


        Set<KategorijaDTO> kategorijeDTO = zahtjev.getKategorije()
                .stream()
                .map(this::toKategorijaDTO)
                .collect(Collectors.toSet());
        dto.setKategorije(kategorijeDTO);


        dto.setPostalCode(zahtjev.getPostalCode());
        dto.setCity(zahtjev.getCity());
        dto.setStreet(zahtjev.getStreet());
        dto.setStreetNumber(zahtjev.getStreetNumber());
        dto.setLatitude(zahtjev.getLatitude());
        dto.setLongitude(zahtjev.getLongitude());

        return dto;
    }


    public static TerminDTO toTerminDto(Termin termin) {
        if (termin == null) {
            return null;
        }

        TerminDTO dto = new TerminDTO();
        dto.setDatumVrijemeStart(termin.getDatumVrijemeStart());
        dto.setDatumVrijemeEnd(termin.getDatumVrijemeEnd());
        dto.setIdDvorana(termin.getDvorana().getIdDvorana());
        dto.setJeJavniEvent(termin.getJeJavniEvent());
        dto.setIdKorisnik(termin.getKorisnik().getId());

        return dto;
    }

}
