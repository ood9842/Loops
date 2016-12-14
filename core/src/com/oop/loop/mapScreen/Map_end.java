package com.oop.loop.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.loop.sprite.Hero;

import java.util.ArrayList;

/**
 * Created by Chetsada Chaiprasop on 12/14/2016.
 */
public class Map_end implements Screen {
    //window property
    private static final int SIZE = 600;
    private static final int GRID_CELL = 30;//may be use in future
    //Graphic receive from Main class
    private SpriteBatch batch;
    //TOOL
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    //game camera to cooperation map
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Wolrd_Map map;
    private OrthogonalTiledMapRenderer paintMap;
    private ArrayList<Rectangle> not_pass = new ArrayList<Rectangle>();
    //player
    private Hero player;
    private float Px=150,Py=150;
    //script
    private boolean show = false;
    private ArrayList<Texture> mesg;
    private int order = 0;
    //change
    private boolean change = false;

    public Map_end(SpriteBatch batch) {
        this.batch = batch;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SIZE, SIZE, gameCam);

        map = new Wolrd_Map();
        paintMap = map.getMap(Wolrd_Map.END_MAP);
        not_pass = map.getReg(Wolrd_Map.END_MAP);
        gameCam.position.set(SIZE/2,SIZE/2,0);//SIZE window / 2 this is pattern
        //script
        mesg = new ArrayList<Texture>();
        for(int i=0;i<5;i++)
        {
            mesg.add(new Texture("message\\end\\0"+(i+1)+".jpg"));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //set point to render map
        paintMap.setView(gameCam);
        //clear screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //render at camera and paint map
        batch.setProjectionMatrix(gameCam.combined);
        paintMap.render();
        //draw player
        if(show) {
           change = true;
        }
        if(!show)
        {
            batch.begin();
            batch.draw(mesg.get(order), 0, 0);
            batch.end();
            if(Gdx.input.isKeyJustPressed(Input.Keys.X))
            {
                order++;
            }
            if(order>4)
            {
                //create player before script
                player = new Hero(this.batch);
                player.create();
                player.setObjPlayerPosition((int)Px,(int)Py);
                show = true;
            }
        }
    }

    public boolean changeMap() {
        return change;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
