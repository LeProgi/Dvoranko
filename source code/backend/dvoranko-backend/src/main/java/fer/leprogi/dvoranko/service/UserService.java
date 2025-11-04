package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.dto.UserDTO;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPictureUrl(),
                user.getRole()
        );
    }



}
