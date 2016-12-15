package com.oop.loop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
	private Map_end end_map;
	//scene
	private Die_scene die;
	private Die_scene2 die2;

	private Sound sound;
	private boolean toggle=true;

	@Override
	public void create () {
		batch = new SpriteBatch();
		die = new Die_scene(batch);
		die2 = new Die_scene2(batch);
		start = new Start_Screen(this);
		end = new End_Screen(this);
		m1 = new Map1(batch);
		m2 = new Map2(batch);
		m3 = new Map3(batch);
		m4 = new Map4(batch);
		m5 = new Map5(batch);
		m6 = new Map6(batch);
		end_map = new Map_end(batch);

		sound = Gdx.audio.newSound(Gdx.files.internal("sound\\piano.wav"));

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
		{	if(toggle){
			sound.play();
			toggle=false;
			}
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
		if(m3.changeMap()==1)
		{
			this.setScreen(m4);
			m3 = new Map3(batch);
		}
		if(m3.changeMap()==2)
		{
			sound.stop();
			die.run();
			this.setScreen(die);
			m3 = new Map3(batch);
		}
		//map 4
		if(m4.changeMap()==1)
		{
			this.setScreen(m5);
			m4 = new Map4(batch);
		}
		if(m4.changeMap()==2)
		{	sound.stop();
			die2.run();
			this.setScreen(die2);
			m4 = new Map4(batch);
		}
		//map 5
		if(m5.changeMap()==1)
		{
			this.setScreen(m6);
			m5 = new Map5(batch);
		}
		if(m5.changeMap()==2)
		{	sound.stop();
			die.run();
			this.setScreen(die);
			m5 = new Map5(batch);
		}
		//map 6
		if(m6.changeMap()==1)
		{
			this.setScreen(end_map);
			m6 = new Map6(batch);
		}
		if(m6.changeMap()==2)
		{	sound.stop();
			die.run();
			this.setScreen(die);
			m6 = new Map6(batch);
		}
		//end map
		if(end_map.changeMap())
		{	sound.stop();
			this.setScreen(end);
			end.run();
			end_map = new Map_end(batch);
		}
		//end scene
		if(end.changeMap())
		{	sound.stop();
			toggle=true;
			this.setScreen(start);
			end = new End_Screen(this);
		}
		//die scene
		if(die.changeMap())
		{	sound.stop();
			this.setScreen(m1);
			toggle=true;
			if(toggle){
				sound.play();
				toggle=false;
			}
			die = new Die_scene(batch);
		}
		if(die2.changeMap())
		{	sound.stop();
			this.setScreen(m1);
			toggle=true;
			if(toggle){
				sound.play();
				toggle=false;
			}
			die2 = new Die_scene2(batch);
		}
	}

	public SpriteBatch gatBatch()
	{
		return this.batch;
	}

	@Override
	public void dispose () {
		batch.dispose();
		sound.dispose();

	}
}
