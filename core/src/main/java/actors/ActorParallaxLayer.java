package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ActorParallaxLayer extends Actor {
    private Array<Texture>  layers;
    private final float LAYER_SPEED_DIFFERENCE = (float) 0.4f;
    private float speed, scroll;
    private int layerCount;
    int x,y,width,heigth,scaleX,scaleY;
    int originX, originY,rotation,srcX,srcY;
    boolean flipX,flipY;



    public ActorParallaxLayer(Array<Texture> textures){
        this.scroll = 0;
        this.speed = 0;
        this.layerCount = textures.size;
        this.layers = textures;
        for(int i = 0; i <layerCount;i++){
            textures.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        this.x = this.y = this.originX = this.originY = this.rotation = this.srcY = 0;
        this.width = Gdx.graphics.getWidth();
        this.heigth = Gdx.graphics.getHeight();
        this.scaleX = this.scaleY = 1;
        this.flipX = this.flipY = false;
    }

    public void setSpeed(float newSpeed){
        this.speed = newSpeed;
    }

    @Override
    public  void draw(Batch batch, float parentAlpha){
        scroll += speed;
        for(int i=0; i<layerCount; i++){
            this.srcX = (int)(scroll + i*this.LAYER_SPEED_DIFFERENCE*scroll);
            batch.draw(layers.get(i), x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,srcX,srcY,layers.get(i).getWidth(),layers.get(i).getHeight(),flipX,flipY);
        }
    }

    // Manual
    public void dispose(){
        for(Texture texture: layers){
            texture.dispose();
        }
    }
}
