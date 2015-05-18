package com.javamonkeys.tests;

import com.javamonkeys.api.GameService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameServiceTest {

    private static final String baseUrl = "http://localhost:8555";

    ObjectMapper mapper = new ObjectMapper();
    @Test
    public void createGame() throws FileNotFoundException {

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GameService.CreateGameRequest> entity =
                new HttpEntity<GameService.CreateGameRequest>(getGame("create_game.json"),headers);
        ResponseEntity<GameService.CreateGameResponse> response = restTemplate.
                exchange(baseUrl + "/game/new-game", HttpMethod.POST, entity, GameService.CreateGameResponse.class);

        Assert.assertNotNull(response.getBody());
    }

    private GameService.CreateGameRequest getGame(String fileName) throws FileNotFoundException {

        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("game/" + fileName);
        try {
            GameService.CreateGameRequest request =  mapper.readValue(stream, GameService.CreateGameRequest.class);
            return request;
        } catch (IOException e) {
            Assert.fail();
        }
        Assert.fail();
        return null;
    }
}
