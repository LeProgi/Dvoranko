package fer.leprogi.dvoranko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminZaFrontDTO {
    private Long id;
    private LocalDateTime datumVrijemeStart;
    private LocalDateTime datumVrijemeEnd;
    private Long idDvorana;
    private Integer jeJavniEvent;
    private Long idKorisnik;
    private String imeDogadanja;
    private String opisDogadanja;
    private String imeVlasnika;
    private DvoranaDTO dvorana;
}