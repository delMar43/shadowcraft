package shadowcraft.dto;

import java.util.HashMap;
import java.util.Map;

public class PropDef {

  private String uiName;
  private MapLayer mapLayer;
  private Map<Orientation, PropView> views;

  public PropDef(String uiName, MapLayer mapLayer, Map<Orientation, PropView> views) {
    this.uiName = uiName;
    this.mapLayer = mapLayer;
    this.views = new HashMap<>(views);
  }

  public String getUiName() {
    return uiName;
  }

  public MapLayer getMapLayer() {
    return mapLayer;
  }

  public Map<Orientation, PropView> getViews() {
    return views;
  }
}
