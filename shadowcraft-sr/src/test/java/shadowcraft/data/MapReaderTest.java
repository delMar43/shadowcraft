package shadowcraft.data;

import org.junit.Test;

import shadowcraft.dto.MapDef;

public class MapReaderTest {

  private MapReader reader = MapReader.INSTANCE;

  @Test
  public void readmap() {
    long start = System.currentTimeMillis();
    MapDef mapDef = reader.loadMap("C:\\Users\\martin\\shadowcraft\\data\\maps\\a2_falseflag_s2.srm.txt");
    System.out.println("Done after " + (System.currentTimeMillis() - start) + " ms");
  }
}
