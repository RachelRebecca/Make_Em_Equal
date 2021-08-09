package com.example.make_em_equal;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

//import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.DialogInterface;

import static com.example.make_em_equal.LineChooser.getGameFromJSON;
import static com.example.make_em_equal.LineChooser.getJSONFromGame;
import static com.example.make_em_equal.Utils.showYesNoDialog;
import static com.example.make_em_equal.Utils.showInfoDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.make_em_equal.databinding.ActivityMainBinding;
import com.example.make_em_equal.databinding.MainIncludeBottomBarAndFabBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;
    private MainIncludeBottomBarAndFabBinding bottomBarAndFabBinding;

    private LineChooser lineChooser;
    private TextView contentMessage;
    private TextView num1a;
    private TextView space1;
    private TextView num1b;
    private TextView num2a;
    private TextView space2;
    private TextView num2b;
    private TextView answer1;
    private TextView answer2;
    private double answerHint;

    private TextView hintBox;

    private final String KEY_GAME = "GAME";
    private final String SPACE_ONE = "1";
    private final String SPACE_TWO = "2";
    private final String ANSWER_ONE = "ANSWER_ONE";
    private final String ANSWER_TWO = "ANSWER_TWO";
    private final String ANSWER_HINT = "ANSWER_HINT";
    private String HINT_BOX;

    @Override
    protected void onStart() {
        super.onStart();

        restoreOrSetFromPreferencesHintSettings();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_GAME, getJSONFromGame(lineChooser));
        outState.putString(SPACE_ONE, space1.getText().toString());
        outState.putString(SPACE_TWO, space2.getText().toString());
        outState.putString(ANSWER_ONE, answer1.getText().toString());
        outState.putString(ANSWER_TWO, answer2.getText().toString());
        outState.putDouble(ANSWER_HINT, answerHint);
        outState.putString(HINT_BOX, hintBox.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lineChooser = getGameFromJSON(savedInstanceState.getString(KEY_GAME));
        space1.setText(savedInstanceState.getString(SPACE_ONE, "_"));
        space2.setText(savedInstanceState.getString(SPACE_TWO, "_"));
        answer1.setText(savedInstanceState.getString(ANSWER_ONE, "_"));
        answer2.setText(savedInstanceState.getString(ANSWER_TWO, "__"));
        answerHint = savedInstanceState.getDouble(ANSWER_HINT);
        hintBox.setText(savedInstanceState.getString(HINT_BOX, ""));
        updateUI();
    }

    private void updateUI() {
        num1a.setText(String.valueOf(lineChooser.getNum1A()));
        num1b.setText(String.valueOf(lineChooser.getNum1B()));
        num2a.setText(String.valueOf(lineChooser.getNum2A()));
        num2b.setText(String.valueOf(lineChooser.getNum2B()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContent();
        setSupportActionBar(binding.includeToolbar.toolbar);
        setupFAB();

        startNewGame();
    }

    private void setContent()
    {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        bottomBarAndFabBinding = MainIncludeBottomBarAndFabBinding.bind(binding.getRoot());

        setContentView(binding.getRoot());

        contentMessage = binding.includeContentMain.contentMessage;
        num1a = binding.includeContentMain.questionRow.num1a;
        space1 = binding.includeContentMain.questionRow.space1;
        num1b = binding.includeContentMain.questionRow.num1b;
        num2a = binding.includeContentMain.questionRow.num2a;
        space2 = binding.includeContentMain.questionRow.space2;
        num2b = binding.includeContentMain.questionRow.num2b;
        answer1 = binding.includeContentMain.answerRow.answer1;
        answer2 = binding.includeContentMain.answerRow.answer2;

        hintBox = bottomBarAndFabBinding.hintBox;
    }

    private void setupFAB()
    {
        bottomBarAndFabBinding.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hint: Try to make them equal to " + answerHint, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void startNewGame()
    {
        //create new LineChooser object
        lineChooser = new LineChooser();

        //set question row
        String num1aString = String.valueOf(lineChooser.getNum1A());
        num1a.setText(num1aString);
        space1.setText(R.string.space);
        String num1bString = String.valueOf(lineChooser.getNum1B());
        num1b.setText(num1bString);
        String num2aString = String.valueOf(lineChooser.getNum2A());
        num2a.setText(num2aString);
        space2.setText(R.string.space);
        String num2bString = String.valueOf(lineChooser.getNum2B());
        num2b.setText(num2bString);

        //set answer row
        if (lineChooser.getNum1A() == 0){
            answer2.setText(String.valueOf(lineChooser.getNum1B()));
        }
        else
        {
            String answer1String = (lineChooser.getNum1A()) + String.valueOf(lineChooser.getNum1B());
            answer1.setText(answer1String);
        }
        if (lineChooser.getNum2A() == 0){
            answer2.setText(String.valueOf(lineChooser.getNum2B()));
        }
        else
        {
            String answer2String = (lineChooser.getNum2A()) + String.valueOf(lineChooser.getNum2B());
            answer2.setText(answer2String);
        }

        //set up hint value
        answerHint = lineChooser.getAnswer();

        contentMessage.setText(R.string.make_equal);

        HINT_BOX = getString(R.string.hint_key);
        restoreOrSetFromPreferencesHintSettings();
    }

    public void selectOperator1(View view)
    {
        space1.setText(((Button) view).getText().toString());
        switch (((Button) view).getText().toString())
        {
            case "+":
                answer1.setText(String.valueOf(lineChooser.getNum1A() + lineChooser.getNum1B()));
                break;
            case "-":
                answer1.setText(String.valueOf(lineChooser.getNum1A() - lineChooser.getNum1B()));
                break;
            case "x": case "X":
                answer1.setText(String.valueOf(lineChooser.getNum1A() * lineChooser.getNum1B()));
                break;
            case "/": case "÷":
                Double number1a = Double.parseDouble(String.valueOf(lineChooser.getNum1A()));
                Double number1b = Double.parseDouble(String.valueOf(lineChooser.getNum1B()));
                double dividedBy1 = (number1a / number1b);
                double roundDividedBy1 = Math.round(dividedBy1 * 10000.0)/10000.0;
                answer1.setText(String.valueOf(roundDividedBy1));
                break;
            case "_": case " ": case "reset": case "Reset":
                space1.setText(R.string.space);
                String answer1Text = num1a.getText().toString() + num1b.getText().toString();
                answer1.setText(answer1Text);
                break;
        }

        setEqualBoard();
    }
    public void selectOperator2(View view)
    {
        space2.setText(((Button) view).getText().toString());
        switch (((Button) view).getText().toString()) {
            case "+":
                answer2.setText(String.valueOf(lineChooser.getNum2A() + lineChooser.getNum2B()));
                break;
            case "-":
                answer2.setText(String.valueOf(lineChooser.getNum2A() - lineChooser.getNum2B()));
                break;
            case "x": case "X": case "*":
                answer2.setText(String.valueOf(lineChooser.getNum2A() * lineChooser.getNum2B()));
                break;
            case "/": case "÷":
                Double number2a = Double.parseDouble(String.valueOf(lineChooser.getNum2A()));
                Double number2b = Double.parseDouble(String.valueOf(lineChooser.getNum2B()));
                double dividedBy2 = (number2a / number2b);
                double roundDividedBy2 = Math.round(dividedBy2 * 10000.0)/10000.0;
                answer2.setText(String.valueOf(roundDividedBy2));
                break;
            case "_": case " ": case "reset": case "Reset":
                space2.setText(R.string.space);
                String answer2Text = num2a.getText().toString() + num2b.getText().toString();
                answer2.setText(answer2Text);
                break;

        }
        setEqualBoard();
    }

    private void setEqualBoard() {
        if (isEqual())
        {
            contentMessage.setText(R.string.great_job);
            showGameOverDialog();
        }
        else
        {
            contentMessage.setText(R.string.make_equal);
        }
    }

    public boolean isEqual()
    {
        boolean isEqual = false;
        if (answer1.getText().toString().equals(answer2.getText().toString()))
        {
            isEqual = true;
        }
        else if (Double.parseDouble(answer1.getText().toString()) == Double.parseDouble(answer2.getText().toString()))
        {
            isEqual = true;
        }
        return isEqual;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.start_new_game)
        {
            startNewGame();
            return true;
        }
        else if (id == R.id.action_settings)
        {
            showSettings();
            return true;
        }
        else if (id == R.id.about)
        {
            openAboutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Comes from PM starter code
    /**
     * Shows an Android (nicer) equivalent to JOptionPane
     *
     */
    private void showGameOverDialog() {
        final DialogInterface.OnClickListener newGameListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startNewGame();
            }
        };
        showYesNoDialog(this, "You won!", "Congratulations! You won the game! Play again?", newGameListener, null);
    }

    private void openAboutDialog(){
        showInfoDialog(this, "About Make 'Em Equal", "Click on the math " +
                "operator to insert that operator into the pair of numbers at the top of the " +
                "calculator. Do this to the other pair of numbers as well. Keep clicking on the " +
                "operations until you make 'em equal!");
    }

    private void showSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        settingsLauncher.launch (intent);
    }

    ActivityResultLauncher<Intent> settingsLauncher = registerForActivityResult (
            new ActivityResultContracts.StartActivityForResult (),
            result -> restoreOrSetFromPreferencesHintSettings ());

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            restoreOrSetFromPreferencesHintSettings();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void restoreOrSetFromPreferencesHintSettings() {
        SharedPreferences sp = getDefaultSharedPreferences(this);
        setHintBoxRevealed(sp.getBoolean(getString(R.string.hint_key), false));
    }

    public void setHintBoxRevealed(Boolean sp){
        if (sp) {
            String hintText = "Hint: Try to make them equal to " + answerHint;
            hintBox.setText(hintText);
        }
        else{
            hintBox.setText("");
        }
    }

}