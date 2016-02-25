package shadowcraft.data;

import java.util.Map;

import org.junit.Test;

import shadowcraft.dto.PropDef;

public class PropDefReaderTest {

  private PropDefReader reader = PropDefReader.INSTANCE;

  @Test
  public void read() {
    memOutput();
    long start = System.currentTimeMillis();
    Map<String, PropDef> result = reader.loadPropDefs("C:\\Users\\martin\\shadowcraft\\data\\props");
    System.out.println("Done after " + (System.currentTimeMillis() - start) + " ms: " + result.size() + " props.");
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
