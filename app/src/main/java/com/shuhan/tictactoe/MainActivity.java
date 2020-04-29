package com.shuhan.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

import com.shuhan.tictactoe.databinding.ActivityMainBinding;
import com.shuhan.tictactoe.model.Player;
import com.shuhan.tictactoe.utilities.StringUtility;
import com.shuhan.tictactoe.view.GameBeginDialog;
import com.shuhan.tictactoe.view.GameEndDialog;
import com.shuhan.tictactoe.viewmodel.GameViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String GAME_BEGIN_DIALOG_TAG = "game_begin_dialog_tag";
    private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";
    private static final String NO_WINNER = "No Winner";

    private GameViewModel gameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        promptForPlayers();
    }

    public void promptForPlayers(){
        GameBeginDialog gameBeginDialog = GameBeginDialog.newInstance(this);
        gameBeginDialog.show(getSupportFragmentManager(),GAME_BEGIN_DIALOG_TAG);
    }

    public void onPlayersSet(String player1, String player2) {
        initDataBinding(player1,player2);
    }

    private void initDataBinding(String player1, String player2){
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        gameViewModel.init(player1,player2);

        activityMainBinding.setGameViewModel(gameViewModel);

        setUpGameEndListener();
    }

    private void setUpGameEndListener(){
        gameViewModel.getWinner().observe(this, this::onGameWinnerChange);
    }

    private void onGameWinnerChange(Player winner) {
        String winnerName = winner == null || StringUtility.isNullOrEmpty(winner.name) ? NO_WINNER: winner.name;

        GameEndDialog gameEndDialog = GameEndDialog.newInstance(this,winnerName);
        gameEndDialog.show(getSupportFragmentManager(),GAME_BEGIN_DIALOG_TAG);
    }

}
