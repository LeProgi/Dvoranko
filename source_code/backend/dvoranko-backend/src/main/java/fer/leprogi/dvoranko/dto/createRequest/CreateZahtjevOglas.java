package fer.leprogi.dvoranko.dto.createRequest;

import lombok.Data;

import java.util.Set;

@Data
public class CreateZahtjevOglas {
    private Long idOwner;

    private String naziv;
    private String opis;
    private Integer kapacitet;
    private String daysOpen;

    private Set<Long> idKategorije;

    private Long postalCode;
    private String city;
    private String street;
    private String streetNumber;
    private Double lat;
    private Double lng;

}
