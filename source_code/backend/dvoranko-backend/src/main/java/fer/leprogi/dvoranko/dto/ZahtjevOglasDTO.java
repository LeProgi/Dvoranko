package fer.leprogi.dvoranko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZahtjevOglasDTO {
    private Long id;
    private UserDTO owner;

    private String naziv;
    private String opis;
    private Integer kapacitet;
    private Iterable<KategorijaDTO> kategorije;
    private String daysOpen;

    private Long postalCode;
    private String city;
    private String street;
    private String streetNumber;
    private Double latitude;
    private Double longitude;


    private Iterable<ZahtjevSlikaDTO> slike;

}