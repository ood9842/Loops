package com.oop.loop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.loop.screen.Game_Frame;
import com.oop.loop.sprite.Hero;
import com.oop.loop.sprite.Item;

public class Loops extends Game {
	public SpriteBatch batch;
	Hero hero;
    Item item;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new Game_Frame(batch));
		hero = new Hero(batch);

        item = new Item(batch,"b6");


		batch = new SpriteBatch();
		hero.create();
        hero.setObjPlayerPosition(0,0);
        item.create();
        item.setObjPlayerPosition(40*4,40*4);

	}

	@Override
	public void render () {
		super.render();
		float deltaTime = Gdx.graphics.getDeltaTime();
		batch.begin();

		hero.updateHero(deltaTime);
		hero.renderHero(deltaTime);
        item.renderItem(deltaTime);
        if(item.overlaps(hero.getObjPlayer())){
            System.out.println("Object is overlaps");
            System.out.println("Grid coordinate X: " + (int)(hero.getObjectPositionX()/40) + " Y: " + (int)(hero.getObjectPositionY()/40));
        }
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
