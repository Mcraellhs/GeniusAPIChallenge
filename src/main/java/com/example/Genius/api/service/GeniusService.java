package com.example.Genius.api.service;

import com.example.Genius.api.config.GeniusAPIConfig;
import com.example.Genius.api.model.GeniusApiSearchResponse;
import com.example.Genius.api.model.GeniusApiSongMediaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GeniusService {

    private final String GENIUS_API_URL="https://api.genius.com/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GeniusAPIConfig geniusAPIConfig;


    public GeniusApiSearchResponse searchSongs(String query) {
        String url = GENIUS_API_URL+"search?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(geniusAPIConfig.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GeniusApiSearchResponse.class
        ).getBody();
    }

    public GeniusApiSongMediaResponse searchSongMedia(Long id){
        String url = GENIUS_API_URL+"songs/"+id;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(geniusAPIConfig.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GeniusApiSongMediaResponse.class
        ).getBody();
    }


}
