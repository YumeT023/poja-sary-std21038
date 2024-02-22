package hei.school.sarisary.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProcessImage {
  @JsonProperty("original_url")
  private String originalUrl;

  @JsonProperty("original_url")
  private String transformedUrl;
}
