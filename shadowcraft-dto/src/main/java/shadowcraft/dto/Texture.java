package shadowcraft.dto;

public class Texture {

  private String filename;
  private boolean flipHorizontal;

  public Texture(String filename, boolean flipHorizontal) {
    this.filename = filename;
    this.flipHorizontal = flipHorizontal;
  }

  public String getFilename() {
    return filename;
  }

  public boolean isFlipHorizontal() {
    return flipHorizontal;
  }
}
