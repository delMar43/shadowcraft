package shadowcraft.dto;

/**
 * It's really a vector.
 * @author martin
 *
 */
public class GridPoint {

  private float x;
  private float y;
  private float z;

  public GridPoint(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public float getZ() {
    return z;
  }
}
