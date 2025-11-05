package fer.leprogi.dvoranko.utils;

import fer.leprogi.dvoranko.dto.*;
import fer.leprogi.dvoranko.model.*;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public MjestoDTO toMjestoDTO(Mjesto mjesto) {
        if (mjesto == null) return null;
        
        MjestoDTO dto = new MjestoDTO();
        dto.setIdMjesto(mjesto.getIdMjesto());
        dto.setPostanskiBroj(mjesto.getPostanskiBroj());
        dto.setNazivMjesto(mjesto.getNazivMjesto());
        return dto;
    }
}
