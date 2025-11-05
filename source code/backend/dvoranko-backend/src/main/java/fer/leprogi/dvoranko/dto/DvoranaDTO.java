package fer.leprogi.dvoranko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DvoranaDTO {
    private Long idDvorana;
    private String nazivDvorana;
    private Integer kapacitet;
    private String opis;
    private AdresaDTO adresa;
}
