package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView resultTV;
    int lastNum;
    double lastNumDouble;
    String lastAction;
    boolean actionPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTV = findViewById(R.id.result_box);

        Button btn_0 = findViewById(R.id.number0);
        Button btn_1 = findViewById(R.id.number1);
        Button btn_2 = findViewById(R.id.number2);
        Button btn_3 = findViewById(R.id.number3);
        Button btn_4 = findViewById(R.id.number4);
        Button btn_5 = findViewById(R.id.number5);
        Button btn_6 = findViewById(R.id.number6);
        Button btn_7 = findViewById(R.id.number7);
        Button btn_8 = findViewById(R.id.number8);
        Button btn_9 = findViewById(R.id.number9);

        Button btn_sin = findViewById(R.id.sin_btn);
        Button btn_cos = findViewById(R.id.cos_btn);
        Button btn_tan = findViewById(R.id.tan_btn);
        Button btn_sqrt = findViewById(R.id.sqrt_btn);

        Button plus_btn = findViewById(R.id.plus_btn);
        Button minus_btn = findViewById(R.id.minus_btn);
        Button mult_btn = findViewById(R.id.btn_mult);
        Button divide_btn = findViewById(R.id.divide_btn);

        Button equal_btn = findViewById(R.id.equal);
        Button clear_btn = findViewById(R.id.clear);
//-------------------------------------------------------------------
        NumberBtnListener btnListener = new NumberBtnListener();
        btn_0.setOnClickListener(btnListener);
        btn_1.setOnClickListener(btnListener);
        btn_2.setOnClickListener(btnListener);
        btn_3.setOnClickListener(btnListener);
        btn_4.setOnClickListener(btnListener);
        btn_5.setOnClickListener(btnListener);
        btn_6.setOnClickListener(btnListener);
        btn_7.setOnClickListener(btnListener);
        btn_8.setOnClickListener(btnListener);
        btn_9.setOnClickListener(btnListener);

        ActionBtnListener actionBtnListener = new ActionBtnListener();
        plus_btn.setOnClickListener(actionBtnListener);
        minus_btn.setOnClickListener(actionBtnListener);
        mult_btn.setOnClickListener(actionBtnListener);
        divide_btn.setOnClickListener(actionBtnListener);

        TrigoBtnListener trigoBtnListener = new TrigoBtnListener();
        btn_sin.setOnClickListener(trigoBtnListener);
        btn_cos.setOnClickListener(trigoBtnListener);
        btn_tan.setOnClickListener(trigoBtnListener);
        btn_sqrt.setOnClickListener(trigoBtnListener);

        clear_btn.setOnClickListener(view -> resultTV.setText(""));

        equal_btn.setOnClickListener(view -> {
            if(lastAction == null) return;
            evaluate();
        });
    }

    private void evaluate(){
        String numStr = resultTV.getText().toString();
        int newNum = Integer.parseInt(numStr);


        switch (lastAction) {
            case "+":
                resultTV.setText(lastNum + newNum + "");
                break;
            case "-":
                resultTV.setText(lastNum - newNum + "");
                break;
            case "X":
                resultTV.setText(lastNum * newNum + "");
                break;
            case "/":
                if (newNum != 0)
                    resultTV.setText(lastNum / newNum + "");
                else {
                    Toast tst = Toast.makeText(MainActivity.this, "Can't divide by 0", Toast.LENGTH_SHORT);
                    tst.setGravity(Gravity.CENTER,0,0);
                    tst.show();
                }
                break;
        }
        actionPressed = true;
        lastAction = null;
    }

    private class NumberBtnListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String numStr = ((Button)view).getText().toString();
            if(actionPressed){
                resultTV.setText(numStr);
                actionPressed = false;
            }
            else
                resultTV.setText(resultTV.getText() + numStr);
        }
    }

    private class ActionBtnListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (lastAction != null)
                    evaluate();

            String numStr = resultTV.getText().toString();
            if (!numStr.equals("")) {
                if(numStr.contains("."))
                    lastNumDouble = Double.parseDouble(numStr);
                else
                    lastNum = Integer.parseInt(numStr);

                lastAction = ((Button) view).getText().toString();
                actionPressed = true;
            }
        }
    }

    private class TrigoBtnListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String numStr = resultTV.getText().toString();
            if(numStr.equals("")) return;

            lastNum = Integer.parseInt(numStr);
            String caseName = ((Button)view).getText().toString();

            switch (caseName){
                case "sin":
                    resultTV.setText(Math.sin(lastNum) + "");
                    break;
                case "cos":
                    resultTV.setText(Math.cos(lastNum) + "");
                    break;
                case "tan":
                    resultTV.setText(Math.tan(lastNum) + "");
                    break;
                case "√":
                    if(lastNum >= 0)
                        resultTV.setText(Math.sqrt(lastNum) + "");
                    break;
            }
        }
    }
}