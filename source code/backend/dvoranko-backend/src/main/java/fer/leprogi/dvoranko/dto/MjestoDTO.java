package fer.leprogi.dvoranko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MjestoDTO {
    private Long idMjesto;
    private Long postanskiBroj;
    private String nazivMjesto;
}
