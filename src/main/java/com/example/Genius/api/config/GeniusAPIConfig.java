package com.example.Genius.api.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeniusAPIConfig {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${genius.api.client-id}")
    private String clientId;

    @Value("${genius.api.client-secret}")
    private String clientSecret;

    private String accessToken="";

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getAccessToken() {
        if (accessToken.isEmpty()) {
            try {

                String tokenUrl = String.format(
                        "https://api.genius.com/oauth/token?client_id=%s&client_secret=%s&grant_type=client_credentials",
                        clientId,
                        clientSecret
                );


                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<TokenResponse> response = restTemplate.exchange(
                        tokenUrl,
                        HttpMethod.POST,
                        entity,
                        TokenResponse.class
                );

                if (response.getBody() != null) {
                    this.accessToken = response.getBody().getAccessToken();
                }
            } catch (Exception e) {
                System.err.println("Failed to retrieve access token: " + e.getMessage());
                throw new RuntimeException("Could not obtain access token", e);
            }
        }
        return accessToken;
    }

    public static class TokenResponse {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("expires_in")
        private Long expiresIn;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

    }
}
