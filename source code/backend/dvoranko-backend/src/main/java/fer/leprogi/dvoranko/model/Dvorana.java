package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dvorana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDvorana;

    @NotBlank(message = "Naziv dvorane ne smije biti prazan.")
    private String nazivDvorana;

    private Integer kapacitet;

    private String opis;

    @OneToOne(cascade = CascadeType.ALL)
    private Adresa adresa;

}
