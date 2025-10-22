package fer.leprogi.dvoranko.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KorisnikDto {

    Long id;

    String ime;

    String prezime;

    String email;

    //Date datumRodenja;
}
