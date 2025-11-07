
package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.model.Role;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.security.CustomOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;


    // Ucitaj korisnika iz baze i updateaj mu podatke, a ako ga nema onda stvori novog s default ulogom USER
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String pictureUrl = oauth2User.getAttribute("picture");
        String googleId = oauth2User.getAttribute("sub");
        
        User user = userRepository.findByGoogleId(googleId)
                .orElseGet(() -> {
                    // New user - assign default role
                    User newUser = new User(email, name, googleId, Role.USER);
                    newUser.setPictureUrl(pictureUrl);
                    return userRepository.save(newUser);
                });
        
        // Update user info if it has changed
        boolean updated = false;
        if (!user.getName().equals(name)) {
            user.setName(name);
            updated = true;
        }
        if (pictureUrl != null && !pictureUrl.equals(user.getPictureUrl())) {
            user.setPictureUrl(pictureUrl);
            updated = true;
        }
        if (updated) {
            user = userRepository.save(user);
        }
        
        return new CustomOAuth2User(oauth2User, user);
    }
}
