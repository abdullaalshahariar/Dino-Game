package views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.main.Main;

public class LoadingScreen implements Screen {
    private Main parent;
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private TextureRegion[] frames;
    private Sprite sprite;
    private float fps;
    private int currentFrame;
    private float timer;
    private float elaspsedTime;
    private boolean isLoadingComplete;

    public LoadingScreen(Main main) {
        parent = main;
        elaspsedTime = 0;
        isLoadingComplete = false;
    }

    @Override
    public void show() {
        //creating a page to draw in
        batch = new SpriteBatch();

        //creating atlas texture
        atlas = new TextureAtlas(Gdx.files.internal("screenGif/LoadingGif.atlas"));

        //creating an array to store the frames
        int frameCount = atlas.getRegions().size;
        frames = new TextureRegion[frameCount];
        for(int i=0; i<frameCount; i++){
            frames[i] = atlas.findRegion("frame" + i);
        }

        //creating the sprite
        sprite = new Sprite(frames[0]);
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //other parameters for showing the animated sprite
        fps = (float) 24;
        currentFrame = 0;
        timer = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        //updating the animation frame
        if (timer < 1.0 / fps) {
            timer += delta;
        } else {
            timer -= (float)1.0 / fps;
            currentFrame = (currentFrame + 1) % frames.length;
        }

        //drawing the sprite
        sprite.setRegion(frames[currentFrame]);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        if(!isLoadingComplete){
            elaspsedTime += delta;
            if(elaspsedTime > 4){
                isLoadingComplete = true;
                parent.changeScreen(parent.MENU);
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {

    }
}
