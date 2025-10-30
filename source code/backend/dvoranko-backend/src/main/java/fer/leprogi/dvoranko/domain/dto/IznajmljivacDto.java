package fer.leprogi.dvoranko.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IznajmljivacDto {
    Long idIznajmljivac;
    String oib;
}
