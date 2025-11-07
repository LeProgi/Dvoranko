package fer.leprogi.dvoranko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdresaDTO {
    private Long idAdresa;
    private Double latitude;
    private Double longitude;
    private String ulica;
    private String kucniBroj;
    private MjestoDTO mjesto;
}
