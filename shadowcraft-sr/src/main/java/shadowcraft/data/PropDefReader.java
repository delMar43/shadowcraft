package shadowcraft.data;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadowcraft.dto.MapLayer;
import shadowcraft.dto.Orientation;
import shadowcraft.dto.PropDef;
import shadowcraft.dto.PropRender;
import shadowcraft.dto.PropView;
import shadowcraft.dto.RenderBounds;
import shadowcraft.dto.Texture;

public enum PropDefReader {
  INSTANCE;

  private ReaderTool tool = ReaderTool.INSTANCE;

  public Map<String, PropDef> loadPropDefs(String path) {
    Map<String, PropDef> result = new HashMap<>();

    File dir = new File(path);
    for (File propFile : dir.listFiles()) {
      String name = propFile.getName().replace(".pb.txt", "");
      //      System.out.println(name);
      result.put(name, loadPropFile(propFile.getAbsolutePath()));
    }

    return result;
  }

  private PropDef loadPropFile(String txtFile) {
    List<String> lines = tool.readLines(txtFile);
    Map<Orientation, PropView> views = new HashMap<>();

    String uiName = tool.getLineValue(lines.get(0));
    MapLayer mapLayer = MapLayer.NONE;
    for (int idx = 1; idx < lines.size(); ++idx) {
      String line = lines.get(idx);
      if (line.startsWith("mapLayer")) {
        mapLayer = MapLayer.valueOf(tool.getLineValue(line));
      } else if ("views {".equals(line)) {
        PropView propView = createPropView(lines, idx + 1);
        views.put(propView.getOrientation(), propView);
      }
    }

    PropDef result = new PropDef(uiName, mapLayer, views);
    return result;
  }

  private PropView createPropView(List<String> lines, int startIdx) {
    Orientation orientation = null;
    Texture texture = null;
    PropRender render = null;

    State state = State.ROOT;

    for (int idx = startIdx; idx < lines.size(); ++idx) {
      String line = lines.get(idx);
      if (state == State.ROOT) {
        if ("}".equals(line)) {
          //We're done with this view
          break;
        } else if (line.startsWith("  orientation:")) {
          orientation = Orientation.valueOf(tool.getLineValue(line));
        } else if ("  texture {".equals(line)) {
          texture = createTexture(lines, idx + 1);
          state = State.TEXTURE;
        } else if ("  render {".equals(line)) {
          render = createRender(lines, idx + 1);
          state = State.RENDER;
        }
      } else {
        String closer = state.getClosing();
        if (closer.equals(line)) {
          state = state.getParent();
        }
      }
    }

    PropView result = new PropView(orientation, texture, render);
    return result;
  }

  private Texture createTexture(List<String> lines, int startIdx) {
    String filename = null;
    boolean flipHorizontal = false;
    for (int idx = startIdx; idx < lines.size(); ++idx) {
      String line = lines.get(idx);
      if (line.startsWith("    filename:")) {
        filename = tool.getLineValue(line);
      } else if (line.startsWith("    flip_horizontal:")) {
        flipHorizontal = Boolean.parseBoolean(tool.getLineValue(line));
      }
    }

    Texture texture = new Texture(filename, flipHorizontal);
    return texture;
  }

  private PropRender createRender(List<String> lines, int startIdx) {
    RenderBounds bounds = null;
    for (int idx = startIdx; idx < lines.size(); ++idx) {
      String line = lines.get(idx);

      if ("    bounds {".equals(line)) {
        if (lines.get(idx + 1).contains("}")) {
          //          System.out.println("empty bounds");
        } else {
          bounds = createBounds(lines, idx + 1);
          idx += 5;
        }
      } else if ("    offset {".equals(line)) {

      } else if ("    }".equals(line)) {
        break;
      }
    }

    PropRender result = new PropRender(bounds, null);
    return result;
  }

  private RenderBounds createBounds(List<String> lines, int startIdx) {
    float width = tool.getLineValueFloat(lines.get(startIdx));
    float height = tool.getLineValueFloat(lines.get(startIdx + 1));
    float depth = tool.getLineValueFloat(lines.get(startIdx + 2));

    RenderBounds result = new RenderBounds(width, height, depth);
    return result;
  }

  private enum State {
    ROOT(0, null), TEXTURE(2, State.ROOT), RENDER(2, State.ROOT), BOUNDS(4, State.RENDER), OFFSET(4, State.RENDER);

    private byte indent;
    private State parent;

    private State(int indent, State parent) {
      this.indent = (byte) indent;
      this.parent = parent;
    }

    public String getClosing() {
      char[] filler = new char[indent];
      Arrays.fill(filler, ' ');
      return String.copyValueOf(filler) + "}";
    }

    public State getParent() {
      return parent;
    }
  }
}
