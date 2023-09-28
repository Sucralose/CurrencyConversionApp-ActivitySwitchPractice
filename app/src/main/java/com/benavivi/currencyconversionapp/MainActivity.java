package com.benavivi.currencyconversionapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	private Button convertButton;
	private EditText euroRateEditText, usdRateEditText, ilsAmount;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		variableSetup();
		convertButtonOnClickListener();
	}

	private void variableSetup () {
		convertButton = findViewById(R.id.convertButton);
		euroRateEditText = findViewById(R.id.editTextEuroConversionRate);
		usdRateEditText = findViewById(R.id.editTextUSDConversionRate);
		ilsAmount = findViewById(R.id.editTextILSAmount);
	}

	private void convertButtonOnClickListener () {
		convertButton.setOnClickListener(view -> moveToConvertScreen());
	}

	private boolean isInputValid () {
		if (ilsAmount.getText().toString().isEmpty())
			return false;
		if (usdRateEditText.getText().toString().isEmpty())
			return false;
		return !euroRateEditText.getText().toString().isEmpty();
	}

	private void moveToConvertScreen () {
		if (!isInputValid())
			return; //Exit because not all are full :c

		Intent intent = new Intent(MainActivity.this, convertedPanel.class);
		intent.putExtra("ILS_AMOUNT", Double.parseDouble(ilsAmount.getText().toString()));
		intent.putExtra("USD_RATE", Double.parseDouble(usdRateEditText.getText().toString()));
		intent.putExtra("EURO_RATE", Double.parseDouble(euroRateEditText.getText().toString()));
		startActivity(intent);

	}

}