package fer.leprogi.dvoranko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZahtjevOglasDTO {
    private Long id;
    private UserDTO user;
    private String naziv;
    private String opis;
    private Integer kapacitet;
    private String adresa;
    private Iterable<KategorijaDTO> kategorije;
    private Double latitude;
    private Double longitude;

}