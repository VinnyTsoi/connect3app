package com.vinnytsoi.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean gameIsActive = true;

    // 0 = yellow, 1 = red
    int activePlayer = 0;

    // 2 means no counter in space
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };

    public void playAgain(View view){

        gameIsActive = true;

        // Winning message invisible
        LinearLayout playAgainLayout = findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);

        // 0 = yellow, 1 = red
        activePlayer = 0;

        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        // blank the spaces on the grid
        LinearLayout linearLayout1 = findViewById(R.id.linearLayout1);
        for(int i = 0; i < linearLayout1.getChildCount(); i++){
            ((ImageView)linearLayout1.getChildAt(i)).setImageResource(R.drawable.blank);
        }

        LinearLayout linearLayout2 = findViewById(R.id.linearLayout2);
        for(int i = 0; i < linearLayout2.getChildCount(); i++){
            ((ImageView)linearLayout2.getChildAt(i)).setImageResource(R.drawable.blank);
        }

        LinearLayout linearLayout3 = findViewById(R.id.linearLayout3);
        for(int i = 0; i < linearLayout3.getChildCount(); i++){
            ((ImageView)linearLayout3.getChildAt(i)).setImageResource(R.drawable.blank);
        }

    }

    public void dropIn(View view){

        ImageView counter = (ImageView) view;

        // what counter was clicked
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        // was a valid space clicked
        if (gameState[tappedCounter] == 2 && gameIsActive){

            counter.setTranslationY(-1000f);
            gameState[tappedCounter] = activePlayer;

            if(activePlayer == 0){
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().
                    translationYBy(1000f).
                    rotation(360).
                    setDuration(200);

            // Did a player win?
            for(int[]winningPosition : winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    // Who has won?
                    String winner = "Yellow";;
                    if (activePlayer == 0)
                        winner = "Red";

                    // show a message
                    LinearLayout playAgainLayout = findViewById(R.id.playAgainLayout);

                    playAgainLayout.setTranslationY(-1000f);
                    playAgainLayout.animate().
                            translationYBy(1000f).
                            rotation(180).
                            setDuration(500);

                    playAgainLayout.animate().
                            rotationBy(360f).
                            setDuration(500);

                    playAgainLayout.setVisibility(View.VISIBLE);

                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");

                    gameIsActive = false;
                }
            }
        }

        // check if it is a draw
        if (gameIsActive){
            boolean gameIsOver = true;
            for(int i = 0; i < gameState.length; i++){
                if (gameState[i] == 2) {
                    gameIsOver = false;
                    break;
                }
            }

            // print message to screen
            if(gameIsOver == true){
                LinearLayout playAgainLayout = findViewById(R.id.playAgainLayout);

                playAgainLayout.setTranslationY(-1000f);
                playAgainLayout.animate().
                        translationYBy(1000f).
                        rotation(180).
                        setDuration(500);

                playAgainLayout.animate().
                        rotationBy(360f).
                        setDuration(500);

                playAgainLayout.setVisibility(View.VISIBLE);

                TextView winnerMessage = findViewById(R.id.winnerMessage);
                winnerMessage.setText("It's a draw");
                gameIsActive = false;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
