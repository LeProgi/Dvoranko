package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adresa {
    @Id
    @NotBlank
    @Pattern(
            regexp = "^-?\\d+(\\.\\d+)?\\s+-?\\d+(\\.\\d+)?$",
            message = "Koordinate moraju biti u formatu 'širina dužina', npr. '45.8150 15.9819'."
    )
    private String koordinate;

    private String ulica;

    private String kucniBroj;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idMjesto", nullable = false)
    private Mjesto mjesto;

}
