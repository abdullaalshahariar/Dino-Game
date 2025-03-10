package views;

import actors.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.files.FileHandle;

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
    private int score; // Declare the score variable here
    private int highestScore = 0;
    SpriteBatch batch;
    BitmapFont font;

    private Music backgroundMusic;
    private Sound deathSound;

    //only debugging er jonno bounding shape ta ke draw kore dekteci
    private ShapeRenderer shapeRenderer;


    public MainScreen(Main main){
        parent = main;
        random = new Random();

        //onlu debugging er jonno bounding shape ta ke draw kore dekteci
        shapeRenderer = new ShapeRenderer();
    }
    //Read the highest score from the file:
    private int readHighestScore() {
        FileHandle file = Gdx.files.internal("highest_score.txt");
        if (file.exists()) {
            String scoreString = file.readString();
            try {
                return Integer.parseInt(scoreString.trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
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

        //collision detection er jonno sob actor ke
        // ekta array te rakhbo
        obstacles = new Array<>();

        //3 ta cactus add korteci
        ActorCactus cactus1  = new ActorCactus(ground, new Texture(Gdx.files.internal("images/cactus_actor1.png")), 7);
        ActorCactus cactus2 = new ActorCactus(ground, new Texture(Gdx.files.internal("images/cactus_actor2.png")), 7);
        ActorCactus cactus3 = new ActorCactus(ground, new Texture(Gdx.files.internal("images/cactus_actor3.png")));
        //cactus gula ke stage e add kore dicci
        stage.addActor(cactus1);
        stage.addActor(cactus2);
        stage.addActor(cactus3);
        //cactus gula ke array te add kore dicci
        obstacles.add(cactus1);
        obstacles.add(cactus2);
        obstacles.add(cactus3);

        //before settig the distance
        //kicu fixed offset distance define kore dicci
        int[] distance = {600, 800, 1200, 1500, 1900, 2500};

        //positon gula set kore dite hobe jate overlap na hoy
        cactus1.setPosition(selectRandom(distance)+1000, ground.getHeight()-10); // Example starting position
        cactus2.setPosition(selectRandom(distance)+1000, ground.getHeight()-10); // Offset by 200 pixels
        cactus3.setPosition(selectRandom(distance)+1000, ground.getHeight()-10);

        //dinosure add korteci
        dino = new ActorDino(ground);
        //dino er position set kore dicci
        dino.setPosition( Gdx.graphics.getWidth()/4f, ground.getHeight()-10);
        //stage e add kore dicci
        stage.addActor(dino);


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
        highestScore = readHighestScore(); // Read the highest score when the game starts
    }


    private void restartGame() {
        int[] distance = {600, 800, 1200, 1500, 1900, 2500};
        gameOver = false;
        backgroundMusic.play();
        dino.setPosition(Gdx.graphics.getWidth() / 4f, ground.getHeight() - 10);
        for (Collidable obstacle : obstacles) {
            obstacle.setPositionCollidable(selectRandom(distance) + 1000, ground.getHeight() - 10);
        }
        score = 0; // Reset score
    }

    private void writeHighestScore(int score) {
        FileHandle file = Gdx.files.local("highest_score.txt");
        file.writeString(String.valueOf(score), false);
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

                if (score > highestScore) {
                    highestScore = score; // Update highest score
                    writeHighestScore(highestScore);
                }
                break;
            }

        }

    }


    @Override
    public void render(float delta) {
        // clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if (!gameOver) {
            // update the stage and draw
            stage.act(Gdx.graphics.getDeltaTime());
            checkCollision();
            score += delta * 100; // Increment score based on time
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            restartGame();
        }

        stage.draw();

        // debugging er jonno bounding shape ta ke draw kore dekteci
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.circle(dino.getBoundingCircle().x, dino.getBoundingCircle().y, dino.getBoundingCircle().radius);
        for (Collidable obstacle : obstacles) {
            shapeRenderer.circle(obstacle.getBoundingCircle().x, obstacle.getBoundingCircle().y, obstacle.getBoundingCircle().radius);
        }
        shapeRenderer.end();

        if (gameOver) {
            batch.begin();
            font.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2f - 50, Gdx.graphics.getHeight() / 2f);
            batch.end();
        }

        // Draw the score and highest score
        batch.begin();
        font.draw(batch, "Score: " + score, 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Highest Score: " + highestScore, 10, Gdx.graphics.getHeight() - 60); // Adjusted position
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

