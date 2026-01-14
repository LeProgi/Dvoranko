package fer.leprogi.dvoranko.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminDTO {
    private Long id;
    private LocalDateTime datumVrijemeStart;
    private LocalDateTime datumVrijemeEnd;
    private Long idDvorana;
    private Integer jeJavniEvent;
    private Long idKorisnik;


}
