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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.loop.sprite.Hero;
import com.oop.loop.sprite.NPC;

import java.util.ArrayList;

/**
 * Created by Chetsada Chaiprasop on 12/13/2016.
 */
public class Map5 implements Screen {
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
    private Rectangle gate_up;
    private Rectangle gate_down;
    private int door = 0;
    //player
    private Hero player;
    private float Px=30*19,Py=30*9;
    private NPC pk1;
    private NPC pk2;
    private NPC pk3;
    private NPC pk4;
    int[] allTalk;
    int talk;

    private boolean show = false;
    private int order = 0;
    private ArrayList<Texture> mesg;

    public Map5(SpriteBatch batch)
    {
        //receive graphic from Loops class
        this.batch = batch;
        //set camera and viewport
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SIZE,SIZE,gameCam);
        //set map
        map = new Wolrd_Map();
        paintMap = map.getMap(Wolrd_Map.QUIZ_MAP4);
        not_pass = map.getReg(Wolrd_Map.QUIZ_MAP4);
        gameCam.position.set(SIZE/2,SIZE/2,0);//SIZE window / 2 this is pattern
        //create gate and setting gate
        gate_up = new Rectangle(180,460,GRID_CELL*5,GRID_CELL*2);
        gate_down = new Rectangle(180,-40,GRID_CELL*5,GRID_CELL*2);//true
        //create player
        player = new Hero(this.batch);
        player.create();
        player.setObjPlayerPosition((int)Px,(int)Py);

        //create NPC
        pk1 = new NPC(this.batch);
        pk1.setting_Position("sprite\\NPC\\02.png",30*10,(30*11));
        pk1.create();
        pk1.setting_size(50,50);
        not_pass.add(pk1.getObjPlayer());

        pk2 = new NPC(this.batch);
        pk2.setting_Position("sprite\\NPC\\02.png",30*12,(30*11));
        pk2.create();
        pk2.setting_size(50,50);
        not_pass.add(pk2.getObjPlayer());

        pk3 = new NPC(this.batch);
        pk3.setting_Position("sprite\\NPC\\02.png",30*15,(30*11));
        pk3.create();
        pk3.setting_size(50,50);
        not_pass.add(pk3.getObjPlayer());

        pk4 = new NPC(this.batch);
        pk4.setting_Position("sprite\\NPC\\02.png",30*17,(30*11));
        pk4.create();
        pk4.setting_size(50,50);
        not_pass.add(pk4.getObjPlayer());

        mesg = new ArrayList<Texture>();
        for(int i=5;i<9;i++)
        {
            mesg.add(new Texture("message\\map5\\pumpkin "+(i+1)+".jpg"));
        }

        allTalk = new int[5];
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
        if(!show){
        batch.begin();

        pk1.render();
        pk2.render();
        pk3.render();
        pk4.render();

        player.updateHero(delta);
        player.renderHero(delta);
        process();
        batch.end();     }
        else{

            batch.begin();
                batch.draw(new Texture("sprite\\NPC\\02.png"), 30*10, 30*11,50,50);
                batch.draw(new Texture("sprite\\NPC\\02.png"), 30*12, 30*11,50,50);
                batch.draw(new Texture("sprite\\NPC\\02.png"), 30*15, 30*11,50,50);
                batch.draw(new Texture("sprite\\NPC\\02.png"), 30*17, 30*11,50,50);
                batch.draw(player.getState(), player.getObjectPositionX(), player.getObjectPositionY(),30,30);
                batch.draw(mesg.get(order), 0, 0);   //q2  q1
            batch.end();
            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                show = false;
            }
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
        if(player.getObjectPositionY()>30*15){
            player.setObjPlayerPosition(player.getObjectPositionX(),30*15);
        }
        //check gate change map
        talk=0;
        for (int i=0;i<4;i++){
            talk += allTalk[i];
            Gdx.app.log("",""+talk);
      }
        if(talk==4) {
            if (gate_up.overlaps(player.getObjPlayer())) {
                door = 2;
            }
            if (gate_down.overlaps(player.getObjPlayer())) {
                door = 1;
            }
        }
        if(player.getObjPlayer().overlaps(pk1.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z) ){
            order = 0;
            allTalk[0] = 1;
            show = true;
        }
        if(player.getObjPlayer().overlaps(pk2.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z) ){
            order = 1;
            allTalk[1] = 1;
            show = true;
        }
        if(player.getObjPlayer().overlaps(pk3.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z) ){
            order = 2;
            allTalk[2] = 1;
            show = true;
        }
        if(player.getObjPlayer().overlaps(pk4.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z) ){
            order = 3;
            allTalk[3] = 1;
            show = true;
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
