package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorDino extends Actor implements Collidable{
    private Animation<TextureRegion> runningAnimation, duckingAnimation, idleAnimation, hitAnimation;
    float elapsedTime = 0;
    public Boolean RUNNING = false, DUCKING=false, IDLE=false, HIT=false, DASHING=false;
    private final float jumpStrength = 400, upGravity = -400, downGravity = -1600;
    private float jumpVelocity = 0;
    public ActorGround ground;
    public Circle boundingCircle;

    private Boolean canDash = false;
    private final float  dashSpeed = 500;
    private float dashTimeRenmaining = 0;
    private final float dashDuration = 0.2f;

    public ActorDino(ActorGround ground){
        this.ground = ground;

        // loading all the textures for dino animation
        // jump korle idle animation cholbe
        TextureAtlas runningAtlas = new TextureAtlas(Gdx.files.internal("screenGif/dino_running_mort.atlas"));
        TextureAtlas duckingAtlas = new TextureAtlas(Gdx.files.internal("screenGif/dino_duck_mort.atlas"));
        TextureAtlas idleAtlas = new TextureAtlas(Gdx.files.internal("screenGif/dino_idle_mort.atlas"));
        TextureAtlas hitAtlas = new TextureAtlas(Gdx.files.internal("screenGif/dino_hit_mort.atlas"));

        // creating regions for dino animation
        runningAnimation = new Animation<>(1f/10f, runningAtlas.getRegions(), Animation.PlayMode.LOOP);
        duckingAnimation = new Animation<>(1f/7f, duckingAtlas.getRegions(), Animation.PlayMode.LOOP);
        idleAnimation = new Animation<>(1f/3f, idleAtlas.getRegions(), Animation.PlayMode.LOOP);
        hitAnimation = new Animation<>(1f/3f, hitAtlas.getRegions(), Animation.PlayMode.NORMAL);

        //setting initial state of dino
        RUNNING = true;

        //size set kore dewa lagbe
        // na dile default size zero hobe, screen e kicu dekha jabe na
        TextureRegion tmpRegion = runningAtlas.getRegions().first();
        setSize(tmpRegion.getRegionWidth()*8, tmpRegion.getRegionHeight()*8);
        setPosition( Gdx.graphics.getWidth()/4f, ground.getHeight()-10);

        //setting up a bounding crcle to make collision detection look more natural
        this.boundingCircle = new Circle(getX()+getWidth()/2f, getY()+getHeight()/2f, (Math.min(getHeight(), getWidth())/2f)*0.9f);

    }

    //from collidable interface
    @Override
    public Circle getBoundingCircle(){
        return boundingCircle;
    }
    @Override
    public void setPositionCollidable(float x, float y){
        this.setPosition(x, y);
        boundingCircle.setPosition(x+getWidth()/2f, y+getHeight()/2f);
    }


    @Override
    public void act(float delta){
        super.act(delta);
        elapsedTime += delta;

        handleJumping(delta);
        handleDashing(delta);
        boundingCircle.setPosition(getX() + getWidth()/2f, getY()+getHeight()/2f);

    }

    private void handleJumping(float delta){
        //cheaking if key is pressed
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && getY()<=ground.getHeight()-10){
            jumpVelocity = jumpStrength;
            IDLE = true;
            RUNNING = false;
            canDash = true;
        }

        //gravity apply korteci
        if(jumpVelocity>0){
            jumpVelocity += upGravity*delta; //upoer e jacce, weak gravity
        }else if(jumpVelocity<=0){
            jumpVelocity += downGravity*delta; //niche jacce, strong gravity
        }
        //jumpVelocity += upGravity*delta;
        moveBy(0, jumpVelocity*delta);

        //System.out.println(jumpVelocity);


        //jodi ground er upore chole jai, tahole ground er upore thakbe
        if(getY()<ground.getHeight()-10){
            setY(ground.getHeight()-10);
            jumpVelocity = 0;
            IDLE = false;
            RUNNING = true;

            canDash = true; //jodi ground touch kore, jump korer por dash dewa jabe
            DASHING = false;
        }

    }

    private void handleDashing(float delta){
        //jodi right shift key press kora hoy and dino air e thake
        // tahole dash dewa jabe
        if(Gdx.input.isKeyJustPressed(Input.Keys.D) && getY()> ground.getHeight()-10 && canDash){
            canDash = false;
            DASHING = true;
            dashTimeRenmaining = dashDuration; //dash start hoye geche
            System.out.println("Shift Pressed");
        }

        //dash apply kora hocce
        if(DASHING && dashTimeRenmaining > 0){
            moveBy(dashSpeed*delta, 0);
            dashTimeRenmaining -= delta;

            if(dashTimeRenmaining<=0){
                DASHING = false;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        if(RUNNING){
            batch.draw(runningAnimation.getKeyFrame(elapsedTime, true), getX(), getY(), getWidth(), getHeight());
        }
        else if(DUCKING){
            batch.draw(duckingAnimation.getKeyFrame(elapsedTime, true), getX(), getY(), getWidth(), getHeight());
        }
        else if(IDLE){
            batch.draw(idleAnimation.getKeyFrame(elapsedTime, true), getX(), getY(), getWidth(), getHeight());
        }
        else if(HIT){
            batch.draw(hitAnimation.getKeyFrame(elapsedTime, false), getX(), getY(), getWidth(), getHeight());
        }
    }
}
