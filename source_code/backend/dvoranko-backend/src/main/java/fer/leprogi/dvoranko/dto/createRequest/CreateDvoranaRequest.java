package fer.leprogi.dvoranko.dto.createRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDvoranaRequest {
    private String nazivDvorana;
    private Integer kapacitet;
    private String opis;
    private Long idAdresa;
    private Set<Long> idKategorija;
    private Long idVlasnik;
}
