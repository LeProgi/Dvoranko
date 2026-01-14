package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlikaDvorana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSlika;

    private String poredakSlike;
    private String urlSlika;

    @ManyToOne
    @JoinColumn(name = "idDvorana", nullable = false)
    private Dvorana dvorana;

}
