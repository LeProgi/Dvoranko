package fer.leprogi.dvoranko.dto.createRequest;

import lombok.Data;

@Data
public class CreateTerminRequest {

    private String datumVrijemeStart;
    private String datumVrijemeEnd;
    private Long idDvorana;
    private Integer jeJavniEvent;
    private Long idKorisnik;
}
