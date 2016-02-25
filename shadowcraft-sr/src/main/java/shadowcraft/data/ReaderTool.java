package shadowcraft.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import shadowcraft.dto.GridPoint;

public enum ReaderTool {
  INSTANCE;

  public GridPoint createGridPoint(List<String> lines) {
    float x = getLineValueFloat(lines.get(0));
    float y = getLineValueFloat(lines.get(1));
    float z = getLineValueFloat(lines.get(2));
    return new GridPoint(x, y, z);
  }

  public String getLineValue(String line) {
    String value = line.split(":")[1].trim();
    return value;
  }

  public int getLineValueInt(String line) {
    String value = getLineValue(line);
    int result = Integer.parseInt(value);
    return result;
  }

  public double getLineValueDouble(String line) {
    String value = getLineValue(line);
    double result = Double.parseDouble(value);
    return result;
  }

  public float getLineValueFloat(String line) {
    String value = getLineValue(line);
    float result = Float.parseFloat(value);
    return result;
  }

  public List<String> readLines(String txtFile) {
    List<String> lines;
    try {
      lines = FileUtils.readLines(new File(txtFile));
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e);
    }
    return lines;
  }
}
