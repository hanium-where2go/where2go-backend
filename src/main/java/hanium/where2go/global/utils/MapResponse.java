package hanium.where2go.global.utils;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class MapResponse {

    private String area1;
    private String area2;
    private String area3;
    private String road;
    private String number;
    private String detail;
    private String zipcode;

    public MapResponse(JsonNode jsonNode) { // 생성자
        JsonNode result = jsonNode.get("results").get(0);
        setAreas(result.get("region"));
        setLand(result.get("land"));
    }

    @JsonSetter("region") // results[0]의 region 객체
    private void setAreas(JsonNode jsonNode) {
        this.area1 = jsonNode.get("area1").get("name").asText();
        this.area2 = jsonNode.get("area2").get("name").asText();
        this.area3 = jsonNode.get("area3").get("name").asText();
    }

    @JsonSetter("land") // results[0]의 land 객체
    private void setLand(JsonNode jsonNode) {
        this.road = jsonNode.get("name").asText();
        this.number = jsonNode.get("number1").asText();
        this.detail = jsonNode.get("addition0").get("value").asText();
        this.zipcode = jsonNode.get("addition1").get("value").asText();
    }
}
