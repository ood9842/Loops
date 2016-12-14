package com.oop.loop.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.loop.sprite.Hero;

import java.util.ArrayList;

/**
 * Created by Chetsada Chaiprasop on 12/13/2016.
 */
public class Map3 implements Screen {
    //window property
    private static final int SIZE = 600;
    private static final int GRID_CELL = 30;
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
    //gate
    private Rectangle gate_left;
    private boolean change=false;
    //player
    private Hero player;
    private float Px=300,Py=300;

    public Map3(SpriteBatch batch)
    {
        //receive graphic from Loops class
        this.batch = batch;
        //set camera and viewport
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SIZE,SIZE,gameCam);
        //set map
        map = new Wolrd_Map();
        paintMap = map.getMap(Wolrd_Map.QUIZ_MAP2);
        not_pass = map.getReg(Wolrd_Map.QUIZ_MAP2);
        gameCam.position.set(SIZE/2,SIZE/2,0);//SIZE window / 2 this is pattern
        //create gate and setting gate
        gate_left = new Rectangle(0,270,GRID_CELL,GRID_CELL*2);
        //create player
        player = new Hero(this.batch);
        player.create();
        player.setObjPlayerPosition((int)Px,(int)Py);
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
        batch.begin();
        player.updateHero(delta);
        player.renderHero(delta);
        process();
        drawGate();
        batch.end();
    }

    private void process() {
        //not_pass edge
        Rectangle tmp = new Rectangle();
        for (Rectangle not_pas : not_pass) {
            if (not_pas.overlaps(player.getObjPlayer())) {
                tmp = not_pas;
                player.walkAndCheck(tmp,tmp.getX(), tmp.getY(), tmp.getWidth(), tmp.getHeight());
            }
            if(!tmp.overlaps(player.getObjPlayer())){
                player.VELOCITY_UP =  player.VELOCITY_RIGHT =   player.VELOCITY_LEFT =    player.VELOCITY_DOWN  = 150;
            }
            else
            {
                player.CANRIGHT = true;
                player.CANUP =true;
                player.CANLEFT = true;
                player.CANDOWN = true;
            }
        }
        //check gate change map
        if(gate_left.overlaps(player.getObjPlayer()))
        {
            change = true;
        }
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

    public boolean changeMap()
    {
        return change;
    }

    //function TOOL
    private void drawGate() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(gate_left.getX(),gate_left.getY(),gate_left.getWidth(),gate_left.getHeight());
        for(Rectangle temp:not_pass)
        {
            shapeRenderer.rect(temp.getX(),temp.getY(),temp.getWidth(),temp.getHeight());
        }
        shapeRenderer.end();
    }

    private void drawGrid() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int x = 0; x < SIZE; x += GRID_CELL) {
            for (int y = 0; y < SIZE; y += GRID_CELL) {
                shapeRenderer.rect(x,y, GRID_CELL, GRID_CELL);
            }
        }
        shapeRenderer.end();
    }

    private void showPositionPlayer() {
        System.out.println(player.getObjPlayer());
    }
}
