package shadowcraft.dto;

public class PropView {

  private Orientation orientation;
  private Texture texture;
  private PropRender render;

  public PropView(Orientation orientation, Texture texture, PropRender render) {
    this.orientation = orientation;
    this.texture = texture;
    this.render = render;
  }

  public Orientation getOrientation() {
    return orientation;
  }

  public Texture getTexture() {
    return texture;
  }

  public PropRender getRender() {
    return render;
  }
}
