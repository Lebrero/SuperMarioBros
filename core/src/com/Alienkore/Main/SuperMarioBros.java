package com.Alienkore.Main;

import com.Alienkore.Screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SuperMarioBros extends Game {
	public SpriteBatch batch;
	Texture img;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 204;
	public static final float PPM = 100;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));

	}

	@Override
	public void render() {
		super.render();
	}
}
