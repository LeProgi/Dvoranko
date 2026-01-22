package fer.leprogi.dvoranko.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "zahtjev_termin")
public class ZahtjevTermin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datum_vrijeme_start",  nullable = false)
    private LocalDateTime datumVrijemeStart;

    @Column(name = "datum_vrijeme_end",  nullable = false)
    private LocalDateTime datumVrijemeEnd;

    @Column(name = "je_javni_event")
    private Integer jeJavniEvent;

    @Column(name = "id_korisnik", nullable = false)
    private Long idKorisnik;

    @ManyToOne
    @JoinColumn(name = "id_dvorana", nullable = false)
    private Dvorana dvorana;

    @Column(name = "ime_dogadanja")
    private String imeDogadanja;

    @Column(name = "opis_dogadanja", columnDefinition = "TEXT")
    private String opisDogadanja;
}
