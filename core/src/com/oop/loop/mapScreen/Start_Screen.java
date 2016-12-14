package com.oop.loop.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.loop.Loops;


/**
 * Created by Chetsada Chaiprasop on 12/14/2016.
 */
public class Start_Screen implements Screen {
    //window property
    private static final int SIZE = 600;
    //camera
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    //graphic batch
    private Loops game;
    //sound
    private Sound sound;
    //image
    private Texture background;
    private Texture bottom_start;
    private Texture bottom_exit;
    private static int POINT_STARTX = 250,POINT_STARTY = 200;
    private static int POINT_ENDX = 250,POINT_ENDY = 130;
    //selection
    private Texture select;
    private boolean cur_select = true;
    private int change = -1;

    public Start_Screen(final Loops gameReseice)
    {
        this.game = gameReseice;

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SIZE,SIZE,gameCam);

        background = new Texture(Gdx.files.internal("scene\\start scene.jpg"));
        bottom_start = new Texture(Gdx.files.internal("sprite\\menu\\start.png"));
        bottom_exit = new Texture(Gdx.files.internal("sprite\\menu\\exit.png"));
        select = new Texture(Gdx.files.internal("sprite\\menu\\select.png"));
        sound = Gdx.audio.newSound(Gdx.files.internal("sound\\start.wav"));
    }
    @Override
    public void show() {
        sound.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.gatBatch().begin();
        game.gatBatch().draw(background,0,0);
        game.gatBatch().draw(bottom_start,POINT_STARTX,POINT_STARTY);
        game.gatBatch().draw(bottom_exit,POINT_ENDX,POINT_ENDY);
        if(cur_select)
        {
            game.gatBatch().draw(select,POINT_STARTX,POINT_STARTY);
        }
        else
        {
            game.gatBatch().draw(select,POINT_ENDX,POINT_ENDY);
        }
        game.gatBatch().end();

        check_bottom();
    }

    private void check_bottom() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)||Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
        {
            cur_select = !cur_select;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            if(cur_select)
            {
                change = 1;
                this.dispose();
            }
            else
            {
                change = 0;
                this.dispose();
            }
        }
    }

    public int changeMap()
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
        sound.dispose();
    }
}
