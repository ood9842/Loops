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
import com.oop.loop.sprite.NPC;


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
    private int change=0;
    //player
    private Hero player;
    private NPC hm;

    private float Px=30*18,Py=30*10;
    private String last_walk = "NONE";
    private int counter = 0;

    boolean challenge= false;
    int chessTurn = 0;
    int gamePosX = 30 * 10;
    int gamePosY = 30 * 10;
    Rectangle board;
    Texture boy;


    private boolean show = false;
    private ArrayList<Texture> mesg;
    private int order = 0;

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

        hm = new NPC(this.batch);
        hm.setting_Position("new pumkin\\55.png",30*12,(30*11));
        hm.create();
        hm.setting_size(60,60);
        not_pass.add(hm.getObjPlayer());

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

        mesg = new ArrayList<Texture>();
        for(int i=0 ; i<3 ; i++) {
            mesg.add(new Texture(Gdx.files.internal("message\\map4\\0"+(i+1)+".jpg")));
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

        queen1.render();
        queen2.render();
        rook.render();
        bishop.render();
        knight.render();
        king.render();
        hm.render();
        //draw player
        if(show){
            batch.begin();
            batch.draw(mesg.get(order), 0, 0);

            batch.draw(player.getState(), player.getObjectPositionX(), player.getObjectPositionY(),30,30);
            if(Gdx.input.isKeyJustPressed(Input.Keys.X))
            {
                order++;
            }
            if(order>2){
                show=false;
            }
            batch.end();
        }else {
            if (challenge) {
                batch.begin();
                myKing.update(gamePosX, gamePosY);
                if (Gdx.input.isKeyJustPressed(Input.Keys.W)||Gdx.input.isKeyJustPressed(Input.Keys.UP) && chessTurn == 1) {
                    gamePosY += 30;
                    chessTurn = 2;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.X) ||Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && chessTurn == 1) {
                    gamePosY -= 30;
                    chessTurn = 2;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.A) ||Gdx.input.isKeyJustPressed(Input.Keys.LEFT)&& chessTurn == 1) {
                    gamePosX -= 30;
                    chessTurn = 2;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)||Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && chessTurn == 1) {
                    gamePosX += 30;

                    chessTurn = 2;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.C) && chessTurn == 1) {
                    gamePosX += 30;
                    gamePosY -= 30;

                    chessTurn = 2;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.E) && chessTurn == 1) {
                    gamePosX += 30;
                    gamePosY += 30;

                    chessTurn = 2;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && chessTurn == 1) {
                    gamePosX -= 30;
                    gamePosY -= 30;

                    chessTurn = 2;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && chessTurn == 1) {
                    gamePosX -= 30;
                    gamePosY += 30;

                    chessTurn = 2;
                }
                //logical chess
                //case 1 in board
                if (chessTurn == 0 && counter == 0) {
                    queen1.walkToTargetGridX(delta, 7);
                    queen1.walkToTargetGridY(delta, 11);
                    Gdx.app.log("", "" + queen1.getObjPlayer().x + "  " + queen1.getObjPlayer().y);
                    if (queen1.isMoved(queen1.getObjPlayer(), 7, 11)) {

                        chessTurn = 1;
                    }

                } else if (chessTurn == 2) {
                    queen1.walkToTargetGridX(delta, gamePosX / 30);
                    queen1.walkToTargetGridY(delta, gamePosY / 30);
                    queen2.walkToTargetGridX(delta, gamePosX / 30);
                    queen2.walkToTargetGridY(delta, gamePosY / 30);
                    rook.walkToTargetGridX(delta, gamePosX / 30);
                    rook.walkToTargetGridY(delta, gamePosY / 30);
                    bishop.walkToTargetGridX(delta, gamePosX / 30);
                    bishop.walkToTargetGridY(delta, gamePosY / 30);
                    knight.walkToTargetGridX(delta, gamePosX / 30);
                    knight.walkToTargetGridY(delta, gamePosY / 30);
                    king.walkToTargetGridX(delta, gamePosX / 30);
                    king.walkToTargetGridY(delta, gamePosY / 30);
                    //play sound1
                    if (queen1.isMoved(queen1.getObjPlayer(), gamePosX / 30, gamePosY / 30) && queen2.isMoved(queen2.getObjPlayer(), gamePosX / 30, gamePosY / 30) && bishop.isMoved(bishop.getObjPlayer(), gamePosX / 30, gamePosY / 30)) {

                        change = 2;
                    }

                }
                batch.end();
            } else {
                player.updateHero(delta);
                player.renderHero(delta);
            }
            process();
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
        if(board.overlaps(player.getObjPlayer())){
            challenge = true;
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
