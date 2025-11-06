package fer.leprogi.dvoranko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZahtjevOglasDTO {
    private Long id;
    private UserDTO user;
}