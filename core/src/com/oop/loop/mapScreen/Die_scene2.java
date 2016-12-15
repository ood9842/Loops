package com.oop.loop.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Chetsada Chaiprasop on 12/14/2016.
 */
public class Die_scene2 implements Screen{
    private Sound sound;
    private SpriteBatch batch;
    private Texture background;
    private long time_start;

    private  boolean change = false;

    public Die_scene2(SpriteBatch batch)
    {
        this.batch = batch;
        background = new Texture(Gdx.files.internal("scene\\die scene.jpg"));
        sound = Gdx.audio.newSound(Gdx.files.internal("sound\\dead sound 2.wav"));
    }

    public void run()
    {
        time_start = TimeUtils.nanoTime();
        sound.play();
    }

    public boolean changeMap()
    {
        return change;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
        {
            change = true;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
