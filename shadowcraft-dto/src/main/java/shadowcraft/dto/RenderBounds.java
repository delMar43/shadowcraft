package shadowcraft.dto;

public class RenderBounds {
  private float width;
  private float height;
  private float depth;

  public RenderBounds(float width, float height, float depth) {
    this.width = width;
    this.height = height;
    this.depth = depth;
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public float getDepth() {
    return depth;
  }
}
