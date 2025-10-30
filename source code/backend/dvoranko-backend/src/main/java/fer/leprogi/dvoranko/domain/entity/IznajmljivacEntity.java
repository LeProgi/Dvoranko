package fer.leprogi.dvoranko.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "iznajmljivac")
public class IznajmljivacEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_iznajmljivac")
    private Long idIznajmljivac;

    @Column(length = 11, nullable = false, unique = true)
    private String oibIznajmljivac;

    // poveznica na korisnika
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_korisnik", nullable = false)
    private KorisnikEntity korisnik;
}