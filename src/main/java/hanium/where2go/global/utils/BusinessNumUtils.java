package hanium.where2go.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class BusinessNumUtils {

    @Value("${ftc.key}")
    private String apiKey;
    private WebClient webClient;

    @PostConstruct
    public void init() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();

        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        factory.uriString("http://api.odcloud.kr");

        this.webClient = WebClient.builder()
                .uriBuilderFactory(factory)
//                .baseUrl("http://api.odcloud.kr")
                .build();
    }

    public boolean validateBusinessNum(String businessNum) {
        return parseJsonToBusinessStatus(sendAndReadJson(businessNum));
    }

    private JsonNode sendAndReadJson(String businessNum) {

        String[] b_no = {businessNum}; // requestBody

        String response = webClient
                .method(HttpMethod.POST)
                .uri(uriBuilder -> uriBuilder
                                .path("/api/nts-businessman/v1/status")
                                .queryParam("serviceKey",apiKey)
                                .queryParam("returnType","JSON")
                                .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(b_no)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readTree(response);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean parseJsonToBusinessStatus(JsonNode jsonNode) {
        String result = jsonNode.get("status_code").asText();
        return "OK".equals(result) ? true : false;
    }

}
