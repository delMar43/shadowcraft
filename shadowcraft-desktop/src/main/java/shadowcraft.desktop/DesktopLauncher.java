package shadowcraft.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {

  public static void main(String... args) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle("ShadowCraft");
    config.setWindowedMode(640, 480);
    // config.setWindowListener(new DesktopWindowListener());

    new Lwjgl3Application(new Main(), config);
  }

}
