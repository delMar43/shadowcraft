package shadowcraft.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Main extends ApplicationAdapter {

  public Environment environment;
  public PerspectiveCamera cam;
  public ModelBatch modelBatch;
  public Model model;
  public ModelInstance instance;
  public CameraInputController camController;

  @Override
  public void create() {
    initCam();
    initModelStuff();
    initEnvironment();

    initCamController();
  }

  private void initCam() {
    cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    cam.position.set(10f, 10f, 10f);
    cam.lookAt(0, 0, 0);
    cam.near = 1f;
    cam.far = 300f;
    cam.update();
  }

  private void initModelStuff() {
    modelBatch = new ModelBatch();

    ModelBuilder modelBuilder = new ModelBuilder();
    model = modelBuilder.createBox(5f, 0.2f, 5f, new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);
    instance = new ModelInstance(model);
  }

  private void initEnvironment() {
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
    environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
  }

  private void initCamController() {
    camController = new CameraInputController(cam);
    Gdx.input.setInputProcessor(camController);
  }

  @Override
  public void resize(int width, int height) {
    System.out.println(String.format("resize to %dx%d", width, height));

  }

  @Override
  public void render() {
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

    modelBatch.begin(cam);
    modelBatch.render(instance, environment);
    modelBatch.end();

    Gdx.graphics.setTitle("ShadowCraft - " + Gdx.graphics.getFramesPerSecond());
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub

  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub

  }

  @Override
  public void dispose() {
    modelBatch.dispose();
    model.dispose();
  }

}
