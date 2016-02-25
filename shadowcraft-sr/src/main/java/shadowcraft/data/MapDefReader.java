package shadowcraft.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import shadowcraft.dto.GridPoint;
import shadowcraft.dto.MapDef;
import shadowcraft.dto.Orientation;
import shadowcraft.dto.PropInstance;

public enum MapDefReader {
  INSTANCE;

  private ReaderTool tool = ReaderTool.INSTANCE;

  public MapDef loadMap(String txtFile) {
    List<String> lines = tool.readLines(txtFile);

    int minX = tool.getLineValueInt(lines.get(0));
    int minZ = tool.getLineValueInt(lines.get(1));
    int numX = tool.getLineValueInt(lines.get(2));
    int numZ = tool.getLineValueInt(lines.get(3));

    //    Map<String, PropInstance> props = new HashMap<>();
    List<PropInstance> props = new ArrayList<>();
    for (int idx = 4; idx < lines.size(); ++idx) {
      if ("props {".equals(lines.get(idx))) {
        PropInstance prop = createPropInstance(lines, idx + 1);
        props.add(prop);
      }
    }

    return new MapDef(minX, minZ, numX, numZ, props);
  }

  private PropInstance createPropInstance(List<String> lines, int startIdx) {
    //I think those three lines are always the same and in the same order
    String name = tool.getLineValue(lines.get(startIdx));
    GridPoint gridPoint = tool.createGridPoint(lines.subList(startIdx + 2, startIdx + 5));
    Orientation orientation = Orientation.valueOf(tool.getLineValue(lines.get(startIdx + 6)));

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
        idRef = tool.getLineValue(lines.get(++idx));
        ++idx;
      } else {
        //        System.out.println("unhandled: " + line);
      }
    }
    PropInstance result = new PropInstance(idRef, name, gridPoint, orientation);

    return result;
  }
}
