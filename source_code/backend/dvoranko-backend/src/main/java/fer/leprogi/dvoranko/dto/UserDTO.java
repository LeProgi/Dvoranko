package fer.leprogi.dvoranko.dto;

import fer.leprogi.dvoranko.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String pictureUrl;
    private Role role;
}