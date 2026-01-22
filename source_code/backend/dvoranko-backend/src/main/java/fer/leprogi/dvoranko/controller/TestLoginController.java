package fer.leprogi.dvoranko.controller;
import fer.leprogi.dvoranko.model.Role;
import fer.leprogi.dvoranko.model.User;
import fer.leprogi.dvoranko.repository.UserRepository;
import fer.leprogi.dvoranko.security.CustomOAuth2User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Profile("test")
public class TestLoginController {

    private final UserRepository userRepository;

    @GetMapping("/test/login/{role}")
    public void testLogin(HttpServletRequest request, HttpServletResponse response, @PathVariable String role) {

//        testLoginService.testCreateUser();
        String email = "test-user@example.com";
        String name = "Test User";
        String googleId = "test-google-id";
        String pictureUrl = "";
        Role roleEnum = role.equals("admin") ? Role.ADMIN : role.equals("moderator") ? Role.MODERATOR : Role.USER;

//        User user = userRepository.findByGoogleId(googleId)
//                .orElseGet(() -> {
//                    User newUser = new User(email, name, googleId, roleEnum);
//                    newUser.setPictureUrl(pictureUrl);
//                    return userRepository.save(newUser);
//                });

        User user;
        if (userRepository.existsByGoogleId(googleId)) {
            User user1 = userRepository.findByGoogleId(googleId).get();
            user1.setRole(roleEnum);
            user = userRepository.save(user1);
        }else {
            User user1 = new User(email, name, googleId, roleEnum);
            user1.setPictureUrl(pictureUrl);
            user = userRepository.save(user1);
        }


        Map<String, Object> attributes = Map.of(
                "email", user.getEmail(),
                "name", user.getName(),
                "picture", user.getPictureUrl(),
                "sub", user.getGoogleId()
        );

        CustomOAuth2User principal = new CustomOAuth2User(
                new org.springframework.security.oauth2.core.user.DefaultOAuth2User(
                        Collections.emptyList(),
                        attributes,
                        "sub"
                ),
                user
        );

        // 1️⃣ Postavi principal u SecurityContext
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 2️⃣ Kreiraj session i postavi SecurityContext
        var session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        // 3️⃣ Vrati session cookie u response
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setPath("/");       // dostupno za sve putanje
        cookie.setHttpOnly(true);  // samo za backend
        cookie.setMaxAge(-1);      // session cookie
        response.addCookie(cookie);

        // 4️⃣ Redirect ili plain text
        response.setStatus(200);
    }
}