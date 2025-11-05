package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mjesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMjesto;

    @NotNull(message = "Postanski broj ne smije biti prazan.")
    @Column(unique = true, nullable = false)
    private Long postanskiBroj;

    @NotBlank(message = "Naziv mjesta ne smije biti prazan.")
    private String nazivMjesto;

//    @OneToMany(mappedBy = "mjesto", orphanRemoval = true, cascade = CascadeType.ALL)
//    private List<Adresa> dvorane;
}