package br.com.up.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore;
    private TextView playerTwoScore;
    private TextView playerStatus;

    private Button [] buttons = new Button[9];
    private Button resetGame;

    private int playerOneScoreCount;
    private int playerTwoScoreCount;
    private int rountCount;

    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //rows
            {0,3,6},{1,4,7},{2,5,8},    // columns
            {0,4,8},{2,4,6}            // cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = findViewById(R.id.playerOneScore);
        playerTwoScore = findViewById(R.id.playerTwoScore);

        playerStatus   = findViewById(R.id.player_status);

        resetGame      = findViewById(R.id.resetGame);

        for(int i=0; i < buttons.length; i++){
            String buttonId = "btn_" + i;
            int resourceId  = getResources().getIdentifier(buttonId,"id",getPackageName());
            buttons[i] = findViewById(resourceId);
            buttons[i].setOnClickListener(this);
       }
        rountCount          = 0;
        playerTwoScoreCount = 0;
        playerTwoScoreCount = 0;

        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")){
            return;
        }
        String buttonId = view.getResources().getResourceEntryName(view.getId());

        int gameStatePointer = Integer.parseInt(buttonId.substring(buttonId.length()-1, buttonId.length())); //2

        if(activePlayer){
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#FFC34A"));

            gameState[gameStatePointer] = 0;
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;

        if(checkWinner()){
            if(activePlayer){
             // if true the first is playing if false the second is playing
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"player one won!!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"player two won!!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(rountCount == 9){
            Toast.makeText(this,"No Winners!", Toast.LENGTH_SHORT).show();
        }else{
            activePlayer = !activePlayer; // if true change to false and if false change to true
        }
        if(playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("Player one is winning");
        }else if(playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("Player Two is winning!");
        }else{
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");

                updatePlayerScore();
            }
        });

    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for (int [] winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] !=2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        rountCount = 0;
        activePlayer = true;

        for(int i=0; i< buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }

}

