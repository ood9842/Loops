package com.oop.loop.sprite;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Item implements ApplicationListener {

    final static int  OBJECT_WIDHT = 32;
    final static int  OBJECT_HIEGHT = 32;
    final static int  FRAME_SIZE = 600;


    TextureAtlas item;
    SpriteBatch batch;

    String path;
    Animation walkRight;


    Rectangle objPlayer ;




    public Item(SpriteBatch batch1,String name){
        this.batch = batch1;
        this.path = name;

    }
    public void setObjPlayerPosition(int x, int y){
        this.objPlayer.x = x;
        this.objPlayer.y = y;

    }
    public boolean overlaps(Rectangle obj) {
        return  obj.overlaps(this.objPlayer);
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        item = new TextureAtlas("sprite\\boy\\assets.txt");


        objPlayer = new Rectangle();
        objPlayer.x = FRAME_SIZE / 2 - 32 / 2;
        objPlayer.y = 32;
        objPlayer.width = OBJECT_WIDHT;
        objPlayer.height = OBJECT_HIEGHT;
    }



    @Override
    public void resize(int width, int height) {

    }



    @Override
    public void render () {
        //stateTime = deltaTime;


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void renderItem(float deltaTime) {


        batch.begin();

        batch.draw(item.findRegion(path), objPlayer.x, objPlayer.y, objPlayer.width, objPlayer.height);

        batch.end();

    }
    public void updateItem(float deltaTime) {


    }


    @Override
    public void dispose () {

    }
}
