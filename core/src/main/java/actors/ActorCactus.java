package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ActorCactus extends Actor {
    private Texture cactusTexture;
    private ActorGround ground;

    public ActorCactus(ActorGround ground){
        this.ground = ground;

        this.cactusTexture = new Texture(Gdx.files.internal("images/cactus_actor.png"));
        this.setSize(this.cactusTexture.getWidth()*5, this.cactusTexture.getHeight()*5);

        //setting position relative to ground actor
        // but needed to be updated every frame
        this.setPosition(ground.getX(), ground.getY()+ground.getHeight()-5);

    }

    @Override
    protected void positionChanged(){
        updatePosition();
        super.positionChanged();
    }

    @Override
    public void act(float delta){
        super.act(delta);
        updatePosition();
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        //System.out.println(ground.getX());
        batch.draw(this.cactusTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    private void updatePosition(){
        this.setPosition(ground.getX(), ground.getY()+ground.getHeight()-5);
    }

    //manually disposing the cactusTexture
    public void dispose(){
        cactusTexture.dispose();
    }
}
