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
    private int change = 0;
    private boolean show = false;
    private int order = 0;
    private ArrayList<Texture> mesg;
    //player
    private Hero player;
    private float Px=30*19,Py=30*9;

    boolean question = false;
    boolean talk = false;
    int ans=1;
    int qAns=1;
    private NPC pk1;
    private NPC pk2;
    private NPC pk3;
    private NPC pk4;
    private NPC pk;

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
        gate_left = new Rectangle(0,270,GRID_CELL-4,GRID_CELL*2);
        //create player
        player = new Hero(this.batch);
        player.create();
        player.setObjPlayerPosition((int)Px,(int)Py);

        //create NPC
        pk1 = new NPC(this.batch);
        pk1.setting_Position("new pumkin\\55.png",30*13,(30*12));
        pk1.create();
        pk1.setting_size(30,30);
        not_pass.add(pk1.getObjPlayer());

        //create NPC
        pk2 = new NPC(this.batch);
        pk2.setting_Position("new pumkin\\55.png",30*5,(30*12));
        pk2.create();
        pk2.setting_size(30,30);
        not_pass.add(pk2.getObjPlayer());

        //create NPC
        pk3 = new NPC(this.batch);
        pk3.setting_Position("new pumkin\\55.png",30*5,(30*4));
        pk3.create();
        pk3.setting_size(30,30);
        not_pass.add(pk3.getObjPlayer());

        //create NPC
        pk4 = new NPC(this.batch);
        pk4.setting_Position("new pumkin\\55.png",30*13,(30*4));
        pk4.create();
        pk4.setting_size(30,30);
        not_pass.add(pk4.getObjPlayer());

        pk = new NPC(this.batch);
        pk.setting_Position("sprite\\NPC\\01.png",30*1,(30*9));
        pk.create();
        pk.setting_size(60,64);
        not_pass.add(pk.getObjPlayer());

        mesg = new ArrayList<Texture>();
        for(int i=0;i<9;i++)
        {
            mesg.add(new Texture("message\\map3\\pumpkin "+(i+1)+".jpg"));
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
        if(!show){
            batch.begin();
            pk1.render();
            pk2.render();
            pk3.render();
            pk4.render();
            pk.render();
            player.updateHero(delta);
            player.renderHero(delta);
            process();
            batch.end();

        }
        else{
            batch.begin();
            if(!question){
                batch.draw(new Texture("new pumkin\\55.png"), 30*13, 30*12,30,30);
                batch.draw(new Texture("new pumkin\\55.png"), 30*5, 30*12,30,30);
                batch.draw(new Texture("new pumkin\\55.png"), 30*5, 30*4,30,30);
                batch.draw(new Texture("new pumkin\\55.png"), 30*13, 30*4,30,30);
            }if(order!=4){
            batch.draw(new Texture("sprite\\NPC\\01.png"), 30*1, 30*9,60,64);}
           if(order==4){
               batch.draw(new Texture("sprite\\NPC\\01.png"), 30*1, 30*11,60,64);}

            batch.draw(player.getState(), player.getObjectPositionX(), player.getObjectPositionY(),30,30);


            batch.draw(mesg.get(order), 0, 0);   //q2  q1

            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                show = false;

            }
            if(question){
                batch.draw(mesg.get(8), 0, 0);   //q2  q1
                if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    qAns--;
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    qAns++;
                }
                if(Math.abs(qAns%4)==0){
                    batch.draw(new Texture("sprite\\NPC\\02.png"), 30*13, 30*12,40,40);
                    batch.draw(new Texture("new pumkin\\55.png"), 30*5, 30*12,30,30);
                    batch.draw(new Texture("new pumkin\\55.png"), 30*5, 30*4,30,30);
                    batch.draw(new Texture("new pumkin\\55.png"), 30*13, 30*4,30,30);
                }
                else if(Math.abs(qAns%4)==1){
                    batch.draw(new Texture("new pumkin\\55.png"), 30*13, 30*12,30,30);
                    batch.draw(new Texture("sprite\\NPC\\02.png"), 30*5, 30*12,40,40);
                    batch.draw(new Texture("new pumkin\\55.png"), 30*5, 30*4,30,30);
                    batch.draw(new Texture("new pumkin\\55.png"), 30*13, 30*4,30,30);

                }
                else if(Math.abs(qAns%4)==2){
                    batch.draw(new Texture("new pumkin\\55.png"), 30*13, 30*12,30,30);
                    batch.draw(new Texture("new pumkin\\55.png"), 30*5, 30*12,30,30);
                    batch.draw(new Texture("sprite\\NPC\\02.png"), 30*5, 30*4,40,40);
                    batch.draw(new Texture("new pumkin\\55.png"), 30*13, 30*4,30,30);

                }
                else if(Math.abs(qAns%4)==3){
                    batch.draw(new Texture("new pumkin\\55.png"), 30*13, 30*12,30,30);
                    batch.draw(new Texture("new pumkin\\55.png"), 30*5, 30*12,30,30);
                    batch.draw(new Texture("new pumkin\\55.png"), 30*5, 30*4,30,30);
                    batch.draw(new Texture("sprite\\NPC\\02.png"), 30*13, 30*4,40,40);

                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                    //Gdx.app.log("",Math.abs(qAns%4)+" "+ans);
                    if(Math.abs(qAns%4)!=ans){
                        pk.changeLocate(30*1,30*11);         //q3
                        order=4;
                        talk = true;
                        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                            question = false;
                            show = false;
                        }
                    }
                    else{


                        change=2;


                    }
                    question = false;
                }

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
            change = 1;
        }
        if(player.getObjPlayer().overlaps(pk.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z) ){
            order = 5;
            question = true;
            show = true;
        }
        if(player.getObjPlayer().overlaps(pk1.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z) ){
            order = 0;
            show = true;
            question = false;
        }
        if(player.getObjPlayer().overlaps(pk2.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z) ){
            order = 1;
            show = true;
            question = false;
        }
        if(player.getObjPlayer().overlaps(pk3.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z) ){
            order = 2;
            show = true;
            question = false;
        }
        if(player.getObjPlayer().overlaps(pk4.getObjPlayer())&&Gdx.input.isKeyJustPressed(Input.Keys.Z) ){
            order = 3;
            show = true;
            question = false;
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