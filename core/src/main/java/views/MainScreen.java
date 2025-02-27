package views;

import actors.ActorCactus;
import actors.ActorGround;
import actors.ActorParallaxLayer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.main.Main;

public class MainScreen implements Screen {
    private Main parent;
    private Stage stage;
    private OrthographicCamera camera;
    private ActorParallaxLayer backgroundLayers;


    public MainScreen(Main main){
        parent = main;
    }

    public void LoadParallaxBackground(Game game){

    }

    @Override
    public void show() {
        //creating stage
        stage = new Stage(new ScreenViewport());
        camera = (OrthographicCamera) stage.getViewport().getCamera();

        //loading all textures for background
        Array<Texture> backgroundTextures = new Array<>();
        for(int i=1; i<=5; i++){
            backgroundTextures.add(new Texture(Gdx.files.internal("screenGif/parallaxBackground/plx-" + i + ".png")));
            backgroundTextures.get(backgroundTextures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        //creating layers for parallax background, setting size,speed adding to stage
        backgroundLayers = new ActorParallaxLayer(backgroundTextures);
        backgroundLayers.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundLayers.setSpeed(1);
        stage.addActor(backgroundLayers);

        //creating ground layer
        float speed=200;
        ActorGround ground = new ActorGround(speed);
        stage.addActor(ground);

        //4 ta cactus add korteci
        ActorCactus cactus1  = new ActorCactus(ground);
        ActorCactus cactus2 = new ActorCactus(ground);
        ActorCactus cactus3 = new ActorCactus(ground);
        ActorCactus cactus4 = new ActorCactus(ground);
        //cactus gula ke stage e add kore dicci
        stage.addActor(cactus1);
        stage.addActor(cactus2);
        stage.addActor(cactus3);
        stage.addActor(cactus4);
        //positon gula set kore dite hobe jate overlap na hoy
        cactus1.setPosition(100, ground.getHeight()); // Example starting position
        cactus2.setPosition(300, ground.getHeight()); // Offset by 200 pixels
        cactus3.setPosition(500, ground.getHeight());
        cactus4.setPosition(700, ground.getHeight());
    }

    @Override
    public void render(float delta) {
        //clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        //update the stage and draw
        stage.act(Gdx.graphics.getDeltaTime());
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
        backgroundLayers.dispose();
    }
}
