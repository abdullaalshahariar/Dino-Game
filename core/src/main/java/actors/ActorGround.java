package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorGround extends Actor {
    private Texture groundTexture;
    private float scrollX, speed;

    public ActorGround(float speed) {
        this.groundTexture = new Texture((Gdx.files.internal("images/ground.png")));
        this.groundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        this.scrollX = 0;
        this.speed = 2;
        this.setSize(Gdx.graphics.getWidth(), this.groundTexture.getHeight());
        this.setPosition(0, 0);
    }

    @Override
    public void act(float delta) {
        this.scrollX += this.speed;
        if (this.scrollX > this.groundTexture.getWidth()) {
            this.scrollX -= this.groundTexture.getWidth();
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        int textureWidth = this.groundTexture.getWidth();
        int screenWidth = (int) this.getWidth();

        batch.draw(this.groundTexture, this.getX() - this.scrollX, this.getY(), textureWidth, this.getHeight());
        batch.draw(this.groundTexture, this.getX() - this.scrollX + textureWidth, this.getY(), textureWidth, this.getHeight());

        System.out.println(this.getX());
    }

    // Manually dispose method
    public void disposeTexture() {
        if (this.groundTexture != null) {
            this.groundTexture.dispose();
        }
    }
}
