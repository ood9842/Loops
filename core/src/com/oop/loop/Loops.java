package com.oop.loop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.oop.loop.mapScreen.*;

public class Loops extends Game {
	private SpriteBatch batch;
	private Start_Screen start;
	private End_Screen end;
    private Map1 m1;
	private Map2 m2;
	private Map3 m3;
	private Map4 m4;
	private Map5 m5;
	private Map6 m6;
	//scene
	private Die_scene die;
	@Override
	public void create () {
		batch = new SpriteBatch();
		die = new Die_scene(batch);
		start = new Start_Screen(this);
		end = new End_Screen(this);
		m1 = new Map1(batch);
		m2 = new Map2(batch);
		m3 = new Map3(batch);
		m4 = new Map4(batch);
		m5 = new Map5(batch);
		m6 = new Map6(batch);
		this.setScreen(start);
	}

	@Override
	public void render () {
		super.render();
		process();
	}

	private void process()
	{
		//start map
		if(start.changeMap()==1)
		{
			this.setScreen(m1);
			start = new Start_Screen(this);
		}
		if(start.changeMap()==0)
		{
			Gdx.app.exit();
		}
		//map 1
		if(m1.changeMap())
		{
			this.setScreen(m2);
			m1 = new Map1(batch);
		}
		//map 2
		if(m2.changeMap())
		{
			this.setScreen(m3);
			m2 = new Map2(batch);
		}
		//map 3
		if(m3.changeMap())
		{
			this.setScreen(m4);
			m3 = new Map3(batch);
		}
		//map 4
		if(m4.changeMap())
		{
			this.setScreen(m5);
			m4 = new Map4(batch);
		}
		//map 5
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
		//map 6
		if(m6.changeMap()==1)
		{
			//this.setScreen(end);
            end.run();
			this.setScreen(end);
			m6 = new Map6(batch);
		}
		if(m6.changeMap()==2)
		{
			this.setScreen(m1);
			m6 = new Map6(batch);
		}
		//end scene
		if(end.changeMap())
		{
			this.setScreen(start);
			end = new End_Screen(this);
		}
	}

	public SpriteBatch gatBatch()
	{
		return this.batch;
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
	/*m1 = new Map1(batch);
			die.run();
			System.out.println(die.toString());
			do {
				this.setScreen(die);
			}while(TimeUtils.nanoTime() - die.timeStart()> 1000000000);
			this.setScreen(m1);*/
}
