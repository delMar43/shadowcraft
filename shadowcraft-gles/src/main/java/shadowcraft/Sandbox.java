package shadowcraft;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;

import shadowcraft.data.MapDefReader;
import shadowcraft.data.PropDefReader;
import shadowcraft.dto.GridPoint;
import shadowcraft.dto.MapDef;
import shadowcraft.dto.MapLayer;
import shadowcraft.dto.PropDef;
import shadowcraft.dto.PropInstance;
import shadowcraft.dto.PropRender;
import shadowcraft.dto.PropView;
import shadowcraft.dto.RenderBounds;

public class Sandbox implements ApplicationListener {

  private MapDef mapDef;
  private Map<String, PropDef> propMap;

  public OrthographicCamera cam;
  public ModelBatch modelBatch;
  public List<ModelInstance> instances;
  public Environment environment;
  public Controller camController;
  public ShapeRenderer shapeRenderer;
  final Matrix4 matrix = new Matrix4();

  public Sandbox() {
    mapDef = MapDefReader.INSTANCE
        .loadMap("C:\\Users\\martin\\Documents\\Shadowrun Hong Kong\\ContentPacks\\Shadowminer\\data\\maps\\First.srm.txt");
    propMap = PropDefReader.INSTANCE.loadPropDefs("C:\\Users\\martin\\shadowcraft\\data\\props\\");
  }

  public static void main(String[] args) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle("ShadowCraft - The Sandbox");
    new Lwjgl3Application(new Sandbox(), config);
  }

  @Override
  public void create() {
    Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    modelBatch = new ModelBatch();
    createCamera();
    createEnvironment();
    //    createGrid();
    createModels();
    createInputProcessor();

    Sandbox.maxTexMem();
  }

  private void createCamera() {
    cam = new OrthographicCamera(20, 20 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
    cam.position.set(10f, 10f, 10f);
    cam.lookAt(-1, -1, -1);
    cam.near = 0;
    cam.far = 300f;
    matrix.setToRotation(new Vector3(1, 0, 0), 90);

    cam.update();
  }

  private void createEnvironment() {
    environment = new Environment();
    environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
    environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
  }

  private void createGrid() {
    shapeRenderer = new ShapeRenderer();
    shapeRenderer.setColor(Color.WHITE);
    shapeRenderer.setProjectionMatrix(cam.combined);
    shapeRenderer.setTransformMatrix(matrix);

    shapeRenderer.begin(ShapeType.Line);
    int size = 1;

    for (int curX = mapDef.getMinX(); curX < mapDef.getNumX(); ++curX) {
      for (int curZ = mapDef.getMinZ(); curZ < mapDef.getNumZ(); ++curZ) {
        shapeRenderer.rect(curX * size, curZ * size, size, size);

        //        System.out.println("x=" + curX + " z=" + curZ);
      }
    }
    shapeRenderer.end();

    //    shapeRenderer.begin(ShapeType.Filled);
    //    for (PropInstance prop : mapDef.getProps()) {
    //      if (prop.getName().equals("\"warehouse_floor_21\"")) {
    //        shapeRenderer.setColor(Color.GREEN);
    //      } else {
    //        shapeRenderer.setColor(Color.YELLOW);
    //      }
    //      GridPoint gridPoint = prop.getGridPoint();
    //      float x = (float) (gridPoint.getX() * size);
    //      float z = (float) (gridPoint.getZ() * size);
    //      shapeRenderer.circle(x, z, size);
    //    }
    //    shapeRenderer.end();
  }

  private void createModels() {
    instances = new ArrayList<>();
    ModelBuilder modelBuilder = new ModelBuilder();
    List<PropInstance> props = mapDef.getProps();

    for (PropInstance prop : props) {
      PropDef propDef = propMap.get(prop.getName());
      if (propDef != null) {
        PropView view = propDef.getViews().get(prop.getOrientation());
        if (view != null) {
          PropRender render = view.getRender();
          RenderBounds bounds = render.getBounds();
          if (bounds != null) {
            Material mat;
            if (propDef.getMapLayer() == MapLayer.MAPLAYER_PROPS) {
              mat = new Material(ColorAttribute.createDiffuse(Color.GREEN));
            } else {
              mat = new Material(ColorAttribute.createDiffuse(Color.YELLOW));
            }
            Model model = modelBuilder.createBox(bounds.getWidth(), bounds.getHeight(), bounds.getDepth(), mat,
                Usage.Position | Usage.Normal);
            GridPoint gp = prop.getGridPoint();
            Vector3 pos = new Vector3(gp.getX(), gp.getY(), gp.getZ());
            instances.add(new ModelInstance(model, pos));
            System.out.println("model added");
          } else {
            System.out.println("no bounds");
          }
        } else {
          System.out.println("no view");
        }
      } else {
        System.out.println("no prop def for '" + prop.getName() + "'");
      }
    }

  }

  private void createInputProcessor() {
    this.camController = new Controller(cam);
    Gdx.input.setInputProcessor(camController);
  }

  private static void maxTexMem() {
    IntBuffer buf = BufferUtils.newIntBuffer(16);
    Gdx.gl.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, buf);
    int maxSize = buf.get(0);
    System.out.println("Max Texture Size: " + maxSize);

    long javaHeap = Gdx.app.getJavaHeap();
    long nativeHeap = Gdx.app.getNativeHeap();
    System.out.println("Java heap: " + javaHeap + ", native: " + nativeHeap);
  }

  @Override
  public void resize(int width, int height) {
    System.out.println("Resize to " + width + "x" + height);
  }

  @Override
  public void render() {
    clear();
    camController.update();
    drawMap();
    createGrid();
  }

  private void clear() {
    //    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    //    cam.update();
  }

  private void drawMap() {
    modelBatch.begin(cam);
    modelBatch.render(instances, environment);
    modelBatch.end();
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {
    modelBatch.dispose();
  }
}
