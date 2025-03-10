package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorBird extends Actor implements Collidable {
    private Animation<TextureRegion> birdAnimation;
    public ActorGround ground;


    public ActorBird(ActorGround ground){
        this.ground = ground;

        //Texture load korteci
        TextureAtlas birdAtlas = new TextureAtlas(Gdx.files.internal("screenGif/bird_gif.atlas"));
    }

    @Override
    public Circle getBoundingCircle() {
        return null;
    }

    @Override
    public void setPositionCollidable(float x, float y) {
        return;
    }
}
