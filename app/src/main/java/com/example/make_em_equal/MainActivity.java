package com.example.make_em_equal;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.make_em_equal.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
    private TextView answerHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent();
        setSupportActionBar(binding.toolbar);
        setupFAB();

        contentMessage = binding.includeContentMain.contentMessage;
        num1a = binding.includeContentMain.num1a;
        space1 = binding.includeContentMain.space1;
        num1b = binding.includeContentMain.num1b;
        num2a = binding.includeContentMain.num2a;
        space2 = binding.includeContentMain.space2;
        num2b = binding.includeContentMain.num2b;
        answer1 = binding.includeContentMain.answer1;
        answer2 = binding.includeContentMain.answer2;
        //answer = binding.includeContentMain.answer;

        startNewGame();
    }

    private void startNewGame() {
        lineChooser = new LineChooser();
        String num1aString = String.valueOf(lineChooser.getNum1A());
        num1a.setText(num1aString);
        String num1bString = String.valueOf(lineChooser.getNum1B());
        num1b.setText(num1bString);
        String num2aString = String.valueOf(lineChooser.getNum2A());
        num2a.setText(num2aString);
        String num2bString = String.valueOf(lineChooser.getNum2B());
        num2b.setText(num2bString);
        String answer1String = String.valueOf(lineChooser.getNum1A() + " " + lineChooser.getNum1B());
        answer1.setText(answer1String);
        //getNum2a has the 0 for 07 if you want to remove do it in an if statement.
        String answer2String = String.valueOf(lineChooser.getNum2A() + " " + lineChooser.getNum2B());
        answer2.setText(answer2String);
        //String hintString = String.valueOf(lineChooser.getAnswer());
        //answer.setText(hintString);
    }

    private void setContent()
    {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setupFAB()
    {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectOperator1(View view) {
        space1.setText(((Button) view).getText().toString());
        switch (((Button) view).getText().toString()){
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
                String answer1Text = num1a.getText().toString() + " " + num1b.getText().toString();
                answer1.setText(answer1Text);
                break;
        }

        if (isEqual()){
            contentMessage.setText(R.string.great_job);
        }
    }
    public void selectOperator2(View view) {
        space2.setText(((Button) view).getText().toString());
        switch (((Button) view).getText().toString()) {
            case "+":
                answer2.setText(String.valueOf(lineChooser.getNum2A() + lineChooser.getNum2B()));
                break;
            case "-":
                answer2.setText(String.valueOf(lineChooser.getNum2A() - lineChooser.getNum2B()));
                break;
            case "x":
            case "X":
                answer2.setText(String.valueOf(lineChooser.getNum2A() * lineChooser.getNum2B()));
                break;
            case "/":
                answer2.setText(String.valueOf(lineChooser.getNum2A() / lineChooser.getNum2B()));
                break;
            case "_": case " ": case "reset":
                space1.setText(R.string.space);
                String answer1Text = num1a.getText().toString() + " " + num1b.getText().toString();
                answer1.setText(answer1Text);
                break;

        }
       if (isEqual()){
           contentMessage.setText(R.string.great_job);
       }
    }

    public boolean isEqual(){
        boolean isEqual = false;
        if (answer1.getText().toString().equals(answer2.getText().toString())){
            isEqual = true;
        }
        return isEqual;
    }
}