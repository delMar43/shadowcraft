package shadowcraft;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class Controller implements InputProcessor {
  final Vector3 curr = new Vector3();
  final Vector3 last = new Vector3(-1, -1, -1);
  final Vector3 delta = new Vector3();

  final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);

  private OrthographicCamera cam;

  public Controller(OrthographicCamera cam) {
    this.cam = cam;
  }

  @Override
  public boolean touchDragged(int x, int y, int pointer) {
    Ray pickRay = cam.getPickRay(x, y);
    Intersector.intersectRayPlane(pickRay, xzPlane, curr);

    if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
      pickRay = cam.getPickRay(last.x, last.y);
      Intersector.intersectRayPlane(pickRay, xzPlane, delta);
      delta.sub(curr);
      cam.position.add(delta.x, delta.y, delta.z);
    }
    last.set(x, y, 0);
    return false;
  }

  @Override
  public boolean touchUp(int x, int y, int pointer, int button) {
    last.set(-1, -1, -1);
    return false;
  }

  @Override
  public boolean keyDown(int keycode) {
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    if (amount > 0) {
      cam.zoom += 0.1;
    } else {
      cam.zoom -= 0.1;
    }
    return false;
  }

  public void update() {
    cam.update();
  }
}
