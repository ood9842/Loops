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

    Animation walkLeft;

    Animation walkUp;
    Animation walkDown;
    Animation walkRight;


    Rectangle objPlayer ;




    public Item(SpriteBatch batch1){
        this.batch = batch1;

    }
    public void setObjPlayerPosition(int x, int y){
        this.objPlayer.x = x;
        this.objPlayer.y = y;

    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        item = new TextureAtlas("sprite\\boy\\assets.txt");

        walkLeft = new Animation(0.15f, item.findRegion("b5"),item.findRegion("b6"),item.findRegion("b7"),item.findRegion("b8"));
        walkUp = new Animation(0.15f, item.findRegion("b13"),item.findRegion("b14"),item.findRegion("b15"),item.findRegion("b16"));
        walkDown = new Animation(0.15f, item.findRegion("b1"),item.findRegion("b2"),item.findRegion("b3"),item.findRegion("b4"));
        walkRight = new Animation(0.15f,item.findRegion("b9"),item.findRegion("b10"),item.findRegion("b11"),item.findRegion("b12"));

        walkLeft.setPlayMode(Animation.PlayMode.LOOP);
        walkUp.setPlayMode(Animation.PlayMode.LOOP);
        walkDown.setPlayMode(Animation.PlayMode.LOOP);
        walkRight.setPlayMode(Animation.PlayMode.LOOP);

        objPlayer = new Rectangle();
        objPlayer.x = FRAME_SIZE / 2 - 32 / 2;
        objPlayer.y = 32;
        objPlayer.width = OBJECT_WIDHT;
        objPlayer.height = OBJECT_HIEGHT;
    }
    public boolean overlaps(Rectangle obj) {
      return  obj.overlaps(this.objPlayer);
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

        batch.draw(item.findRegion("b9"), objPlayer.x, objPlayer.y, objPlayer.width, objPlayer.height);

        batch.end();

    }
    public void updateItem(float deltaTime) {


    }


    @Override
    public void dispose () {

    }
}
