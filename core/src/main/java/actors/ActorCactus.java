package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ActorCactus extends Actor {
    private Texture cactusTexture;
    private ActorGround ground;
    private float speed;
    public ActorCactus(ActorGround ground){
        this.ground = ground;
        this.speed = ground.speed;

        this.cactusTexture = new Texture(Gdx.files.internal("images/cactus_actor.png"));
        this.setSize(this.cactusTexture.getWidth()*5, this.cactusTexture.getHeight()*5);

        //ground er speed ar cactus er speed same hobe
        // but every frame ei speed update korte hobe because
        // ground er speed change hote pare
        this.setPosition(Gdx.graphics.getWidth(), ground.getY() + ground.getHeight() - 5);

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
            setX(Gdx.graphics.getWidth());
        }

        //System.out.println("Cactus X: " + getX() + ", Ground X: " + ground.getX() + ", Speed: " + speed + ", Delta: " + delta);
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
