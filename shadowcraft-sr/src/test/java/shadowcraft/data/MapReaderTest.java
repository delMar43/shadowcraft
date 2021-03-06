package shadowcraft.data;

import org.junit.Test;

import shadowcraft.dto.MapDef;

public class MapReaderTest {

  private MapDefReader reader = MapDefReader.INSTANCE;

  @Test
  public void readmap() {
    long start = System.currentTimeMillis();
    MapDef mapDef = reader.loadMap("C:\\Users\\martin\\shadowcraft\\data\\maps\\a2_falseflag_s2.srm.txt");
    System.out.println("Done after " + (System.currentTimeMillis() - start) + " ms");
    memOutput();
  }

  private void memOutput() {
    int mb = 1024 * 1024;

    //Getting the runtime reference from system
    Runtime runtime = Runtime.getRuntime();

    System.out.println("##### Heap utilization statistics [MB] #####");

    //Print used memory
    System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);

    //Print free memory
    System.out.println("Free Memory:" + runtime.freeMemory() / mb);

    //Print total available memory
    System.out.println("Total Memory:" + runtime.totalMemory() / mb);

    //Print Maximum available memory
    System.out.println("Max Memory:" + runtime.maxMemory() / mb);
  }
}
