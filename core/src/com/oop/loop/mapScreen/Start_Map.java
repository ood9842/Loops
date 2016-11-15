package com.oop.loop.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.loop.sprite.Hero;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Chetsada Chaiprasop on 11/12/2016.
 */
public class Start_Map implements Screen{
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
    private Rectangle gate_right;
    private Rectangle gate_up;
    private Rectangle gate_down;
    private int get_gate;
    private int true_gate;
    private Random rand;
    //player
    private Hero player;
    private int count = -1;
    private float Px,Py;
    private String current_map;
    private String late_enter;

    public Start_Map(SpriteBatch batch)
    {
        //receive graphic from Loops class
        this.batch = batch;
        //set camera and viewport
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SIZE,SIZE,gameCam);
        //set map
        map = new Wolrd_Map();
        paintMap = map.getMap(Wolrd_Map.STRAT_MAP);
        not_pass = map.getReg(Wolrd_Map.STRAT_MAP);
        gameCam.position.set(SIZE/2,SIZE/2,0);//SIZE window / 2 this is pattern
        //create gate and setting gate
        gate_left = new Rectangle(0,270,GRID_CELL,GRID_CELL*2);
        gate_right = new Rectangle(SIZE-30,270,GRID_CELL,GRID_CELL*2);
        gate_up = new Rectangle(270,SIZE-30,GRID_CELL*2,GRID_CELL);
        gate_down = new Rectangle(270,0,GRID_CELL*2,GRID_CELL);
        rand = new Random();
        //set true gate
        true_gate = 0;//start map is has one gate left
        get_gate = -1;
        //create player
        player = new Hero(batch);
        player.create();
        player.setObjPlayerPosition(300,300);
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
        batch.end();
        process();//process
    }

    private void process() {//call all private function in this class
        checkGate();
        if(get_gate == true_gate)
        {
            true_gate = Math.abs(rand.nextInt()%4);
            count++;
            get_gate = -1;
            if(count == 3)
            {
                count = 0;
                player.setObjPlayerPosition(532,284);//equal gate
                paintMap = map.getMap(Wolrd_Map.QUIZ_MAP1);
                not_pass = map.getReg(Wolrd_Map.QUIZ_MAP1);
            }
            else
            {
                paintMap = map.getMap(Wolrd_Map.DEFAULT_MAP);
                not_pass = map.getReg(Wolrd_Map.DEFAULT_MAP);
                if(late_enter =="LEFT")
                {
                    player.setObjPlayerPosition(532, (int) Py);
                }
                if(late_enter =="RIGHT")
                {
                    player.setObjPlayerPosition(32, (int) Py);
                }
                if(late_enter =="UP")
                {
                    player.setObjPlayerPosition((int) Px,30);
                }
                if(late_enter =="DOWN")
                {
                    player.setObjPlayerPosition((int) Px,540);
                }
                late_enter = "";
            }
        }
        else if(get_gate != true_gate && get_gate != -1)
        {
            late_enter = "";
            true_gate = 0;
            count = 0;
            get_gate = -1;
            player.setObjPlayerPosition(532, 286);//equal gate
            paintMap = map.getMap(Wolrd_Map.STRAT_MAP);
            not_pass = map.getReg(Wolrd_Map.STRAT_MAP);
        }
        drawGate();
    }

    private void checkGate() {
        Rectangle tmp = new Rectangle();
        for (Rectangle not_pas : not_pass) {

            if (not_pas.overlaps(player.getObjPlayer())) {

                tmp = not_pas;
                player.walkAndCheck(tmp,tmp.getX(), tmp.getY(), tmp.getWidth(), tmp.getHeight());
                Gdx.app.log("V",  not_pas.getX()+" "+  not_pas.getY()+" "+   not_pas.getWidth()+" "+  not_pas.getHeight());
                Gdx.app.log("P ", player.getObjectPositionX()+" "+  player.getObjectPositionY());


            }
            if(!tmp.overlaps(player.getObjPlayer())){

                player.VELOCITY_UP =  player.VELOCITY_RIGHT =   player.VELOCITY_LEFT =    player.VELOCITY_DOWN  = 150;
            }
            else {
                player.CANRIGHT = true;
                player.CANUP =true;
                player.CANLEFT = true;
                player.CANDOWN = true;
            }
        }
        if(gate_left.overlaps(player.getObjPlayer()))
        {
            Px = player.getObjectPositionX();
            Py = player.getObjectPositionY();
            get_gate = 0;
            late_enter = "LEFT";
        }
        else if(gate_right.overlaps(player.getObjPlayer()))
        {
            Px = player.getObjectPositionX();
            Py = player.getObjectPositionY();
            get_gate = 1;
            late_enter = "RIGHT";
        }
        else if(gate_up.overlaps(player.getObjPlayer()))
        {
            Px = player.getObjectPositionX();
            Py = player.getObjectPositionY();
            get_gate = 2;
            late_enter = "UP";
            System.out.println("Touch");
        }
        else if(gate_down.overlaps(player.getObjPlayer()))
        {
            Px = player.getObjectPositionX();
            Py = player.getObjectPositionY();
            get_gate = 3;
            late_enter = "DOWN";
            System.out.println("Touch");
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
    //function TOOL
    private void drawGate() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(gate_left.getX(),gate_left.getY(),gate_left.getWidth(),gate_left.getHeight());
        shapeRenderer.rect(gate_right.getX(),gate_right.getY(),gate_right.getWidth(),gate_right.getHeight());
        shapeRenderer.rect(gate_up.getX(),gate_up.getY(),gate_up.getWidth(),gate_up.getHeight());
        shapeRenderer.rect(gate_down.getX(),gate_down.getY(),gate_down.getWidth(),gate_down.getHeight());
        for(Rectangle temp:not_pass)
        {
            shapeRenderer.rect(temp.getX(),temp.getY(),temp.getWidth(),temp.getHeight());
        }
        shapeRenderer.end();
        //System.out.println(true_gate+" "+count);
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
