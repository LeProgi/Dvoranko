package fer.leprogi.dvoranko.service;

import fer.leprogi.dvoranko.security.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionAdminService {
    private final SessionRegistry sessionRegistry;

    public void logoutUserEverywhere(String googleId){
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof CustomOAuth2User user && googleId.equals(user.getName())) {
                for (SessionInformation session : sessionRegistry.getAllSessions(principal, false)) {
                    session.expireNow();
                }
            }
        }
    }
}
