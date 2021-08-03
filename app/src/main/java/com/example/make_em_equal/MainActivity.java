package com.example.make_em_equal;

import android.content.DialogInterface;
import static com.example.make_em_equal.Utils.showYesNoDialog;
import static com.example.make_em_equal.Utils.showInfoDialog;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.make_em_equal.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContent();
        setSupportActionBar(binding.toolbar);
        setupFAB();
        startNewGame();
    }

    private void setContent()
    {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contentMessage = binding.includeContentMain.contentMessage;
        num1a = binding.includeContentMain.num1a;
        space1 = binding.includeContentMain.space1;
        num1b = binding.includeContentMain.num1b;
        num2a = binding.includeContentMain.num2a;
        space2 = binding.includeContentMain.space2;
        num2b = binding.includeContentMain.num2b;
        answer1 = binding.includeContentMain.answer1;
        answer2 = binding.includeContentMain.answer2;
    }

    private void setupFAB()
    {
        binding.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "HINT: Try to make them equal to " + answerHint, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    private void startNewGame()
    {
        lineChooser = new LineChooser();
        String num1aString = String.valueOf(lineChooser.getNum1A());
        num1a.setText(num1aString);
        String num1bString = String.valueOf(lineChooser.getNum1B());
        num1b.setText(num1bString);
        String num2aString = String.valueOf(lineChooser.getNum2A());
        num2a.setText(num2aString);
        String num2bString = String.valueOf(lineChooser.getNum2B());
        num2b.setText(num2bString);
        String answer1String = (lineChooser.getNum1A()) + String.valueOf(lineChooser.getNum1B());
        answer1.setText(answer1String);
        //getNum2a has the 0 for 07 if you want to remove do it in an if statement.
        String answer2String = (lineChooser.getNum2A()) + String.valueOf(lineChooser.getNum2B());
        answer2.setText(answer2String);
        answerHint = lineChooser.getAnswer();
        space1.setText(R.string.space);
        space2.setText(R.string.space);
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
            case "/":
                answer1.setText(String.valueOf(lineChooser.getNum1A() / lineChooser.getNum1B()));
                break;
            case "_": case " ": case "reset":
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
            case "/":
                answer2.setText(String.valueOf(lineChooser.getNum2A() / lineChooser.getNum2B()));
                break;
            case "_": case " ": case "reset":
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
}