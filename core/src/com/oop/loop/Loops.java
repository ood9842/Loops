package com.oop.loop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.loop.mapScreen.Start_Map;
import com.oop.loop.sprite.Hero;
import com.oop.loop.sprite.Item;

public class Loops extends Game {
	public SpriteBatch batch;
	Hero hero;
    Item item;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new Start_Map(batch));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
