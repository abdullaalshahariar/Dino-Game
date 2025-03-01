package views;

import actors.ActorCactus;
import actors.ActorDino;
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

import java.util.Random;

public class MainScreen implements Screen {
    private Main parent;
    private Stage stage;
    private OrthographicCamera camera;
    private ActorParallaxLayer backgroundLayers;
    private Random random;


    public MainScreen(Main main){
        parent = main;
        random = new Random();
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

        //3 ta cactus add korteci
        ActorCactus cactus1  = new ActorCactus(ground, new Texture(Gdx.files.internal("images/cactus_actor1.png")), 7);
        ActorCactus cactus2 = new ActorCactus(ground, new Texture(Gdx.files.internal("images/cactus_actor2.png")), 7);
        ActorCactus cactus3 = new ActorCactus(ground, new Texture(Gdx.files.internal("images/cactus_actor3.png")));
        //cactus gula ke stage e add kore dicci
        stage.addActor(cactus1);
        stage.addActor(cactus2);
        stage.addActor(cactus3);

        //before settig the distance
        //kicu fixed offset distance define kore dicci
        int[] distance = {200, 300, 400, 500, 600};

        //positon gula set kore dite hobe jate overlap na hoy
        cactus1.setPosition(selectRandom(distance), ground.getHeight()-10); // Example starting position
        cactus2.setPosition(selectRandom(distance), ground.getHeight()-10); // Offset by 200 pixels
        cactus3.setPosition(selectRandom(distance), ground.getHeight()-10);

        //dinosure add korteci
        ActorDino dino = new ActorDino(ground);
        //dino er position set kore dicci
        dino.setPosition( Gdx.graphics.getWidth()/4f, ground.getHeight()-10);
        //stage e add kore dicci
        stage.addActor(dino);



    }

    private int selectRandom(int[] distances){
        return distances[random.nextInt(distances.length)];
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
