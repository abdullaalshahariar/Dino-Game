package views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.main.Main;

public class MenuScreen implements Screen {
    private Main parent;
    private Stage stage;
    private Music menuMusic;

    private class RunningDino extends Actor {
        private TextureAtlas atlas;
        private TextureRegion[] frames;
        private float fps;
        private int currentFrame;
        private float timer;

        public RunningDino(){
            //loading the atlas
            atlas = new TextureAtlas(Gdx.files.internal("screenGif/MenuScreenGif.atlas"));

            //keeping all the regions in an array
            int frame_count = atlas.getRegions().size;
            frames = new TextureRegion[frame_count];
            for(int i=0; i<frame_count; i++){
                frames[i] = atlas.findRegion("frame" + i);
            }

            //setting size of actor
            setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            //setting position
            setPosition(0, 0);

            //other parameters for showing the animated sprite
            fps = (float) 10;
            currentFrame = 0;
            timer = 0;
        }

        @Override
        public void draw(Batch batch, float parentAlpha){
            //updating the animation frame
            if (timer < 1.0 / fps) {
                timer += Gdx.graphics.getDeltaTime();
            } else {
                timer -= (float)1.0 / fps;
                currentFrame = (currentFrame + 1) % frames.length;
            }

            //draw the dino
            batch.draw(frames[currentFrame], getX(), getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    private class Play_button extends Actor{
        Texture texture;

        public Play_button(Main parent){
            //loading the button
            texture = new Texture(Gdx.files.internal("images/play_button.png"));

            //setting size keeping aspect ration same
            float aspectRatio = (float)texture.getWidth() / (float)texture.getHeight();
            float width = (float) Gdx.graphics.getWidth()/4;
            float height = width / aspectRatio;
            setSize(width, height);

            //making changes so that it can take input
            setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight()); //now it knows how big it is
            setTouchable(Touchable.enabled); //now it can be touched
            addListener(new InputListener(){
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                                parent.changeScreen(Main.APPLICATION);
                                return true;
                            }
                        }
            );

            //setting position
            setPosition(Gdx.graphics.getWidth() / 2f - getWidth() / 2f, Gdx.graphics.getHeight()*(float)0.10 - (float) 10);
        }

        @Override
        public void draw(Batch batch, float parentAlpha){
            //draw the button
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }
    }

    public void stopMusic(){
        menuMusic.stop();
    }

    public MenuScreen(Main main){
        parent = main;
    }

    @Override
    public void show() {
        //takes two arguments, viewport and batch. if not provided,
        // it will create of its own and use the default viewport and batch
        stage = new Stage(new ScreenViewport());
        // it can also handle inputs
        Gdx.input.setInputProcessor(stage);

        //adding Running Dino actor to stage
        RunningDino dino = new RunningDino();
        stage.addActor(dino);
        //adding Play button actor to stage
        Play_button play_button = new Play_button(parent);
        stage.addActor(play_button);

        //giving the stage keyboard focus so that it can handle inputs
        stage.setKeyboardFocus(play_button);


        //music load koreci
        //music credit
//        Cipher by Kevin MacLeod http://incompetech.com
//        Creative Commons — Attribution 4.0 International — CC BY 4.0
//        Free Download / Stream: https://bit.ly/_cipher
//        Music promoted by Audio Library https://youtu.be/XRdRGzd-Mcw
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/Cipher2.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.7f);
        menuMusic.play();
    }


    @Override
    public void render(float delta) {
        //clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        //draw the stage
        stage.act();
        stage.draw();
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
        stage.dispose();
        menuMusic.dispose();
    }
}
