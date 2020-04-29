package com.shuhan.tictactoe.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

public class Game {
    private static final String TAG = Game.class.getSimpleName();
    private static final int BOARD_SIZE = 3;

    public Player player1;
    public Player player2;

    public Player currentPlayer;
    public Cell[][] cells;

    public MutableLiveData<Player> winner = new MutableLiveData<>();

    public Game(String playerOne, String playerTwo){
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        player1 = new Player(playerOne,"X");
        player2 = new Player(playerTwo,"O");
        currentPlayer = player1;
    }

    public void switchPlayer(){
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    public boolean hasGameEnded(){
        if(hasThreeSameHorizontal() || hasThreeSameVertical() || hasThreeSameDiagonal()){
            winner.setValue(currentPlayer);
            return true;
        }

        if(isBoardFull()){
            winner.setValue(null);
            return true;
        }

        return false;
    }

    private boolean hasThreeSameHorizontal(){
        try {
            for(int i = 0 ; i < BOARD_SIZE ; i++){
                if(areEqual(cells[i][0], cells[i][1], cells[i][2]))
                    return true;
            }
            return false;
        }catch (NullPointerException e){
            Log.d(TAG, "hasThreeSameHorizontal: "+ e.getMessage());
            return false;
        }
    }

    private boolean hasThreeSameVertical(){
        try {
            for(int i = 0 ; i < BOARD_SIZE ; i++){
                if(areEqual(cells[0][i], cells[1][i], cells[2][i]))
                    return true;
            }
            return false;
        }catch (NullPointerException e){
            Log.d(TAG, "hasThreeSameVertical: "+ e.getMessage());
            return false;
        }
    }

    private boolean hasThreeSameDiagonal(){
        try {
            return areEqual(cells[0][0], cells[1][1], cells[2][2]) || areEqual(cells[0][2], cells[2][2], cells[2][0]);
        }catch (NullPointerException e){
            Log.d(TAG, "hasThreeSameDiagonal: "+e.getMessage());
            return false;
        }
    }

    private boolean isBoardFull(){
        for (Cell[] row:cells){
            for(Cell cell: row){
                if(cell == null || cell.isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean areEqual(Cell... cells){
        if(cells == null || cells.length == 0){
            return false;
        }

        for (Cell cell : cells){
            if (cell == null || cell.player.value == null || cell.player.value.isEmpty() ){
                return false;
            }
        }


        Cell comparisonBase = cells[0];

        for (int i = 1 ; i < cells.length ; i++){
            if(!comparisonBase.player.value.equals(cells[i].player.value))
                return false;
        }

        return true;
    }

    public void reset(){
        player1 = null;
        player2 = null;
        currentPlayer = null;
        cells = null;
    }

}
