package com.oop.loop.sprite;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static com.oop.loop.sprite.State.StandingD;

/**
 * Created by OVER on 12/14/2016.
 */
public class Chess implements ApplicationListener {
    final static int  OBJECT_WIDHT = 32;
    final static int  OBJECT_HIEGHT = 32;
    final static int  FRAME_SIZE = 600;
    public int  VELOCITY_UP = 150;
    public int  VELOCITY_DOWN = 150;
    public int  VELOCITY_LEFT = 150;
    public int  VELOCITY_RIGHT = 150;

    public boolean CANRIGHT = true;
    public boolean CANLEFT = true;
    public boolean CANUP = true;
    public boolean CANDOWN = true;

    public boolean toggle = false;


    Texture player;
    SpriteBatch batch;
    String local = "";

    Item item;
    public Rectangle objPlayer ;

    float stateTime ;

    int x = 0;
    int y = 0;


    public Chess(SpriteBatch batch1){
        this.batch = batch1;

    }

    public Chess() {
    }

    public void setObjPlayerPosition(float x, float y){
        this.objPlayer.x = x;
        this.objPlayer.y = y;

    }
    public float getObjectPositionX(){
        return objPlayer.x;


    }
    public float getObjectPositionY(){
        return objPlayer.y;


    }
    public void setting(String local, int x , int y )
    {  this.local = local;
        this.x = x;
        this.y = y;

    }

    public void changeLocate( int x , int y )
    {
        this.x = (int) (objPlayer.x = x);
        this.y = (int) (objPlayer.y = y);
        render();

    }

    public Rectangle getObjPlayer() {
        return objPlayer;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        player = new Texture(Gdx.files.internal(local));

        objPlayer = new Rectangle();
        objPlayer.setPosition(x,y);
        objPlayer.width = OBJECT_WIDHT;
        objPlayer.height = OBJECT_HIEGHT;
        // Gdx.app.log("", String.valueOf(objPlayer.getX())+String.valueOf(objPlayer.getY()));

    }

    @Override
    public void resize(int width, int height) {

    }



    @Override
    public void render () {
        batch.begin();

        batch.draw(player, objPlayer.x, objPlayer.y, objPlayer.width, objPlayer.height);

        batch.end();
    }
    public void update(int x, int y) {
        batch.begin();

        batch.draw(player, x, y, objPlayer.width, objPlayer.height);

        batch.end();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }



    public void walkToTargetGridX(float deltaTime, int x){

        if ((int)this.getObjectPositionX() < x*30) {

            this.setObjPlayerPosition(this.getObjectPositionX() + (150*deltaTime), this.getObjectPositionY());

        }
        if ((int)this.getObjectPositionX() > x*30) {

            this.setObjPlayerPosition(this.getObjectPositionX() - (100*deltaTime), this.getObjectPositionY());

        }

    }

    public void walkToTargetGridY(float deltaTime, int y){

        if ((int)this.getObjectPositionY() < y*30) {

            this.setObjPlayerPosition(this.getObjectPositionX() , this.getObjectPositionY() + (100*deltaTime));

        }
        if ((int)this.getObjectPositionY() > y*30) {

            this.setObjPlayerPosition(this.getObjectPositionX() , this.getObjectPositionY() - (100*deltaTime));

        }

    }
    public boolean isMoved(Rectangle o,int targetX,int targetY){

        if ((int)o.y == targetY*30 && (int)o.x == targetX*30) {
           return true;
        }
       else {
            return false;
        }
    }



    @Override
    public void dispose () {

    }
}
