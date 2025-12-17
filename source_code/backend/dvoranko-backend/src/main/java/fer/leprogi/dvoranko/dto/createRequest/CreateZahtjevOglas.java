package fer.leprogi.dvoranko.dto.createRequest;

import lombok.Data;

@Data
public class CreateZahtjevOglas {
    private Long idOwner;

    private String naziv;
    private String opis;
    private Integer kapacitet;

    private Integer postalCode;
    private String city;
    private String street;
    private String streetNumber;
    private Double lat;
    private Double lng;

}
