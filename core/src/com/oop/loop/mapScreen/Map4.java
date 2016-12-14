package com.oop.loop.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.loop.sprite.Chess;
import com.oop.loop.sprite.Hero;


import java.util.ArrayList;

/**
 * Created by Chetsada Chaiprasop on 12/13/2016.
 */
public class Map4 implements Screen {
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
    private int change= 0;
    //player
    private Hero player;
    private float Px=30*10,Py=30*10;
    int state =0;

    private Chess king;
    private Chess queen2;
    private Chess rook;
    private Chess queen1;
    private Chess knight;
    private Chess bishop;
    private String last_walk1 = "NONE";
    private String last_walk2 = "NONE";
    private String last_walk3 = "NONE";//die

    public Map4(SpriteBatch batch)
    {
        //receive graphic from Loops class
        //receive graphic from Loops class
        this.batch = batch;
        //set camera and viewport
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SIZE,SIZE,gameCam);
        //set map
        map = new Wolrd_Map();
        paintMap = map.getMap(Wolrd_Map.QUIZ_MAP3);
        not_pass = map.getReg(Wolrd_Map.QUIZ_MAP3);
        gameCam.position.set(SIZE/2,SIZE/2,0);//SIZE window / 2 this is pattern
        //create gate and setting gate
        gate_left = new Rectangle(0,270,GRID_CELL,GRID_CELL*2);
        //create player
        player = new Hero(this.batch);
        player.create();
        player.setObjPlayerPosition((int)Px,(int)Py);

        ////add chess/// begin 3*7 end 10*14
        king = new Chess(this.batch);
        king.setting("sprite\\chess\\5.png",30*9,30*8);
        king.create();
        queen2 = new Chess(this.batch);
        queen2.setting("sprite\\chess\\6.png",30*3,30*9);
        queen2.create();
        rook = new Chess(this.batch);
        rook.setting("sprite\\chess\\2.png",30*7,30*14);
        rook.create();
        knight = new Chess(this.batch);
        knight.setting("sprite\\chess\\3.png",30*9,30*13);
        knight.create();
        bishop = new Chess(this.batch);//fix it
        bishop.setting("sprite\\chess\\4.png",30*4,30*8);
        bishop.create();
        queen1 = new Chess(this.batch);
        queen1.setting("sprite\\chess\\6.png",30*7,30*7);
        queen1.create();
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
        ////add chess///
        king.render();
        rook.render();
        queen2.render();
        knight.render();
        bishop.render();
        queen1.render();
        process_chess();
        process();
        drawGate();
        batch.end();
    }

    private void process_chess() {
        //die case
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
        {
            knight.setting("sprite\\chess\\3.png",30*10,30*11);
            knight.create();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
        {
            queen2.setting("sprite\\chess\\6.png",30*10,30*9);
            queen2.create();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)&&Gdx.input.isKeyJustPressed(Input.Keys.LEFT));
        {
            king.setting("sprite\\chess\\5.png",30*9,30*9);
            king.render();
        }

        //case LEFT
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
        {
            queen2.setting("sprite\\chess\\6.png",30*9,30*9);
            queen2.create();
            last_walk1 = "LEFT";
        }
        //case LEFT LEFT
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)&&last_walk1.equals("LEFT"))
        {
            rook.setting("sprite\\chess\\2.png",30*7,30*10);
            rook.create();
            last_walk2 = "LEFT";
        }
        //case LEFT RIGHT
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)&&last_walk1.equals("LEFT"))
        {
            rook.setting("sprite\\chess\\2.png",30*10,30*14);
            rook.create();
            last_walk2 = "RIGHT";
        }

        //case LEFTRIGTH
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)&&Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
        {
            queen2.setting("sprite\\chess\\6.png",30*3,30*11);
            queen2.create();
            last_walk1 = "LEFTRIGHT";
        }
        //case LEFTRIGHT UP UPRIGHT
        if((Gdx.input.isKeyJustPressed(Input.Keys.UP)||(Gdx.input.isKeyJustPressed(Input.Keys.UP))
                &&Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))&&last_walk1.equals("LEFTRIGHT"))
        {
            queen2.setting("sprite\\chess\\6.png",30*10,30*11);
            queen2.create();
            last_walk2 = "UP UPRIGHT";
        }
        //case LEFTRIGHT UPLEFT
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)&&last_walk1.equals("LEFT"))
        {
            queen1.setting("sprite\\chess\\6.png",30*7,30*12);
            queen1.create();
            last_walk2 = "UPLEFT";
        }
       // if()
        if(!last_walk2.equals("NONE")&&Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
        {
            change = 2;
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
