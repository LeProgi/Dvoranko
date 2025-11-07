package fer.leprogi.dvoranko.dto;

import fer.leprogi.dvoranko.model.SlikaDvorana;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DvoranaDTO {
    private Long idDvorana;
    private String nazivDvorana;
    private Integer kapacitet;
    private String opis;
    private AdresaDTO adresa;
    private Iterable<KategorijaDTO> kategorije;
    private UserDTO vlasnik;
    private SlikaDvoranaDTO slika;
}
