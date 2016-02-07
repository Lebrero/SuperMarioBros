package com.alienkorp.supermariobros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.PlayScreen;

public class SuperMarioBros extends Game {
	public SpriteBatch batch;
	Texture img;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 204;

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
