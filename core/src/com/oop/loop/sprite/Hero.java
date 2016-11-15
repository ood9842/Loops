package com.oop.loop.sprite;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static com.oop.loop.sprite.State.StandingD;

public class Hero implements ApplicationListener {
    final static int  OBJECT_WIDHT = 32;
    final static int  OBJECT_HIEGHT = 32;
    final static int  FRAME_SIZE = 600;
    final static int  VELOCITY = 150;

    State state = StandingD;
    State laststate = StandingD;


    TextureAtlas player;
    SpriteBatch batch;

    Animation walkLeft;
    Animation walkUp;
    Animation walkDown;
    Animation walkRight;


    Rectangle objPlayer ;

    float stateTime ;


    public Hero(SpriteBatch batch1){
            this.batch = batch1;
        }

    public void setObjPlayerPosition(int x, int y){
        this.objPlayer.x = x;
        this.objPlayer.y = y;
    }

    public int getObjectPositionX(){
        return (int)objPlayer.x;
    }

    public int getObjectPositionY(){
        return (int)objPlayer.y;
    }

    public Rectangle getObjPlayer() {
        return objPlayer;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        player = new TextureAtlas("sprite\\boy\\assets.txt");

        walkLeft = new Animation(0.15f, player.findRegion("b5"),player.findRegion("b6"),player.findRegion("b7"),player.findRegion("b8"));
        walkUp = new Animation(0.15f, player.findRegion("b13"),player.findRegion("b14"),player.findRegion("b15"),player.findRegion("b16"));
        walkDown = new Animation(0.15f, player.findRegion("b1"),player.findRegion("b2"),player.findRegion("b3"),player.findRegion("b4"));
        walkRight = new Animation(0.15f,player.findRegion("b9"),player.findRegion("b10"),player.findRegion("b11"),player.findRegion("b12"));

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

    public void renderHero(float deltaTime) {

        TextureRegion frame = null;
        switch (state) {
            case StandingD:
                frame = walkDown.getKeyFrame(1);
                break;
            case StandingU:
                frame = walkUp.getKeyFrame(1);
                break;
            case StandingL:
                frame = walkLeft.getKeyFrame(1);
                break;
            case StandingR:
                frame = walkRight.getKeyFrame(1);
                break;
            case WalkingL:
                frame = walkLeft.getKeyFrame(stateTime);
                break;
            case WalkingU:
                frame = walkUp.getKeyFrame(stateTime);
                break;
            case WalkingD:
                frame = walkDown.getKeyFrame(stateTime);
                break;
            case WalkingR:
                frame = walkRight.getKeyFrame(stateTime);
                break;
        }


        batch.begin();

        batch.draw(frame, objPlayer.x, objPlayer.y, objPlayer.width, objPlayer.height);

        batch.end();

    }
    public void updateHero(float deltaTime) {
        if (deltaTime == 0) return;
        if (deltaTime > 0.1f)
            deltaTime = 0.1f;

        stateTime += deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            state = State.WalkingU;
            laststate = State.WalkingU;
            objPlayer.y += VELOCITY * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            laststate = State.WalkingD;
            state = State.WalkingD;
            objPlayer.y -= VELOCITY * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            laststate = State.WalkingL;
            state = State.WalkingL;
            objPlayer.x -= VELOCITY * deltaTime;

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            laststate = State.WalkingR;
            state = State.WalkingR;
            objPlayer.x += VELOCITY * deltaTime;

        } else {if (laststate == State.WalkingR) {

            state = State.StandingR;
        }
            if (laststate == State.WalkingD) {

                state = State.StandingD;
            }
            if (laststate == State.WalkingL) {

                state = State.StandingL;
            }
            if (laststate == State.WalkingU) {

                state = State.StandingU;
            }


            //state = State.StandingD;
        }


        if (objPlayer.x < 0)
            objPlayer.x = 0;
        if (objPlayer.x > FRAME_SIZE - 40)
            objPlayer.x = FRAME_SIZE - 40;
        if (objPlayer.y < 0)
            objPlayer.y = 0;
        if (objPlayer.y > FRAME_SIZE - 40)
            objPlayer.y = FRAME_SIZE - 40;
    }

    public void stopHero(int x,int y)
    {

    }
    @Override
    public void dispose () {

    }
}
