package fer.leprogi.dvoranko.dto.createRequest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTerminRequest {

    private LocalDateTime datumVrijemeStart;
    private LocalDateTime datumVrijemeEnd;
    private Long idDvorana;
    private Integer jeJavniEvent;
    private Long idKorisnik;
}
