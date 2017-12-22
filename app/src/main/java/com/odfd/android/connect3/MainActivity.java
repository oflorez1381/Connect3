package com.odfd.android.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int YELLOW_PLAYER = 0;
    private static final int RED_PLAYER = 1;
    private static final int UNPLAYED = 2;

    private static final String YELLOW_PLAYER_MESSAGE = "Yellow";
    private static final String RED_PLAYER_MESSAGE = "Red";

    private int activePlayer = YELLOW_PLAYER;

    private int[] gameState = new int[9];

    private int[][] winningPositions = { {0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6} };

    private boolean gameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGameState();
    }

    private void initGameState(){
        for(int i=0; i < gameState.length; i++){
            gameState[i] = UNPLAYED;
        }
    }

    public void dropIn(View view){

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.valueOf(counter.getTag().toString());

        if (gameState[tappedCounter] == UNPLAYED && gameActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == YELLOW_PLAYER) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = RED_PLAYER;
            } else if (activePlayer == RED_PLAYER) {
                counter.setImageResource(R.drawable.red);
                activePlayer = YELLOW_PLAYER;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for (int[] winningPosition : winningPositions){

                if(isThereAWinner(winningPosition)){

                    String winner = RED_PLAYER_MESSAGE;
                    gameActive = false;

                    if(gameState[winningPosition[0]] == YELLOW_PLAYER){
                        winner = YELLOW_PLAYER_MESSAGE;
                    }

                    setWinnerMesage(winner + " has won!");
                    setPlayAgainLayoutVisibility(View.VISIBLE);

                } else {
                    boolean gameIsOver = true;

                    for(int counterState : gameState){
                        if(counterState == UNPLAYED){
                            gameIsOver = false;
                        }
                    }

                    if(gameIsOver){
                        setWinnerMesage("It's a draw!");
                        setPlayAgainLayoutVisibility(View.VISIBLE);
                    }
                }
            }

        }
    }

    private boolean isThereAWinner(int[] winningPosition) {
        return gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                gameState[winningPosition[0]] != UNPLAYED;
    }

    private void setPlayAgainLayoutVisibility(int invisible) {
        LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(invisible);
    }

    private void setWinnerMesage(String text) {
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
        winnerMessage.setText(text);
    }

    public void playAgain(View v){
        gameActive = true;
        activePlayer = YELLOW_PLAYER;
        setPlayAgainLayoutVisibility(View.INVISIBLE);
        initGameState();
        initGameBoard();
    }

    private void initGameBoard() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}
