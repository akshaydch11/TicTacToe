package com.sabudevelopers.tictactoe;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This fragment is used for taking information about players
 * like name of user.
 */
@SuppressLint("ValidFragment")
public class PlayerInfoFragment extends Fragment {

    TextView num_players;
    String playerNumbers, playerOne, playerTwo;
    boolean isSinglePlayer;
    Button startNewGame;
    EditText playerOneEdit, playerTwoEdit;


	public PlayerInfoFragment(String players) {
        playerNumbers = players;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("PlayerInfo", "onCreateview");
        View rootView = inflater.inflate(R.layout.player_info, container, false);

        initialize(rootView);
        return rootView;
    }

    private void initialize(View v) {
        num_players = (TextView) v.findViewById(R.id.num_of_player);
        num_players.setText(playerNumbers);

        startNewGame = (Button) v.findViewById(R.id.start_game);
        startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callStartNewGame();
            }
        });

        playerOneEdit = (EditText) v.findViewById(R.id.edit_player_one);
        playerTwoEdit = (EditText) v.findViewById(R.id.edit_player_two);
        isSinglePlayer = false;
        if (playerNumbers.equals("One Player")) {
            isSinglePlayer = true;
            playerTwoEdit.setText("Player 2");
            playerTwoEdit.setFocusable(false);
        }
    }

    /**
     * Handles "Start Game" buttons listener and take user
     * TicTacToeActivity page
     */
    private void callStartNewGame() {

        playerOne = ((Editable) playerOneEdit.getText()).toString();
        playerTwo = ((Editable) playerTwoEdit.getText()).toString();

        playerOne = (playerOne.equals("")) ? "Player 1" : playerOne;
        playerTwo = (playerTwo.equals("")) ? "Player 2" : playerTwo;

        Intent intent = new Intent(getActivity(), TicTacToeActivity.class);

        intent.putExtra("PlayerOneName", playerOne).putExtra("PlayerTwoName", playerTwo)
                .putExtra("isSinglePlayer", isSinglePlayer);
        startActivity(intent);

        getActivity().finish();


    }
}
