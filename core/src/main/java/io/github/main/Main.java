package io.github.main;


import com.badlogic.gdx.Game;
import views.*;

public class Main extends Game{
    private LoadingScreen loadingScreen;
    private EndScreen endScreen;
    private MainScreen mainScreen;
    private MenuScreen menuScreen;
    private PreferencesScreen preferencesScreen;

    public static final int MENU = 0;
    public static final int PREFERCENCS = 1;
    public static final int APPLICATION = 2;
    public static final int ENDGAME = 3;

    public void changeScreen(int screen){
        switch (screen){
            case MENU:
                if(menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PREFERCENCS:
                if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case APPLICATION:
                if(mainScreen == null) mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                menuScreen.stopMusic();
                break;
            case ENDGAME:
                if(endScreen == null) endScreen = new EndScreen(this);
                this.setScreen(endScreen);
                break;
        }
    }

    @Override
    public void create(){
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    @Override
    public void dispose(){
        loadingScreen.dispose();
        mainScreen.dispose();
        menuScreen.dispose();
        preferencesScreen.dispose();
        endScreen.dispose();
    }
}

