package shadowcraft.dto;

public class PropInstance {

  private String idRef;
  private String name;
  private Orientation orientation;
  private GridPoint gridPoint;

  public PropInstance(String idRef, String name, GridPoint gridPoint, Orientation orientation) {
    this.idRef = idRef;
    this.name = name.replace("\"", "");
    this.gridPoint = gridPoint;
    this.orientation = orientation;
  }

  public String getIdRef() {
    return idRef;
  }

  public String getName() {
    return name;
  }

  public GridPoint getGridPoint() {
    return gridPoint;
  }

  public Orientation getOrientation() {
    return orientation;
  }

}
