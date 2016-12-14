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

public class NPC  implements ApplicationListener {
    final static int  OBJECT_WIDHT = 30;
    final static int  OBJECT_HIEGHT = 30;

    private int x;
    private int y;

    Texture player;
    SpriteBatch batch;
    String local = "";


    public Rectangle objPlayer ;



    public NPC(SpriteBatch batch1) {
        this.batch = batch1;

    }


    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Texture(Gdx.files.internal(local));
        objPlayer = new Rectangle();
        objPlayer.setPosition(x,y);
        objPlayer.width = OBJECT_WIDHT;
        objPlayer.height = OBJECT_HIEGHT;

       // Gdx.app.log("", String.valueOf(objPlayer.getX()) + String.valueOf(objPlayer.getY()));

    }
    public Rectangle getObjPlayer() {
        return objPlayer;
    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void render() {
        batch.begin();

        batch.draw(player, objPlayer.x, objPlayer.y, objPlayer.width, objPlayer.height);

        batch.end();


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }


}
