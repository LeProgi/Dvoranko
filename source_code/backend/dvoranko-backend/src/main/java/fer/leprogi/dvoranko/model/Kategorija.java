package fer.leprogi.dvoranko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "dvorane")

public class Kategorija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKategorija;

    @NotBlank(message = "Naziv kategorije ne smije biti prazan.")
    @Column(unique = true, nullable = false)
    private String nazivKategorija;

    @ManyToMany(mappedBy = "kategorije")
    @JsonIgnore
    public Set<Dvorana> dvorane;
}
