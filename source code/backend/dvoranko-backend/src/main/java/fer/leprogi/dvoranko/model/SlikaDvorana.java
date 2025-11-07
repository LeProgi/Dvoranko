package fer.leprogi.dvoranko.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class SlikaDvorana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSlika;

    @Lob
    @NotNull
    private byte[] imageData;


}
