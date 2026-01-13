package fer.leprogi.dvoranko.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminDTO {

    private String datumVrijemeStart;
    private String datumVrijemeEnd;
    private Long idDvorana;
    private Integer jeJavniEvent;
    private Long idKorisnik;


}
