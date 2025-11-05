package fer.leprogi.dvoranko.dto.createRequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdresaRequest {
    @NotNull(message = "Latitude je obavezna")
    @DecimalMin(value = "-90.0", message = "Latitude mora biti između -90 i 90")
    @DecimalMax(value = "90.0", message = "Latitude mora biti između -90 i 90")
    private Double latitude;

    @NotNull(message = "Longitude je obavezna")
    @DecimalMin(value = "-180.0", message = "Longitude mora biti između -180 i 180")
    @DecimalMax(value = "180.0", message = "Longitude mora biti između -180 i 180")
    private Double longitude;

    private String ulica;

    private String kucniBroj;

    @NotNull(message = "ID mjesta je obavezan")
    private Long idMjesto;
}