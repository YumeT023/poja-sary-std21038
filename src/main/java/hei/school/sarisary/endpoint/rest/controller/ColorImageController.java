package hei.school.sarisary.endpoint.rest.controller;

import static hei.school.sarisary.endpoint.rest.controller.health.PingController.OK;

import hei.school.sarisary.model.ProcessImage;
import hei.school.sarisary.service.ColorImageService;
import java.io.File;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ColorImageController {
  private final ColorImageService service;

  @PutMapping(
      value = "/black-and-white/{id}",
      produces = {MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<String> toBlackAndWhite(@PathVariable String id, @RequestBody byte[] image) {
    try {
      service.newBlackAndWhite(id, image);
      return OK;
    } catch (Exception e) {
      return OK;
    }
  }

  @GetMapping("/black-and-white/{id}")
  public ResponseEntity<ProcessImage> getImage(@PathVariable String id) {
    try {
      return ResponseEntity.ok().body(service.getById(id));
    } catch (Exception e) {
      return ResponseEntity.ok().body(new ProcessImage(null, null));
    }
  }
}
