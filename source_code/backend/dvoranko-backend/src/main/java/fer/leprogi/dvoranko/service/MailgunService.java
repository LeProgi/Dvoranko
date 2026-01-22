package fer.leprogi.dvoranko.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Base64;

@Service
public class MailgunService {

    @Value("${mailgun.api-key}")
    private String apiKey;

    @Value("${mailgun.domain}")
    private String domain;

    @Value("${mailgun.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendEmail(String to, String subject, String text) {

        String url = baseUrl + "/" + domain + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Basic Auth: api:key-xxxx
        String auth = "api:" + apiKey;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        headers.set("Authorization", "Basic " + new String(encodedAuth));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("from", "Mailgun Sandbox <postmaster@" + domain + ">");
        body.add("to", to);
        body.add("subject", subject);
        body.add("text", text);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}