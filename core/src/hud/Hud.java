package hud;

import com.alienkorp.supermariobros.SuperMarioBros;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {

	public Stage stage;
	private Viewport viewport;
	private Integer worldTimer;
	private float timeCount;
	private Integer score;

	Label countdownLabel;
	Label scoreLabel;
	Label timeLabel;
	Label levelLabel;
	Label worldLabel;
	Label marioLabel;

	/**
	 * Constructor
	 * 
	 * @param sb
	 *            SpriteBatch
	 */
	public Hud(SpriteBatch sb) {
		// Inicializamos nuestro Hud
		inicializarHud(sb);

	}

	/**
	 * Método encargado de inicializar las variables
	 * 
	 * @param sb
	 *            SpriteBatch
	 */
	private void inicializarHud(SpriteBatch sb) {
		// 1. Preparamos nuestras variables en pantalla
		worldTimer = 300;
		timeCount = 0;
		score = 0;
		// 2. Preparamos nuestro viewport
		viewport = new StretchViewport(SuperMarioBros.V_WIDTH, SuperMarioBros.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);

		// 3. Creamos la Tabla de Datos
		crearTabla();
	}

	/**
	 * Método encargado de crear la tabla que contiene todos los datos del juego
	 */
	private void crearTabla() {
		Table table = new Table();
		// Nos situa arriba y en el centro del todo de la tabla
		table.top();
		// Hacemos la tabla del tamaño de nuestra Stage
		table.setFillParent(true);

		countdownLabel = new Label(String.format("%03d", worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		table.add(marioLabel).expandX().padTop(5);
		table.add(worldLabel).expandX().padTop(5);
		table.add(timeLabel).expandX().padTop(5);

		table.row();

		table.add(scoreLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(countdownLabel).expandX();

		stage.addActor(table);
	}

}
