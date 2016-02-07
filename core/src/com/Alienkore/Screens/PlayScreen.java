package com.Alienkore.Screens;

import com.Alienkore.Hud.Hud;
import com.Alienkore.Main.SuperMarioBros;
import com.Alienkore.Sprites.Mario;
import com.alienkorp.Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayScreen implements Screen {

	private SuperMarioBros SuperMario;
	private OrthographicCamera mainCamera;
	private FitViewport gamePort;
	private Mario player;
	private TmxMapLoader mapLoader;
	private OrthogonalTiledMapRenderer rendered;
	private TiledMap map;
	private Hud hud;
	private World world;
	private Box2DDebugRenderer b2dr;

	public PlayScreen(SuperMarioBros SuperMario) {

		this.SuperMario = SuperMario;

		hud = new Hud(SuperMario.batch);
		// Creamos la camara
		mainCamera = new OrthographicCamera();
		// Creamos el viewPort, a partir de la camara
		crearViewPort();
		// Inicializamos nuestro mundo
		inicializarWorld();

		// Creamos el mapa
		crearMapa();

		player = new Mario(world);

		new B2WorldCreator(world, map);

	}

	/**
	 * Método encargado de crear nuestro ViewPort
	 */
	private void crearViewPort() {
		gamePort = new FitViewport(SuperMarioBros.V_WIDTH / SuperMarioBros.PPM,
				SuperMarioBros.V_HEIGHT / SuperMarioBros.PPM, mainCamera);
		mainCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
	}

	/**
	 * Método que se encarga de inicializar todo lo necesario para crear el
	 * "World"
	 */
	private void inicializarWorld() {

		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();

	}

	/**
	 * Método encargado de crear el mapa
	 */
	private void crearMapa() {
		// Creamos el mapLoader
		mapLoader = new TmxMapLoader();
		// Asociamos en mapa a mapLoader,Load
		map = mapLoader.load("level1.tmx");

		rendered = new OrthogonalTiledMapRenderer(map, 1 / SuperMarioBros.PPM);

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

		world.step(1 / 60f, 6, 2);
		mainCamera.position.x = player.b2body.getPosition().x;

		rendered.setView(mainCamera);
	}

	/**
	 * Método encargado de manejar
	 */
	private void handleInput(float dt) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.W))
			player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);

		if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2)
			player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

		if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2)
			player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
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
		map.dispose();
		rendered.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();
	}

}
