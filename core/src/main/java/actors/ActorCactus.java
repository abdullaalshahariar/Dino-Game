package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;


public class ActorCactus extends Actor implements Collidable{
    private Texture cactusTexture;
    private ActorGround ground;
    private float speed;
    private int size;
    private int[] distance = {200,300,400,500,600};
    private Random random;
    //public Rectangle boundingBox;
    public Circle boundingCircle;

    public ActorCactus(ActorGround ground, Texture cactusTexture){
        this(ground, cactusTexture, 5);
    }

    public ActorCactus(ActorGround ground, Texture cactusTexture, int size){
        this.size = size;
        this.ground = ground;
        this.speed = ground.speed;

        this.cactusTexture = cactusTexture;
        this.setSize(this.cactusTexture.getWidth()*size, this.cactusTexture.getHeight()*size);

        this.setPosition(Gdx.graphics.getWidth(), ground.getY() + ground.getHeight() - 50);
        //this.boundingBox = new Rectangle(getX(), getY(), getWidth(), getHeight());
        this.boundingCircle = new Circle(getX()+getWidth()/2f, getY()+getHeight()/2f, (Math.min(getHeight(), getWidth())/2f)*0.9f);
        this.random = new Random();
    }

    //from collidable interface
    public Circle getBoundingCircle(){
        return boundingCircle;
    }
    public void setPositionCollidable(float x, float y){
        this.setPosition(x, y);
        boundingCircle.setPosition(x+getWidth()/2f, y+getHeight()/2f);
    }


    @Override
    public void act(float delta){
        super.act(delta);

        //every frame e speed sync korteci ground er sathe
        speed = ground.speed;
        //speed negative because left e move kortece
        moveBy(-speed*delta, 0);
        //jodi cactus viewport er baire chole jai, tahole
        // again screen er right side e chole asbe
        if(getX() < -getWidth()){
            setX(Gdx.graphics.getWidth() + selectRandom());
        }

        //changing bounding box position
        //boundingBox.setPosition(getX(), getY());
        boundingCircle.setPosition(getX() + getWidth()/2f, getY()+getHeight()/2f);

        //System.out.println("Cactus X: " + getX() + ", Ground X: " + ground.getX() + ", Speed: " + speed + ", Delta: " + delta);
    }

    int selectRandom(){
        return distance[random.nextInt(distance.length)];
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(this.cactusTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    //manually disposing the cactusTexture
    public void dispose(){
        cactusTexture.dispose();
    }
}
