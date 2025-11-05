package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdresa;

    @NotNull(message = "Latitude je obavezna")
    @Column(nullable = false)
    private Double latitude;

    @NotNull(message = "Longitude je obavezna")
    @Column(nullable = false)
    private Double longitude;

    private String ulica;

    private String kucniBroj;

    @NotNull(message = "Mjesto je obavezno")
    @ManyToOne
    @JoinColumn(name = "idMjesto", nullable = false)
    private Mjesto mjesto;

}
