package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;


public class ActorCactus extends Actor {
    private Texture cactusTexture;
    private ActorGround ground;
    private float speed;
    private int size;
    private int[] distance = {200,300,400,500,600};
    private Random random;

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

        this.random = new Random();
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
