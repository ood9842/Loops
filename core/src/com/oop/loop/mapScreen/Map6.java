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
import com.oop.loop.sprite.NPC;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Chetsada Chaiprasop on 12/13/2016.
 */
public class Map6 implements Screen {
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
    private int true_gate = 0;
    private int get_gate = 0;
    private int door = 0;
    //player
    private Hero player;
    private NPC hm;
    private float Px=30*10,Py=30*17;

    private boolean show = false;
    private ArrayList<Texture> mesg;
    private int order = 0;

    public Map6(SpriteBatch batch)
    {
        //receive graphic from Loops class
        this.batch = batch;
        //set camera and viewport
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SIZE,SIZE,gameCam);
        //set map
        map = new Wolrd_Map();
        paintMap = map.getMap(Wolrd_Map.QUIZ_MAP5);
        not_pass = map.getReg(Wolrd_Map.QUIZ_MAP5);
        gameCam.position.set(SIZE/2,SIZE/2,0);//SIZE window / 2 this is pattern
        //create gate and setting gate
        gate_left = new Rectangle(0,270,GRID_CELL,GRID_CELL*2);
        gate_right = new Rectangle(570,270,GRID_CELL,GRID_CELL*2);
        gate_up = new Rectangle(270,570,GRID_CELL*2,GRID_CELL);
        gate_down = new Rectangle(270,0,GRID_CELL*2,GRID_CELL);
        //set true gate
        Random rand = new Random();
        true_gate = Math.abs(rand.nextInt()%4)+1;
        //create player
        player = new Hero(this.batch);
        player.create();
        player.setObjPlayerPosition((int)Px,(int)Py);

        hm = new NPC(this.batch);
        hm.setting_Position("new pumkin\\55.png",30*7,(30*11));
        hm.create();
        hm.setting_size(60,60);
        not_pass.add(hm.getObjPlayer());
        //create chat box
        mesg = new ArrayList<Texture>();
        for(int i=9 ; i<11 ; i++) {
            mesg.add(new Texture(Gdx.files.internal("message\\map6\\pumpkin "+(i+1)+".jpg")));
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
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            //render at camera and paint map
            batch.setProjectionMatrix(gameCam.combined);
            paintMap.render();
            //draw player
            if(!show) {
                batch.begin();
                hm.render();
            player.updateHero(delta);
            player.renderHero(delta);
            process();
            batch.end();
        }else{
                batch.begin();
                batch.draw(mesg.get(order), 0, 0);
                batch.draw(new Texture("new pumkin\\55.png"), 30*7,(30*11),60,60);
                batch.draw(player.getState(), player.getObjectPositionX(), player.getObjectPositionY(),30,30);
                if(Gdx.input.isKeyJustPressed(Input.Keys.X))
                {
                    order++;
                }
                if(order>1){
                    show=false;
                }
                batch.end();
            }
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
            get_gate = 1;
        }
        if(gate_right.overlaps(player.getObjPlayer()))
        {
            get_gate = 2;
        }
        if(gate_up.overlaps(player.getObjPlayer()))
        {
            get_gate = 3;
        }
        if(gate_down.overlaps(player.getObjPlayer()))
        {
            get_gate = 4;
        }
        //check true gate
        if(get_gate!=true_gate&&get_gate!=0)
        {
            door = 2;
        }
        if(get_gate==true_gate)
        {
            door = 1;
        }
        if(hm.getObjPlayer().overlaps(player.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            show=true;
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

    public int changeMap()
    {
        return door;
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
