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

/**
 * Created by OVER on 12/14/2016.
 */
public class murderer implements ApplicationListener {
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

    State state = StandingD;
    State laststate = StandingD;


    TextureAtlas player;
    SpriteBatch batch;

    Animation walkLeft;

    Animation walkUp;
    Animation walkDown;
    Animation walkRight;

    Item item;
    public Rectangle objPlayer ;

    float stateTime ;

    int x = 0;
    int y = 0;


    public murderer(SpriteBatch batch1){
        this.batch = batch1;

    }

    public murderer() {
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

    public Rectangle getObjPlayer() {
        return objPlayer;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        player = new TextureAtlas("sprite\\girl\\assets.txt");

        item = new Item(batch,"g6");

        item.create();
        item.setObjPlayerPosition(30*4,30*4);

        x = item.getObjectPositionX();
        y = item.getObjectPositionY();

        walkLeft = new Animation(0.15f, player.findRegion("g5"),player.findRegion("g6"),player.findRegion("g7"),player.findRegion("g8"));
        walkUp = new Animation(0.15f, player.findRegion("g13"),player.findRegion("g14"),player.findRegion("g15"),player.findRegion("g16"));
        walkDown = new Animation(0.15f, player.findRegion("g1"),player.findRegion("g2"),player.findRegion("g3"),player.findRegion("g4"));
        walkRight = new Animation(0.15f,player.findRegion("g9"),player.findRegion("g10"),player.findRegion("g11"),player.findRegion("g12"));

        walkLeft.setPlayMode(Animation.PlayMode.LOOP);
        walkUp.setPlayMode(Animation.PlayMode.LOOP);
        walkDown.setPlayMode(Animation.PlayMode.LOOP);
        walkRight.setPlayMode(Animation.PlayMode.LOOP);

        objPlayer = new Rectangle();
        objPlayer.x = 0;
        objPlayer.y = 0;
        objPlayer.width = OBJECT_WIDHT;
        objPlayer.height = OBJECT_HIEGHT;

        // Gdx.app.log("", String.valueOf(objPlayer.getX())+String.valueOf(objPlayer.getY()));

    }

    @Override
    public void resize(int width, int height) {

    }



    @Override
    public void render () {
        float deltaTime = Gdx.graphics.getDeltaTime();
        //stateTime = deltaTime;
        item.renderItem(deltaTime);



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
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) && CANUP) {

            state = State.WalkingU;
            laststate = State.WalkingU;
            objPlayer.y += VELOCITY_UP * deltaTime;



        } else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))&& CANDOWN) {
            laststate = State.WalkingD;
            state = State.WalkingD;
            objPlayer.y -= VELOCITY_DOWN * deltaTime;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && CANLEFT) {

            laststate = State.WalkingL;
            state = State.WalkingL;
            objPlayer.x -= VELOCITY_LEFT * deltaTime;

        } else if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && CANRIGHT) {

            laststate = State.WalkingR;
            state = State.WalkingR;
            objPlayer.x += VELOCITY_RIGHT * deltaTime;


        } else {if (laststate == State.WalkingR) {

            state = State.StandingR;
        }
            if ((Gdx.input.isKeyPressed(Input.Keys.SPACE))){

                objPlayer.set(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,30,30);



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





    }
    public void walkWithKey(){
        if (objPlayer.x < 0)
            objPlayer.x = 0;
        if (objPlayer.x > FRAME_SIZE - 30)
            objPlayer.x = FRAME_SIZE - 30;
        if (objPlayer.y < 0)
            objPlayer.y = 0;
        if (objPlayer.y > FRAME_SIZE - 30)
            objPlayer.y = FRAME_SIZE - 30;

    }
    public void walkToTargetAxisX(float deltaTime, float x){

        if (this.getObjectPositionX() < x) {
            if(toggle){
                state = State.WalkingR;
                toggle = false;
            }else{
                state = State.StandingR;
                toggle = true;
            }
            this.setObjPlayerPosition(this.getObjectPositionX() + (150*deltaTime), this.getObjectPositionY());

        }
        if (this.getObjectPositionX() > x) {
            if(toggle){
                state = State.WalkingL;
                toggle = false;
            }else{
                state = State.StandingL;
                toggle = true;
            }
            this.setObjPlayerPosition(this.getObjectPositionX() - (100*deltaTime), this.getObjectPositionY());

        }

    }

    public void walkToTargetAxisY(float deltaTime, float y){

        if (this.getObjectPositionY() < y) {
            if(toggle){
                state = State.WalkingU;
                toggle = false;
            }else{
                state = State.StandingU;
                toggle = true;
            }
            this.setObjPlayerPosition(this.getObjectPositionX() , this.getObjectPositionY() + (150*deltaTime));

        }
        if (this.getObjectPositionY() > y) {
            if(toggle){
                state = State.WalkingD;
                toggle = false;
            }else{
                state = State.StandingD;
                toggle = true;
            }
            this.setObjPlayerPosition(this.getObjectPositionX() , this.getObjectPositionY() - (100*deltaTime));

        }

    }

    public void walkAndCheck(Rectangle o,float x,float y,float w , float h){


        if( objPlayer.x > x - 30  &&  objPlayer.x < x +10 &&  objPlayer.y > y - 30  &&  objPlayer.y < y + h ){
            // CANRIGHT = false;

            VELOCITY_RIGHT = 0;

        }
        if ( objPlayer.x > x-20  &&  objPlayer.x < x + w &&  objPlayer.y > (y - 30) &&  objPlayer.y < y ){

            // CANUP = false;
            VELOCITY_UP=0;


        }
        if ( objPlayer.x > x + w -20     &&  objPlayer.x < x + w +20  &&  objPlayer.y > y -30 &&  objPlayer.y < y + h ){
            //CANLEFT = false;
            VELOCITY_LEFT = 0;
        }
        if ( objPlayer.x > x - 20  &&  objPlayer.x < x + w - 10 &&  objPlayer.y > y-20  &&  objPlayer.y < y + h ){
            //CANDOWN = false;
            VELOCITY_DOWN = 0;
        }
        else{
            //VELOCITY_UP =  VELOCITY_RIGHT =   VELOCITY_LEFT =    VELOCITY_DOWN  = 150;
        }


    }


    @Override
    public void dispose () {

    }
}
