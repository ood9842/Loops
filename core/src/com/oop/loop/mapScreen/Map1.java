package com.oop.loop.mapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oop.loop.sprite.Hero;
import com.oop.loop.sprite.murderer;

import javax.swing.plaf.synth.Region;
import java.util.ArrayList;


/**
 * Created by Chetsada Chaiprasop on 12/13/2016.
 */
public class Map1 implements Screen {
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
    private murderer Mur;
    private float Px=150,Py=150;
    //script
    private boolean show = false;
    private ArrayList<Texture> mesg;
    private int order = 0;
    private int state = 0;
    //test
    /*private boolean show = false;
    private Vector2 start;
    private Vector2 end;

    private static float SPREED = 100;
    private static float DELAY = 0.0f;
    private float directionX;
    private float directionY;
    private float distance;
    private boolean moving;
    */

    Texture boy;
    Texture girl;
    boolean script = false;

    public Map1(SpriteBatch batch)
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
        //script

        mesg = new ArrayList<Texture>();
        for(int i=0;i<5;i++)
        {
            mesg.add(new Texture("message\\start\\0"+(i+1)+".jpg"));
        }
        boy = new Texture("sprite\\boy\\b15.png");
        girl = new Texture("sprite\\girl\\g1.png");

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

        if(show) {
            batch.begin();
            player.updateHero(delta);
            player.renderHero(delta);
            Mur.updateHero(delta);
            Mur.renderHero(delta);
            if (!script){
                if(state==0){
                Mur.walkToTargetAxisY(delta,30*9);
                if ((int)Mur.getObjectPositionY() == 30*9) {
                    state++;
                }
                }
                if(state==1){
                    Mur.walkToTargetAxisX(delta,-60);
                    if ((int)Mur.getObjectPositionY() == -60) {
                        script = true;
                    }
                }
            }

            batch.end();
            process();
            drawGate();
        }
        if(!show)//script
        {
            batch.begin();
            batch.draw(mesg.get(order), 0, 0);
            if(order > 0) {
                batch.draw(boy, 30 * 5, 30 * 5);
                batch.draw(girl, 30 * 5, 30 * 6);
            }
            batch.end();
            if(Gdx.input.isKeyJustPressed(Input.Keys.X))
            {
                order++;
            }
            if(order>4)
            {
                //create player before script
                player = new Hero(this.batch);
                Mur = new murderer(this.batch);
                player.create();
                Mur.create();
                player.setObjPlayerPosition((int)Px,(int)Py);
                Mur.setObjPlayerPosition((int)Px,(int)Py+30);
                show = true;

            }


        }


        ///////////////////

        /////////////////////////////////////



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
            player.setObjPlayerPosition((int)Px,(int)Py);
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

    /*private void setVector(int sx,int sy,int ex,int ey)
    {
        start = new Vector2(sx,sy);
        end = new Vector2(ex,ey);
        distance = (float) Math.sqrt(Math.pow(end.x-start.x,2)+Math.pow(end.y-start.y,2));
        directionX = end.x-start.x;
        directionY = end.y-end.y;
        moving = true;
    }
    /*if(moving)
            {
                float update_Px = player.getObjectPositionX()+ directionX * SPREED * DELAY;//new meteor render in hero class
                float update_Py = player.getObjectPositionY()+ directionY * SPREED * DELAY;
                player.setObjPlayerPosition((int)update_Px,(int)update_Py);
                if(Math.sqrt(Math.pow(player.getObjectPositionX()-start.x,2)+Math.pow(player.getObjectPositionY()-start.y,2)) >= distance)
                {
                    player.setObjPlayerPosition((int)end.x,(int)end.y);
                    moving = false;
                    show = true;
                }
           }*/
}
