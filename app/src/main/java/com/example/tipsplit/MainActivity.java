package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "tipSplitMainActivity";
    private EditText billAmountInput;
    private TextView tipAmountTextBox;
    private TextView totalAmountWithTip;
    private RadioGroup radioGroup;
    private EditText noOfPeople;
    private TextView billAmountPerPerson;
    private String totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billAmountInput = findViewById(R.id.editTextNumberDecimal);
        tipAmountTextBox = findViewById(R.id.tipAmountReadOnlyTextView);
        totalAmountWithTip = findViewById(R.id.totalWithTipReadOnly);
        radioGroup = findViewById(R.id.radioGroup);
        noOfPeople = findViewById(R.id.editTextNumber);
        billAmountPerPerson = findViewById(R.id.totalPerPersonReadOnly);
    }

    public void onClickTipRadioButton(View view){
        String billAmountTotal = billAmountInput.getText().toString();
        if(billAmountTotal.isEmpty()){
           radioGroup.clearCheck();
           Toast.makeText(this, "enter amount", Toast.LENGTH_LONG).show();
        }
        else {
            String[] billStringSplit = billAmountTotal.split("\\$");
            billAmountTotal = billStringSplit[billStringSplit.length - 1];
            Float billAmountInFloat = Float.parseFloat(billAmountTotal);
            Float tipAmount = new Float(0);
            Float totalWithTip = new Float(0);
            DecimalFormat finalValue = new DecimalFormat("0.00");
            if (view.getId() == R.id.radioButton_12_percent) {
                tipAmount = (billAmountInFloat * 12) / 100;
            }
            else if (view.getId() == R.id.radioButton_15_percent) {
                tipAmount = (billAmountInFloat * 15) / 100;

            }
            else if (view.getId() == R.id.radioButton_18_percent) {
                tipAmount = (billAmountInFloat * 18) / 100;

            }
            else if (view.getId() == R.id.radioButton_20_percent) {
                tipAmount = (billAmountInFloat * 20) / 100;

            }
            totalWithTip = tipAmount + billAmountInFloat;
            String tipAmountStr = "$"+ finalValue.format(tipAmount);
            String totalAmountWithTipStr = "$" + finalValue.format(totalWithTip);
            String billAmountInputStr = "$" + billAmountTotal;
            tipAmountTextBox.setText(tipAmountStr);
            totalAmountWithTip.setText(totalAmountWithTipStr);
            billAmountInput.setText(billAmountInputStr);
            totalAmount = finalValue.format(totalWithTip);
        }
    }

    public void onClickGo(View view){
        String billAmountTotal = billAmountInput.getText().toString();
        String people = noOfPeople.getText().toString();
        int returnedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        DecimalFormat formatter = new DecimalFormat("0.00");
        if(billAmountTotal.isEmpty()){
            //radioGroup.clearCheck();
            Toast.makeText(this, "enter amount", Toast.LENGTH_LONG).show();
        }
        else if(returnedRadioButtonId == -1){
            Toast.makeText(this, "select tip percentage", Toast.LENGTH_LONG).show();
        }
        else if(people.isEmpty()){
            Toast.makeText(this, "enter number of people", Toast.LENGTH_LONG).show();
        }
        else{
            //String billAmountTotalWithTip = totalAmountWithTip.getText().toString();
            Float billAmountTotalWithTipInFloat = Float.parseFloat(totalAmount);
            Float p = Float.parseFloat(people);
            Float amountPerPerson = billAmountTotalWithTipInFloat / p;
            String perPerson = "$" + formatter.format(amountPerPerson);
            billAmountPerPerson.setText(perPerson);
        }

    }

    public void onClickClear(View view){
        totalAmount = "";
        radioGroup.clearCheck();
        billAmountInput.setText("");
        tipAmountTextBox.setText("");
        totalAmountWithTip.setText("");
        noOfPeople.setText("");
        billAmountPerPerson.setText("");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tipAmountTextBox.setText(savedInstanceState.getString("tip_amount"));
        totalAmountWithTip.setText(savedInstanceState.getString("total_with_tip"));
        billAmountPerPerson.setText(savedInstanceState.getString("bill_amount_per_person"));
        totalAmount = savedInstanceState.getString("total_amount");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("tip_amount",tipAmountTextBox.getText().toString());
        outState.putString("total_with_tip",totalAmountWithTip.getText().toString());
        outState.putString("bill_amount_per_person",billAmountPerPerson.getText().toString());
        outState.putString("total_amount",totalAmount);
        super.onSaveInstanceState(outState);
    }
}