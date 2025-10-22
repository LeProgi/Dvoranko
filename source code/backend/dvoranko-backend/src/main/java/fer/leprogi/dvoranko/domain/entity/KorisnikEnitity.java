package fer.leprogi.dvoranko.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "korisnik")
public class KorisnikEnitity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String ime;

    String prezime;

    String email;

    //Date datumRodenja;
}
