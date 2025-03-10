package views;

import actors.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    private Array<Collidable> obstacles;
    private Boolean gameOver = false;

    private ActorDino dino;
    private ActorGround ground;

    SpriteBatch batch;
    BitmapFont font;

    private Music backgroundMusic;
    private Sound deathSound;

    private int score;
    private BitmapFont scoreFont;
    private int highestScore;

    //only debugging er jonno bounding shape ta ke draw kore dekteci
//    private ShapeRenderer shapeRenderer;


    public MainScreen(Main main){
        parent = main;
        random = new Random();

        //onlu debugging er jonno bounding shape ta ke draw kore dekteci
//        shapeRenderer = new ShapeRenderer();
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
        ground = new ActorGround(speed);
        stage.addActor(ground);


        //dinosure add korteci
        dino = new ActorDino(ground);
        //dino er position set kore dicci
        dino.setPosition( Gdx.graphics.getWidth()/4f, ground.getHeight()-10);
        //stage e add kore dicci
        stage.addActor(dino);


        //collision detection er jonno sob actor ke
        // ekta array te rakhbo
        obstacles = new Array<>();

        //3 ta cactus add korteci
        ActorCactus cactus1  = new ActorCactus(ground, new Texture(Gdx.files.internal("images/cactus_actor1.png")), 7);
        ActorCactus cactus2 = new ActorCactus(ground, new Texture(Gdx.files.internal("images/barrel.png")), 4);
        ActorCactus cactus3 = new ActorCactus(ground, new Texture(Gdx.files.internal("images/cactus_actor3.png")));
        //bird add korteci
        ActorBird bird = new ActorBird(dino);

        //cactus gula ke stage e add kore dicci
        stage.addActor(cactus1);
        stage.addActor(cactus2);
        stage.addActor(cactus3);
        //bird ke stage e add kore dicci
        stage.addActor(bird);

        //cactus gula ke array te add kore dicci
        obstacles.add(cactus1);
        obstacles.add(cactus2);
        obstacles.add(cactus3);
        //bird ke array te add kore dicci
        obstacles.add(bird);

        //before settig the distance
        //kicu fixed offset distance define kore dicci
        int[] distance = {600, 800, 1200, 1500, 1900, 2500};

        //positon gula set kore dite hobe jate overlap na hoy
        cactus1.setPosition(selectRandom(distance)+1000, ground.getHeight()-10); // Example starting position
        cactus2.setPosition(selectRandom(distance)+1000, ground.getHeight()-10); // Offset by 200 pixels
        cactus3.setPosition(selectRandom(distance)+1000, ground.getHeight()-10);

        bird.setPosition(selectRandom(distance), dino.getHeight() + 50);

        //text for "Game Over"
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(5);

        //background music
        //music credit
//        Music by Bensound.com/free-music-for-videos
//        License code: Q4SLNCGIFBHAQ45J

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/background_track.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.45f);
        backgroundMusic.play();

        //death sound
        deathSound = Gdx.audio.newSound(Gdx.files.internal("sound/Pacman-death-sound.mp3"));


        //tracking score
        score = 0;
        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(3);
        //loading highest score
        loadHighestScore();

    }

    private void loadHighestScore(){
        try{
            FileHandle file = Gdx.files.local("highestScore.txt");
            if(file.exists()){
                highestScore = Integer.parseInt(file.readString());
            }
        }catch (Exception e){
            highestScore = 0;
        }
    }

    private void saveHighestScore(){
        try {
            FileHandle file = Gdx.files.local("highestScore.txt");
            file.writeString(Integer.toString(highestScore), false);
        }catch (Exception e){
            System.out.println("Error saving highest score");
        }
    }

    private void restartGame(){
        int[] distance = {600, 800, 1200, 1500, 1900, 2500};
        gameOver = false;
        backgroundMusic.play();
        dino.setPosition( Gdx.graphics.getWidth()/4f, ground.getHeight()-10);
        for(Collidable obstacle: obstacles){
            obstacle.setPositionCollidable(selectRandom(distance)+1000, ground.getHeight()-10);
        }
    }

    private int selectRandom(int[] distances){
        return distances[random.nextInt(distances.length)];
    }

    private void checkCollision(){
        for(Collidable obstacle: obstacles){
            if(dino.getBoundingCircle().overlaps(obstacle.getBoundingCircle())){
                gameOver = true;
                dino.HIT = true;
                dino.IDLE = false;
                dino.DUCKING = false;
                dino.RUNNING = false;

                backgroundMusic.stop();
                deathSound.play(0.6f);

                break;
            }
        }
    }

    @Override
    public void render(float delta) {
        //clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if(!gameOver){
            //update the stage and draw
            score += delta*100;
            stage.act(Gdx.graphics.getDeltaTime());
            checkCollision();

            if(score > highestScore){
                highestScore = (int) score;
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W)){
            restartGame();
        }

        stage.draw();

        //debugging er jonno bounding shape ta ke draw kore dekteci
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(0,1,0,1);
//        shapeRenderer.circle(dino.getBoundingCircle().x, dino.getBoundingCircle().y, dino.getBoundingCircle().radius);
//        for(Collidable obstacle: obstacles){
//            shapeRenderer.circle(obstacle.getBoundingCircle().x, obstacle.getBoundingCircle().y, obstacle.getBoundingCircle().radius);
//        }
//        shapeRenderer.end();

//        System.out.println(delta);
        batch.begin();
        scoreFont.draw(batch, "Score: " + (int)score, 50, Gdx.graphics.getHeight()-50);
        scoreFont.draw(batch, "Highest Score: " + highestScore, Gdx.graphics.getWidth()-400, Gdx.graphics.getHeight()-50);
        if(gameOver){
            font.draw(batch, "Game Over", Gdx.graphics.getWidth()/2f-200, Gdx.graphics.getHeight()/2f);
            saveHighestScore();
            score = 0;
        }
        batch.end();



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
