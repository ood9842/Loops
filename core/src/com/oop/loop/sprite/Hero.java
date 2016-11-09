package com.oop.loop.sprite;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;

import static com.oop.loop.sprite.State.StandingD;

public class Hero implements ApplicationListener {
    State state = StandingD;
    State laststate = StandingD;
    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    TextureAtlas player;
    TextureAtlas textureAtlas;

    SpriteBatch batch;

    Animation walkLeft;
    Animation standAnimation;
    Animation walkUp;
    Animation walkDown;
    Animation walkRight;

    static float MAX_VELOCITY = 10f;
    static float velocity = 0;
    Rectangle objPlayer ;

    float stateTime ;

    boolean facesRight=false;
        public Hero(SpriteBatch batch1){
            this.batch = batch1;

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
        objPlayer.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        objPlayer.y = 20;
        objPlayer.width = 64;
        objPlayer.height = 64;
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

    public void renderKoala(float deltaTime) {
        // based on the koala state, get the animation frame
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

        // draw the koala, depending on the current velocity
        // on the x-axis, draw the koala facing either right
        // or left


        batch.begin();

        batch.draw(frame, objPlayer.x, objPlayer.y, objPlayer.width, objPlayer.height);

        batch.end();

    }
    public void updateKoala(float deltaTime) {
        if (deltaTime == 0) return;

        if (deltaTime > 0.1f)
            deltaTime = 0.1f;


        stateTime += deltaTime;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.A)) {

            state = State.WalkingU;
            laststate = State.WalkingU;
            objPlayer.y += 200 * deltaTime;


        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            laststate = State.WalkingD;
            state = State.WalkingD;
            objPlayer.y -= 200 * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            laststate = State.WalkingL;
            state = State.WalkingL;
            objPlayer.x -= 200 * deltaTime;

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            laststate = State.WalkingR;
            state = State.WalkingR;
            objPlayer.x += 200 * deltaTime;

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

        //if (Gdx.input.isKeyJustPressed(Input.Keys.B))
        //debug = !debug;
        if (objPlayer.x < 0)
            objPlayer.x = 0;
        if (objPlayer.x > 600 - 64)
            objPlayer.x = 600 - 64;
        if (objPlayer.y < 0)
            objPlayer.y = 0;
        if (objPlayer.y > 600 - 64)
            objPlayer.y = 600 - 64;


    }
    private boolean isTouched (float startX, float endX) {
        // Check for touch inputs between startX and endX
        // startX/endX are given between 0 (left edge of the screen) and 1 (right edge of the screen)
        for (int i = 0; i < 2; i++) {
            float x = Gdx.input.getX(i) / (float)Gdx.graphics.getWidth();
            if (Gdx.input.isTouched(i) && (x >= startX && x <= endX)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose () {

    }
}
