package com.oop.loop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.loop.screen.Game_Frame;
import com.oop.loop.sprite.Hero;

public class Loops extends Game {
	public SpriteBatch batch;
	Hero hero;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new Game_Frame(batch));
		hero = new Hero(batch);
		batch = new SpriteBatch();
		hero.create();
	}

	@Override
	public void render () {
		super.render();
		float deltaTime = Gdx.graphics.getDeltaTime();
		batch.begin();

		hero.updateKoala(deltaTime);
		hero.renderKoala(deltaTime);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
