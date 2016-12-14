package com.oop.loop.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.loop.Loops;

/**
 * Created by Chetsada Chaiprasop on 12/14/2016.
 */
public class End_Screen implements Screen{
    //window property
    private static final int SIZE = 600;
    //camera
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    //graphic batch
    private Loops game;
    //image
    private Texture background;
    private boolean change = false;
    //audio
    private Sound sound;

    public End_Screen(final Loops gameReseice)
    {
        this.game = gameReseice;

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SIZE,SIZE,gameCam);

        background = new Texture(Gdx.files.internal("scene\\end scene.jpg"));
        sound = Gdx.audio.newSound(Gdx.files.internal("sound\\end sound.wav"));
    }

    public void run()
    {
        sound.play();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.gatBatch().begin();
        game.gatBatch().draw(background,0,0);
        game.gatBatch().end();
        check_bottom();
    }

    private void check_bottom() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
        {
            change = true;
        }
    }

    public boolean changeMap()
    {
        return change;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
