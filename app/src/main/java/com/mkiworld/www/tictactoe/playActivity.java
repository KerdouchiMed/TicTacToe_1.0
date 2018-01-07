package com.mkiworld.www.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class playActivity extends AppCompatActivity {

    public final String GAME_STATE_KEY = "gameStateKey";
    public final String TOWER_KEY = "towerKey";
    public final String XSCORE_KEY = "XScoreKey";
    public final String OSCORE_KEY = "OScoreKey";
    public final String GAME_FINISHED_KEY = "GameFinishedKey";

    public final int PLAYER_X = 1;
    public final int PLAYER_O = 2;

    int onTower= PLAYER_X;
    int XScore = 0;
    int OScore = 0;
    boolean GameFinished = false;

    TextView WinnerTV;
    TextView TowerTV;
    TextView XScoreTV;
    TextView OScoreTV;

    LinearLayout GameOverLayout;

    int [] gameState= {0,0,0,0,0,0,0,0,0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        TowerTV = findViewById(R.id.TowerTV);
        XScoreTV = findViewById(R.id.XScoreTV);
        OScoreTV = findViewById(R.id.OScoreTV);
        WinnerTV = findViewById(R.id.WinnerTV);
        GameOverLayout = findViewById(R.id.GameOverLayout);


        if(savedInstanceState != null)
        {
            gameState = savedInstanceState.getIntArray(GAME_STATE_KEY);
            onTower = savedInstanceState.getInt(TOWER_KEY);
            XScore = savedInstanceState.getInt(XSCORE_KEY);
            OScore = savedInstanceState.getInt(OSCORE_KEY);
            GameFinished = savedInstanceState.getBoolean(GAME_FINISHED_KEY);
        }
        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(GAME_STATE_KEY,gameState);
        outState.putInt(TOWER_KEY,onTower);
        outState.putInt(XSCORE_KEY,XScore);
        outState.putInt(OSCORE_KEY,OScore);
        outState.putBoolean(GAME_FINISHED_KEY,GameFinished);
    }

    public void playAgain(View v)
    {
        int i;
        for(i=0;i<gameState.length;i++)
        {
            gameState[i]=0;
        }
        onTower = PLAYER_X;
        updateUI();
        GameFinished = false;
        GameOverLayout.setVisibility(View.GONE);


    }

    public void mainMenu(View v)
    {
        playActivity.this.finish();
    }

    public void onSetOorX(View v)
    {
        if(!GameFinished)
        {
            ImageView imageView = (ImageView) v;
            int tag = Integer.parseInt(v.getTag().toString());

            if(gameState[tag] == 0)
            {
                imageView.setTranslationY(-1000f);
                if(onTower == PLAYER_X)
                {
                    imageView.setImageResource(R.drawable.x);
                    onTower = PLAYER_O;
                    gameState[tag] = PLAYER_X;
                }
                else
                {
                    imageView.setImageResource(R.drawable.o);
                    onTower = PLAYER_X;
                    gameState[tag] = PLAYER_O;
                }
                imageView.animate().translationYBy(1000f).setDuration(100);
                if(gameIsOver()==1)
                {
                    WinnerTV.setText("Player X Win");
                    XScore++;
                    XScoreTV.setText(""+XScore);
                    GameFinished =true;
                }
                else if(gameIsOver()==2)
                {
                    WinnerTV.setText("Player O Win");
                    OScore++;
                    OScoreTV.setText(""+OScore);
                    GameFinished =true;
                }
                else if(gameIsOver()==0)
                {
                    WinnerTV.setText("No Winner");
                    GameFinished =true;

                }
                if(onTower == PLAYER_O)
                    TowerTV.setText("Tower To O");
                if(onTower == PLAYER_X)
                    TowerTV.setText("Tower To X");
            }

        }
        if(GameFinished)
        {
            GameOverLayout.setVisibility(View.VISIBLE);
        }
    }

    private void updateUI()
    {
        ImageView [] IVArray = new ImageView[9];
        IVArray[0] = findViewById(R.id.IV00);
        IVArray[1] = findViewById(R.id.IV01);
        IVArray[2] = findViewById(R.id.IV02);
        IVArray[3] = findViewById(R.id.IV10);
        IVArray[4] = findViewById(R.id.IV11);
        IVArray[5] = findViewById(R.id.IV12);
        IVArray[6] = findViewById(R.id.IV20);
        IVArray[7] = findViewById(R.id.IV21);
        IVArray[8] = findViewById(R.id.IV22);
        int i;
        for(i=0;i<IVArray.length;i++)
        {
            if(gameState[i]==PLAYER_O)
            {
                IVArray[i].setImageResource(R.drawable.o);
            }
            else if(gameState[i]==PLAYER_X)
            {
                IVArray[i].setImageResource(R.drawable.x);
            }
            else
                IVArray[i].setImageResource(0);
        }

        if(onTower==PLAYER_X)
            TowerTV.setText("Tower To X");
        else if(onTower==PLAYER_O)
            TowerTV.setText("Tower TO O");

        XScoreTV.setText(""+XScore);
        OScoreTV.setText(""+OScore);

        if(GameFinished)
        {
            GameOverLayout.setVisibility(View.VISIBLE);
        }
        else
            GameOverLayout.setVisibility(View.GONE);

    }

    //return le resultat si le jeux est terminer si non return -1
    public int gameIsOver()
    {
        int i;
        for(i=0;i<3;i++) {
            if(gameState[i*3]==gameState[(i*3)+1] && gameState[(i*3)+1]==gameState[(i*3)+2] && gameState[i*3]!=0)
                return gameState[i*3];

            if(gameState[i]==gameState[i+3] && gameState[i+3] == gameState[i+6] && gameState[i]!=0)
                return gameState[i];
        }
        if(gameState[0]==gameState[4] && gameState[4] == gameState[8] && gameState[0]!=0)
            return gameState[0];
        if(gameState[2]==gameState[4] && gameState[4] == gameState[6] && gameState[6]!=0)
            return gameState[6];
        if(gameState[0]!=0 && gameState[1]!=0 && gameState[2]!=0 &&
           gameState[3]!=0 && gameState[4]!=0 && gameState[5]!=0 &&
           gameState[6]!=0 && gameState[7]!=0 && gameState[8]!=0)
            return 0;
        return -1;
    }
}
