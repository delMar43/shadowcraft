package shadowcraft.dto;

import java.util.Collections;
import java.util.List;

public class MapDef {

  private int minX;
  private int minZ;
  private int numX;
  private int numZ;
  private List<PropInstance> props;

  public MapDef(int minX, int minZ, int numX, int numZ, List<PropInstance> props) {
    this.minX = minX;
    this.minZ = minZ;
    this.numX = numX;
    this.numZ = numZ;
    this.props = Collections.unmodifiableList(props);
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

  public List<PropInstance> getProps() {
    return props;
  }
}
