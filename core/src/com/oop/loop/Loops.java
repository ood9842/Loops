package com.oop.loop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oop.loop.mapScreen.*;

import java.util.ArrayList;

public class Loops extends Game {
	private SpriteBatch batch;
    private Map1 m1;
	private Map2 m2;
	private Map3 m3;
	private Map4 m4;
	private Map5 m5;
	private Map6 m6;
	@Override
	public void create () {
		batch = new SpriteBatch();
		m1 = new Map1(batch);
		m2 = new Map2(batch);
		m3 = new Map3(batch);
		m4 = new Map4(batch);
		m5 = new Map5(batch);
		m6 = new Map6(batch);
		this.setScreen(m1);
	}

	@Override
	public void render () {
		super.render();
		process();
	}

	private void process()
	{
		if(m1.changeMap())
		{
			this.setScreen(m2);
			m1 = new Map1(batch);
		}
		if(m2.changeMap())
		{
			this.setScreen(m3);
			m2 = new Map2(batch);
		}
		if(m3.changeMap())
		{
			this.setScreen(m4);
			m3 = new Map3(batch);
		}
		if(m4.changeMap())
		{
			this.setScreen(m5);
			m4 = new Map4(batch);
		}
		if(m5.changeMap()==1)
		{
			this.setScreen(m6);
			m5 = new Map5(batch);
		}
		if(m5.changeMap()==2)
		{
			this.setScreen(m1);
			m5 = new Map5(batch);
		}
		if(m6.changeMap()==1)
		{
			//this.setScreen(end);
			m6 = new Map6(batch);
		}
		if(m6.changeMap()==2)
		{
			this.setScreen(m1);
			m6 = new Map6(batch);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
