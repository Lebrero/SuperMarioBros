package screens;

import org.w3c.dom.css.Rect;

import com.alienkorp.supermariobros.SuperMarioBros;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;

import hud.Hud;

public class PlayScreen implements Screen {

	private SuperMarioBros SuperMario;
	private OrthographicCamera mainCamera;
	private FitViewport gamePort;
	private PolygonShape shape;
	private BodyDef bdef;

	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer rendered;

	private Hud hud;

	private World world;
	private Box2DDebugRenderer b2dr;

	FixtureDef fdef = new FixtureDef();
	Body body;

	public PlayScreen(SuperMarioBros SuperMario) {

		this.SuperMario = SuperMario;

		hud = new Hud(SuperMario.batch);
		// Creamos la camara
		mainCamera = new OrthographicCamera();
		// Creamos el viewPort, a partir de la camara
		gamePort = new FitViewport(SuperMarioBros.V_WIDTH, SuperMarioBros.V_HEIGHT, mainCamera);
		mainCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		// Inicializamos nuestro mundo
		inicializarWorld();
		// Creamos el mapa
		crearMapa();
		// Creamos las colisiones
		crearTiles();

	}

	/**
	 * Método que se encarga de inicializar todo lo necesario para crear el
	 * "World"
	 */
	private void inicializarWorld() {

		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();
		bdef = new BodyDef();
		shape = new PolygonShape();
	}

	/**
	 * Método encargado de crear las colisiones
	 */
	private void crearTiles() {

		// Recorreo todos los rectangulos que hay en mi mapa con los metodos
		// necesarios y los meto en object de tipo MapObject.

		// Ground
		crearColisiones(2);
		// Pipes
		crearColisiones(3);
		// Coins
		crearColisiones(4);
		// Bricks
		crearColisiones(5);
	}

	/**
	 * Método encargado de traducir la información de nuestro mapa, para
	 * estableces un sistema de colisiones
	 * 
	 * @param i
	 *            Indice de las capas del mapa
	 */
	private void crearColisiones(int i) {

		for (MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
			// Creo un rectangulo y hago "cast" para meterlos en un objeto de
			// tipo "Rectangle"
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			// Establecemos el tipo de Body que le corresponde, en este caso
			// "StaticBody"
			bdef.type = BodyDef.BodyType.StaticBody;
			// Definimos su posición a la del rectangulo que hemos obtenido
			// anteriormente
			bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
			// Finalmente creamos el body
			body = world.createBody(bdef);
			// Creamos nuestra shape
			shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
			// Añadimos la shape a nuestro fixture
			fdef.shape = shape;
			// Creamos nuestra fixture
			body.createFixture(fdef);
		}
	}

	/**
	 * Método encargado de crear el mapa
	 */
	private void crearMapa() {
		// Creamos el mapLoader
		mapLoader = new TmxMapLoader();
		// Asociamos en mapa a mapLoader,Load
		map = mapLoader.load("level1.tmx");
		rendered = new OrthogonalTiledMapRenderer(map);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		// separate our update logic from render
		update(delta);

		// Clear the game screen with Black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		SuperMario.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		rendered.render();
		b2dr.render(world, mainCamera.combined);
		hud.stage.draw();

	}

	public void update(float dt) {
		handleInput(dt);
		mainCamera.update();
		rendered.setView(mainCamera);
	}

	/**
	 * Método encargado de manejar
	 */
	private void handleInput(float dt) {
		if (Gdx.input.isTouched())
			mainCamera.position.x += 100 * dt;
	}

	@Override
	public void resize(int width, int height) {
		// Actualizamos el tamaño de la camara
		gamePort.update(width, height);
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
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
