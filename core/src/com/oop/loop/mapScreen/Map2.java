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
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.loop.sprite.Hero;
import com.oop.loop.sprite.NPC;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

/**
 * Created by Chetsada Chaiprasop on 12/13/2016.
 */
public class Map2 implements Screen{
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
    private boolean isFound = false;
    //player
    private Hero player;
    private float Px=300,Py=300;
    //chat box and NPC
    private ArrayList<Texture> mesg;
    private int order = 0;
    private int counter = 0;
    private NPC pk1;
    private boolean talking = false;
    private int isTalk = 0;
    private int stage = 0;

    public Map2(SpriteBatch batch)
    {
        //receive graphic from Loops class
        this.batch = batch;
        //set camera and viewport
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SIZE,SIZE,gameCam);
        //set map
        map = new Wolrd_Map();
        paintMap = map.getMap(Wolrd_Map.QUIZ_MAP1);
        not_pass = map.getReg(Wolrd_Map.QUIZ_MAP1);
        gameCam.position.set(SIZE/2,SIZE/2,0);//SIZE window / 2 this is pattern
        //create gate and setting gate
        gate_left = new Rectangle(0,240,GRID_CELL,GRID_CELL*2);
        //create player
        player = new Hero(this.batch);
        player.create();
        player.setObjPlayerPosition((int)Px,(int)Py);
        //create NPC
        pk1 = new NPC(this.batch);
        pk1.setting_Position("new pumkin\\55.png",30*1,(30*8));
        pk1.create();
        pk1.setting_size(60,60);
        not_pass.add(pk1.getObjPlayer());
        //create chat box
        mesg = new ArrayList<Texture>();
        for(int i=0 ; i<3 ; i++) {
            mesg.add(new Texture(Gdx.files.internal("message\\map2\\0"+(i+1)+".jpg")));
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

        if(stage ==1)
        {
            pk1.changeLocate(30*1,30*11);
            batch.begin();
            batch.draw(mesg.get(2), 0, 0);

            batch.draw(new Texture(Gdx.files.internal("sprite\\boy\\b7.png")),player.getObjectPositionX(),player.getObjectPositionY(),32,32);
            batch.end();

            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                //order = 1;
                talking=false;
                stage=0;
               // counter++;
            }
        }

        if(!talking) {
            batch.begin();
            pk1.render();
            player.updateHero(delta);
            player.renderHero(delta);
            process();
            drawGate();
            batch.end();
        }
        if(talking)//script
        {
            batch.begin();
            batch.draw(mesg.get(order), 0, 0);
            batch.draw(new Texture(Gdx.files.internal("new pumkin\\55.png")),30*1,30*8,60,60);
            batch.draw(new Texture(Gdx.files.internal("sprite\\boy\\b7.png")),player.getObjectPositionX(),player.getObjectPositionY(),32,32);
            batch.end();

            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                order = 1;
                counter++;
            }
            if(counter>1)
            {
                talking = false;
                counter = 0;
                isTalk = 2;
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
        //check gate change map
        if(gate_left.overlaps(player.getObjPlayer())&&isFound)
        {
            change = true;
        }
        //
        if( Gdx.input.isKeyJustPressed(Input.Keys.X)&&pk1.getObjPlayer().overlaps(player.getObjPlayer())&&isTalk==2){
            stage = 1;
            isFound = true;
        }

        //communication
        if(pk1.getObjPlayer().overlaps(player.getObjPlayer())&& Gdx.input.isKeyJustPressed(Input.Keys.Z)){
                        talking = true;
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

    public void update(){}
}

