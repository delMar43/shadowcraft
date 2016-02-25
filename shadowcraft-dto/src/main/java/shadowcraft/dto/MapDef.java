package shadowcraft.dto;

import java.util.Collections;
import java.util.Map;

public class MapDef {

  private int minX;
  private int minZ;
  private int numX;
  private int numZ;
  private Map<String, PropInstance> props;

  public MapDef(int minX, int minZ, int numX, int numZ, Map<String, PropInstance> props) {
    this.minX = minX;
    this.minZ = minZ;
    this.numX = numX;
    this.numZ = numZ;
    this.props = Collections.unmodifiableMap(props);
  }

  public int getMinX() {
    return minX;
  }

  public int getMinZ() {
    return minZ;
  }

  public int getNumX() {
    return numX;
  }

  public int getNumZ() {
    return numZ;
  }

  public Map<String, PropInstance> getProps() {
    return props;
  }
}
