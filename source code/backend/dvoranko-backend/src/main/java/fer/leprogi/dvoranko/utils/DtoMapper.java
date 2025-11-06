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

        if (dvorana.getKategorije() != null) {
            Set<KategorijaDTO> kategorijeDTO = dvorana.getKategorije()
                    .stream()
                    .map(this::toKategorijaDTO)
                    .collect(Collectors.toSet());
            dto.setKategorije(kategorijeDTO);
        }

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

    public  KategorijaDTO toKategorijaDTO(Kategorija kategorija) {
        if (kategorija == null) return null;

        KategorijaDTO dto = new KategorijaDTO();
        dto.setIdKategorija(kategorija.getIdKategorija());
        dto.setNazivKategorije(kategorija.getNazivKategorija());
        return dto;
    }
}
