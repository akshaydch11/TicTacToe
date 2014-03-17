package com.akshay.tictactoe;

import java.util.Random;

/**
 * This class will do all calculation of winning
 * or losing of game
 */
public class GamePlay {

    public static final char PLAYER_ONE = 'X';
    public static final char PLAYER_TWO = 'O';
    public static final char EMPTY_SPOT = ' ';

    private char mBoard[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private final int BOARD_SIZE = 9;


    private Random mRand;

    public GamePlay() {
        mRand = new Random();
    }


    /**
     * Check for a winner.
     * Match result    0 if no winner
     * 1 if it's a tie
     * 2 if X won
     * 3 if O won
     *
     * @return number for winner
     */

    public int checkForWinner() {

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == PLAYER_ONE &&
                    mBoard[i + 1] == PLAYER_ONE &&
                    mBoard[i + 2] == PLAYER_ONE)
                return 2;
            if (mBoard[i] == PLAYER_TWO &&
                    mBoard[i + 1] == PLAYER_TWO &&
                    mBoard[i + 2] == PLAYER_TWO)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == PLAYER_ONE &&
                    mBoard[i + 3] == PLAYER_ONE &&
                    mBoard[i + 6] == PLAYER_ONE)
                return 2;
            if (mBoard[i] == PLAYER_TWO &&
                    mBoard[i + 3] == PLAYER_TWO &&
                    mBoard[i + 6] == PLAYER_TWO)
                return 3;
        }

        // Check for diagonal wins
        if ((mBoard[0] == PLAYER_ONE &&
                mBoard[4] == PLAYER_ONE &&
                mBoard[8] == PLAYER_ONE) ||
                (mBoard[2] == PLAYER_ONE &&
                        mBoard[4] == PLAYER_ONE &&
                        mBoard[6] == PLAYER_ONE))
            return 2;
        if ((mBoard[0] == PLAYER_TWO &&
                mBoard[4] == PLAYER_TWO &&
                mBoard[8] == PLAYER_TWO) ||
                (mBoard[2] == PLAYER_TWO &&
                        mBoard[4] == PLAYER_TWO &&
                        mBoard[6] == PLAYER_TWO))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO)
                return 0;
        }

        return 1;
    }

    /**
     * It decides and returns best move for the computer to make.
     *
     * @return best move for the computer to make.
     */
    public int getComputerMove() {
        int move;

        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO) {
                char curr = mBoard[i];
                mBoard[i] = PLAYER_TWO;
                if (checkForWinner() == 3) {
                    return i;
                } else
                    mBoard[i] = curr;
            }
        }

        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != PLAYER_ONE && mBoard[i] != PLAYER_TWO) {
                char curr = mBoard[i];   // Save the current number
                mBoard[i] = PLAYER_ONE;
                if (checkForWinner() == 2) {
                    mBoard[i] = PLAYER_TWO;
                    return i;
                } else
                    mBoard[i] = curr;
            }
        }

        // Generate random move
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] == PLAYER_ONE || mBoard[move] == PLAYER_TWO);
        mBoard[move] = PLAYER_TWO;
        return move;
    }

    /**
     * Clear the board of all X's and O's by setting all spots to EMPTY_SPOT.
     */
    public void clearBoard() {
        for (int i = 0; i < mBoard.length; i++) {
            mBoard[i] = EMPTY_SPOT;
        }

    }

    /**
     * Check if location is available and set given player
     * at the given location on the game board.
     *
     * @param player   - The PLAYER_ONE or PLAYER_TWO
     * @param location - The location to place the move
     */
    public void setMove(char player, int location) {

        if (mBoard[location] == EMPTY_SPOT) {
            mBoard[location] = player;
        }
    }


}
