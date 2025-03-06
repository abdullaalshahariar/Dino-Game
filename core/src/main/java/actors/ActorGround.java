package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorGround extends Actor {
    private Texture groundTexture;
    private float scrollX;
    public float speed;

    public ActorGround(float groundSpeed) {
        groundTexture = new Texture(Gdx.files.internal("images/ground.png"));
        groundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        scrollX = 0;
        speed = groundSpeed; // Ground speed dynamically pass kora
        setSize(Gdx.graphics.getWidth(), groundTexture.getHeight());
        setPosition(0, 0);
    }

    @Override
    public void act(float delta) {
        scrollX += speed * delta;
        if (scrollX > groundTexture.getWidth()) {
            scrollX -= groundTexture.getWidth();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        int textureWidth = groundTexture.getWidth();
        int screenWidth = (int) getWidth();
        batch.draw(groundTexture, getX() - scrollX, getY(), textureWidth, getHeight());
        batch.draw(groundTexture, getX() - scrollX + textureWidth, getY(), textureWidth, getHeight());
    }
    public void disposeTexture() {
        if (groundTexture != null) {
            groundTexture.dispose();
        }
    }


}
