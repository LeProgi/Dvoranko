package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
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

    @Column(unique = true)
    private Long postanskiBroj;

    private String nazivMjesto;

//    @OneToMany(mappedBy = "mjesto", orphanRemoval = true, cascade = CascadeType.ALL)
//    private List<Adresa> dvorane;
}