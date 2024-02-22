package hei.school.sarisary.service;

import static hei.school.sarisary.service.util.ColorImageUtil.toBlackWhite;
import static java.nio.file.Files.readAllBytes;

import hei.school.sarisary.file.BucketComponent;
import hei.school.sarisary.model.ProcessImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ColorImageService {
  private final BucketComponent bucketComponent;

  public ProcessImage getById(String id) {
    var originalUrl = bucketComponent.presign(getOriginalS3Key(id), Duration.ofHours(1)).toString();
    var transformedUrl =
        bucketComponent.presign(getTransformedS3Key(id), Duration.ofHours(1)).toString();
    return new ProcessImage(originalUrl, transformedUrl);
  }

  public void newBlackAndWhite(String id, byte[] original) throws IOException {
    var originalKey = getOriginalS3Key(id);
    var transformedKey = getTransformedS3Key(id);

    var originalFile = toTemp(id, original);
    var transformedFile = toTemp(id, toBlackWhite(original));

    bucketComponent.upload(originalFile, originalKey);
    bucketComponent.upload(transformedFile, transformedKey);
  }

  private String getOriginalS3Key(String id) {
    return id + "-original";
  }

  private String getTransformedS3Key(String id) {
    return id + "-transformed";
  }

  private File toTemp(String id, byte[] bytes) throws IOException {
    var temp = File.createTempFile("temp", id);
    try (FileOutputStream fos = new FileOutputStream(temp)) {
      fos.write(bytes);
    }
    temp.deleteOnExit();
    return temp;
  }
}
