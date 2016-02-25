package shadowcraft.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import shadowcraft.dto.GridPoint;
import shadowcraft.dto.MapDef;
import shadowcraft.dto.Orientation;
import shadowcraft.dto.PropInstance;

public enum MapReader {
  INSTANCE;

  public MapDef loadMap(String txtFile) {
    List<String> lines = readLines(txtFile);

    int minX = getLineValueInt(lines.get(0));
    int minZ = getLineValueInt(lines.get(1));
    int numX = getLineValueInt(lines.get(2));
    int numZ = getLineValueInt(lines.get(3));

    Map<String, PropInstance> props = new HashMap<>();
    for (int idx = 4; idx < lines.size(); ++idx) {
      if ("props {".equals(lines.get(idx))) {
        PropInstance prop = createPropInstance(lines, idx + 1);
        props.put(prop.getIdRef(), prop);
      }
    }

    return new MapDef(minX, minZ, numX, numZ, props);
  }

  private PropInstance createPropInstance(List<String> lines, int startIdx) {
    //I think those three lines are always the same and in the same order
    String name = getLineValue(lines.get(startIdx));
    GridPoint gridPoint = createGridPoint(lines.subList(startIdx + 2, startIdx + 5));
    Orientation orientation = Orientation.valueOf(getLineValue(lines.get(startIdx + 6)));

    String idRef = null;

    for (int idx = startIdx + 7; idx < lines.size(); ++idx) {
      String line = lines.get(idx);
      if ("}".equals(line)) {
        //the prop is done
        if (idRef == null) {
          idRef = UUID.randomUUID().toString();
        }
        break;
      } else if ("  idRef {".equals(line)) {
        //read next line (++idx) and then skip closing brackets
        idRef = getLineValue(lines.get(++idx));
        ++idx;
      } else {
        //        System.out.println("unhandled: " + line);
      }
    }
    PropInstance result = new PropInstance(idRef, name, gridPoint, orientation);

    return result;
  }

  private GridPoint createGridPoint(List<String> lines) {
    double x = getLineValueDouble(lines.get(0));
    double y = getLineValueDouble(lines.get(1));
    double z = getLineValueDouble(lines.get(2));
    return new GridPoint(x, y, z);
  }

  private String getLineValue(String line) {
    String value = line.split(":")[1].trim();
    return value;
  }

  private int getLineValueInt(String line) {
    String value = getLineValue(line);
    int result = Integer.parseInt(value);
    return result;
  }

  private double getLineValueDouble(String line) {
    String value = getLineValue(line);
    double result = Double.parseDouble(value);
    return result;
  }

  private List<String> readLines(String txtFile) {
    List<String> lines;
    try {
      lines = FileUtils.readLines(new File(txtFile));
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e);
    }
    return lines;
  }

  private PropInstance readProp(List<String> lines) {
    throw new RuntimeException("NIY");
  }

  private enum State {
    ROOT, PROP, GRID_POINT, IDREF;
  }
}
