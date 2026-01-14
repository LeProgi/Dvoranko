package fer.leprogi.dvoranko.dto.createRequest;

import fer.leprogi.dvoranko.dto.ZahtjevSlikaDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CreateZahtjevOglas {
    private Long idOwner;

    private String naziv;
    private String opis;
    private Integer kapacitet;

    private Set<Long> idKategorije;

    private Long postalCode;
    private String city;
    private String street;
    private String streetNumber;
    private Double lat;
    private Double lng;

    private List<ZahtjevSlikaDTO> slike;


}
