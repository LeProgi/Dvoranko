package fer.leprogi.dvoranko.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "korisnik")
public class KorisnikEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_korisnik")
    private Long idKorisnik;

    private String ime;

    private String prezime;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDate datumRodenja;
}
