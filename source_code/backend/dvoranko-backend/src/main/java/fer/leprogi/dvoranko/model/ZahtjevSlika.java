package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZahtjevSlika {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSlika;

    private String poredakSlike;
    private String urlSlika;

    @ManyToOne
    @JoinColumn(name = "idZahtjevOglas", nullable = false)
    private ZahtjevOglas zahtjevOglas;
}
