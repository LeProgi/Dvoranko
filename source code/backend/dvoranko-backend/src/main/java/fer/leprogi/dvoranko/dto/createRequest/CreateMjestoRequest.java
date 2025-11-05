package fer.leprogi.dvoranko.dto.createRequest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMjestoRequest {
    @NotNull(message = "Poštanski broj ne smije biti prazan")
    @Min(value = 10000, message = "Poštanski broj mora biti 5-znamenkast")
    @Max(value = 99999, message = "Poštanski broj mora biti 5-znamenkast")
    private Long postanskiBroj;

    @NotBlank(message = "Naziv mjesta ne smije biti prazan")
    private String nazivMjesto;
}
