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
    private boolean change=false;
    //player
    private Hero player;
    private float Px=30*11,Py=30*10;
    private String last_walk = "NONE";
    private int counter = 0;

    boolean challenge= false;
    int chessTurn = 0;
    int gamePosX = 30 * 10;
    int gamePosY = 30 * 10;
    Rectangle board;
    Texture boy;

    Chess bishop;
    Chess queen1;
    Chess queen2;
    Chess rook;
    Chess king;
    Chess knight;
    Chess myKing;

    public Map4(SpriteBatch batch)
    {
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
        ////chess board///
        board = new Rectangle();
        board.setPosition(30*3,30*7);
        board.setSize(30*8,30*8);

        boy = new Texture("sprite\\boy\\b15.png");

        ////add chess///
        bishop = new Chess(this.batch);//fix it
        bishop.setting("sprite\\chess\\4.png",30*4,30*8);
        bishop.create();
        queen1 = new Chess(this.batch);
        queen1.setting("sprite\\chess\\6.png",30*3,30*9);
        queen1.create();

        queen2 = new Chess(this.batch);
        queen2.setting("sprite\\chess\\2.png",30*6,30*7);
        queen2.create();

        rook = new Chess(this.batch);
        rook.setting("sprite\\chess\\2.png",30*7,30*14);
        rook.create();

        king = new Chess(this.batch);
        king.setting("sprite\\chess\\5.png",30*9,30*8);
        king.create();

        knight = new Chess(this.batch);
        knight.setting("sprite\\chess\\3.png",30*9,30*13);
        knight.create();

        myKing = new Chess(this.batch);
        myKing.setting("sprite\\chess\\11.png",30*16,30*9);
        myKing.create();
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

        queen1.render();
        queen2.render();
        rook.render();
        bishop.render();
        knight.render();
        king.render();
        //draw player

        if(challenge) {
            batch.begin();
            myKing.update(gamePosX,gamePosY);
            if(Gdx.input.isKeyJustPressed(Input.Keys.W)&&chessTurn==1){
                gamePosY += 30;
                if((myKing.getObjectPositionY()+30)>30*14)
                {
                    gamePosY -= 30;
                }
                chessTurn = 2;
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.X)&&chessTurn==1){
                gamePosY -= 30;
                if((myKing.getObjectPositionX()+30)<30*7)
                {
                    gamePosY += 30;
                }
                chessTurn = 2;
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.A)&&chessTurn==1){
                gamePosX -= 30;
                if((myKing.getObjectPositionX()+30)<30*3)
                {
                    gamePosX += 30;
                }
                chessTurn = 2;
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.D)&&chessTurn==1){
                gamePosX += 30;
                if((myKing.getObjectPositionX()+30)>30*10)
                {
                    gamePosX -= 30;
                }
                chessTurn = 2;
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.C)&&chessTurn==1){
                gamePosX += 30;
                gamePosY -= 30;
                if(myKing.getObjectPositionX()+30>30*10||myKing.getObjectPositionY()-30<30*7)
                {
                    gamePosX -= 30;
                    gamePosY += 30;
                }
                chessTurn = 2;
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.E)&&chessTurn==1){
                gamePosX += 30;
                gamePosY += 30;
                if(myKing.getObjectPositionX()+30>30*10||myKing.getObjectPositionY()+30>30*14)
                {
                    gamePosX -= 30;
                    gamePosY -= 30;
                }
                chessTurn = 2;
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.Z)&&chessTurn==1){
                gamePosX -= 30;
                gamePosY -= 30;
                if(myKing.getObjectPositionX()-30<30*3||myKing.getObjectPositionY()-30<30*7)
                {
                    gamePosX += 30;
                    gamePosY += 30;
                }
                chessTurn = 2;
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.Q)&&chessTurn==1){
                gamePosX -= 30;
                gamePosY += 30;
                if(myKing.getObjectPositionX()+30<30*3||myKing.getObjectPositionY()+30>30*14)
                {
                    gamePosX += 30;
                    gamePosY -= 30;
                }
                chessTurn = 2;
            }
            //logical chess
            //case 1 in board
            if(chessTurn==0&&counter==0)
            {
                queen2.setting("sprite\\chess\\2.png",30*7,30*7 );
                queen2.create();
                queen2.render();
                chessTurn = 1;
                counter++;
            }
            else
            {
                chessTurn = 1;
            }
            batch.end();
        }
        else
        {
            player.updateHero(delta);
            player.renderHero(delta);
        }
        process();
        drawGate();
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
        if(board.overlaps(player.getObjPlayer())){
            challenge = true;
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
        shapeRenderer.rect(board.getX(),board.getY(),board.getWidth(),board.getHeight());
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
