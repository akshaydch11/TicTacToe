package com.sabudevelopers.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * This activity consists of UI of board where
 * Tic-Tac-Toe will be played
 * Also it will maintain the score
 */
public class TicTacToeActivity extends Activity implements View.OnClickListener {

    GamePlay mGame;
    private static int mUserWins = 0;
    private static int mComputerWins = 0;
    private static int mTieCount = 0;
    private TextView userScore, tieScore, computerScore;
    private Button mBoardButtons[] = new Button[9];
    private boolean isSinglePlayer = false;
    private String playerOneName, playerTwoName;
    private static boolean isCrossPlayerTurn = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        isSinglePlayer = getIntent().getExtras().getBoolean("isSinglePlayer");
        playerOneName = getIntent().getExtras().getString("PlayerOneName");
        playerTwoName = getIntent().getExtras().getString("PlayerTwoName");

        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);

        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);

        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        userScore = (TextView) findViewById(R.id.userScore);
        tieScore = (TextView) findViewById(R.id.tieScore);
        computerScore = (TextView) findViewById(R.id.computerScore);

        mGame = new GamePlay();
        startNewGame();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_quit:
                optionItemAlertDialogs("Are you sure you want to Quit Game ?", R.id.action_quit);
                break;
            case R.id.action_new:
                optionItemAlertDialogs("Are you sure you want to Start New Game ? " +
                        "All your progress will be lost ", R.id.action_new);
                startNewGame();
                break;
            case R.id.action_main_menu:
                optionItemAlertDialogs("Are you sure you want switch to Main Menu ? " +
                        "All your progress will be lost ", R.id.action_main_menu);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles different alert boxes for each options menu item
     *
     * @param message message to be shown in alert box
     * @param num     to identify which menu item is clicked
     */

    private void optionItemAlertDialogs(String message, final int num) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        alert.setMessage(message);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                if (num == R.id.action_new) {
                    mUserWins = mTieCount = mComputerWins = 0;
                    startNewGame();
                } else if (num == R.id.action_main_menu) {
                    Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else if (num == R.id.action_quit) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        alert.show();

    }

    /**
     * It starts new game by resetting all user scores
     * and buttons.
     */

    private void startNewGame() {
        mGame.clearBoard();
        for (Button btn : mBoardButtons) {
            btn.setText(" ");
            btn.setEnabled(true);
            btn.setOnClickListener(this);
        }
        isCrossPlayerTurn = true;
        userScore.setText(playerOneName + " : " + mUserWins);
        tieScore.setText("Tie : " + mTieCount);
        computerScore.setText(playerTwoName + " : " + mComputerWins);
        //Toast.makeText(this, playerOneName + " go First", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method handles click events of buttons
     * on board where "X" or "O" get displayed
     *
     * @param view
     */

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            Button button = (Button) view;
            int location = -1;
            for (int i = 0; i < mBoardButtons.length; i++) {
                if (button.getId() == mBoardButtons[i].getId())
                    location = i;
            }
            if (isSinglePlayer) {
                setMove(GamePlay.PLAYER_ONE, location);


                if (mGame.checkForWinner() == 0) {
                    location = mGame.getComputerMove();
                    setMove(GamePlay.PLAYER_TWO, location);
                }
                if (mGame.checkForWinner() == 0) {
                    //Toast.makeText(this, "Your TURN", Toast.LENGTH_SHORT).show();
                } else {
                    alertDialogShow(mGame.checkForWinner());
                }
            } else {
                if (isCrossPlayerTurn) {
                    setMove(GamePlay.PLAYER_ONE, location);
                    isCrossPlayerTurn = false;
                } else {
                    setMove(GamePlay.PLAYER_TWO, location);
                    isCrossPlayerTurn = true;
                }
                if (mGame.checkForWinner() > 0) {
                    alertDialogShow(mGame.checkForWinner());
                }
            }

        }
    }

    /**
     * Shows alert dialog when result someone wins or tie
     * Match result :   0 - continue play
     * 1 - match tie
     * 2 - X wins
     * 3 - O wins
     *
     * @param num Result of who won match
     */
    private void alertDialogShow(int num) {

        String message = "";
        if (num == 1) {
            message = "It's Tie";
            mTieCount++;
        } else if (num == 2) {
            message = playerOneName + " (X) wins";
            mUserWins++;
        } else if (num == 3) {
            message = playerTwoName + " (O) wins";
            mComputerWins++;
        }


        AlertDialog.Builder alert = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        //alert.setTitle("Result");
        alert.setMessage(message);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startNewGame();
            }
        });

        alert.show();

    }

    /**
     * Sets button to either X or O as per user event
     *
     * @param player   player either X or O
     * @param location location where player want to move
     */

    private void setMove(char player, int location) {

        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == GamePlay.PLAYER_ONE)
            mBoardButtons[location].setTextColor(Color.rgb(0, 150, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(190, 0, 0));
    }
}
