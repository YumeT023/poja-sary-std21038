package hei.school.sarisary.service.util;

import static java.awt.color.ColorSpace.CS_GRAY;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ColorImageUtil {
  public static byte[] toBlackWhite(byte[] image) {
    var buf = toBufferedImage(image);
    var cs = ColorSpace.getInstance(CS_GRAY);
    var op = new ColorConvertOp(cs, null);
    return toBytes(op.filter(buf, null));
  }

  private static BufferedImage toBufferedImage(byte[] image) {
    InputStream is = new ByteArrayInputStream(image);
    try {
      return ImageIO.read(is);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static byte[] toBytes(BufferedImage buf) {
    var temp = new ByteArrayOutputStream();
    try {
      ImageIO.write(buf, "png", temp);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return temp.toByteArray();
  }
}
