package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorBird extends Actor implements Collidable {
    private Animation<TextureRegion> birdAnimation;
    public ActorDino dino;
    public Circle boundingCircle;
    private float elapsedTime = 0;
    private float speed = 250;
    private int[] distance = {2000, 5000, 3000, 4000};
    private int[] height = {100, 0, 200};


    public ActorBird(ActorDino dino){
        this.dino = dino;

        //Texture load korteci
        TextureAtlas birdAtlas = new TextureAtlas(Gdx.files.internal("screenGif/bird_gif.atlas"));

        //regions for bird animation
        birdAnimation = new Animation<>(1f/8f, birdAtlas.getRegions(), Animation.PlayMode.LOOP);

        //size set kore dicci
        TextureRegion tmpRegion = birdAtlas.getRegions().first();
        setSize(tmpRegion.getRegionWidth(), tmpRegion.getRegionHeight());
        setPosition( Gdx.graphics.getWidth()/4f, dino.getHeight() + 50);

        //bounding circle set kore dicci
        this.boundingCircle = new Circle(getX()+getWidth()/2f, getY()+getHeight()/2f, (Math.min(getHeight(), getWidth())/2f)*0.9f);
    }

    @Override
    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    @Override
    public void setPositionCollidable(float x, float y) {
        this.setPosition(x,y);
        boundingCircle.setPosition(x+getWidth()/2f, y+getHeight()/2f);
    }

    @Override
    public void act(float delta){
        super.act(delta);
        elapsedTime += delta;

        moveBy(speed*delta, 0);

        if(getX()> Gdx.graphics.getWidth()){
            setPosition(-(getWidth() + selectRandom(distance)), dino.getHeight() + selectRandom(height));
        }

        boundingCircle.setPosition(getX()+getWidth()/2f, getY()+getHeight()/2f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(birdAnimation.getKeyFrame(elapsedTime, true), getX(), getY(), getWidth(), getHeight());
    }

    private int selectRandom(int[] distances){
        return distances[(int)(Math.random()*distances.length)];
    }


}
