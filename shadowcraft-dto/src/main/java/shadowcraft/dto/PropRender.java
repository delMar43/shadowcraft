package shadowcraft.dto;

public class PropRender {
  private RenderBounds bounds;
  private GridPoint offset;

  public PropRender(RenderBounds bounds, GridPoint offset) {
    this.bounds = bounds;
    this.offset = offset;
  }

  public RenderBounds getBounds() {
    return bounds;
  }

  public GridPoint getOffset() {
    return offset;
  }
}
