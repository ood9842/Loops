package com.oop.loop.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.oop.loop.Loops;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Chetsada Chaiprasop on 12/14/2016.
 */
public class Start_Screen implements Screen {
    //graphic batch
    private Loops game;
    //image
    private Texture background;
    //camera
    private OrthographicCamera camera;
    //stage
    private Stage stage;
    private Skin st;

    public Start_Screen(final Loops gameReseice)
    {
        this.game = gameReseice;

        camera = new OrthographicCamera();//may be this is use to lock the character in position window
        camera.setToOrtho(false, 800, 480);

        stage = new Stage(new StretchViewport(800, 480));//not sure this is fix center the stage in window
        Gdx.input.setInputProcessor(stage);//fix get input to the this stage

        background = new Texture(Gdx.files.internal("scene\\start scene.jpg"));

        st = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton buttonStart = new TextButton("START", st);
        buttonStart.setWidth(200);
        buttonStart.setHeight(50);
        buttonStart.setPosition(800 / 2 - 200 / 2, 300);


        //checking input
        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);//dummy meteor
                game.setScreen(new Map1(game.gatBatch()));
            }
        });
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.gatBatch().begin();
        this.game.gatBatch().draw(background,0,0);
        this.game.gatBatch().end();
        stage.act(Gdx.graphics.getDeltaTime());//?
        stage.draw();//draw object in stage
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
