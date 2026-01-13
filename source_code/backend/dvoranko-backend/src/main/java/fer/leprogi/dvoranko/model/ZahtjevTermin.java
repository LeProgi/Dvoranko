package fer.leprogi.dvoranko.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "zahtjev_oglas")
public class ZahtjevTermin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datum_vrijeme_start", length = 8, nullable = false)
    private String datumVrijemeStart;

    @Column(name = "datum_vrijeme_end", length = 8, nullable = false)
    private String datumVrijemeEnd;

    @Column(name = "je_javni_event")
    private Integer jeJavniEvent;

    @Column(name = "id_korisnik", nullable = false)
    private Long idKorisnik;

    @Column(name = "id_dvorana", nullable = false)
    private Long idDvorana;
}
