package com.benavivi.currencyconversionapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Locale;

public class convertedPanel extends AppCompatActivity {

	private TextView amountILS, amountUSD, amountEuro;
	private TextToSpeech textToSpeech;
	private Button textToSpeechButton, returnButton;
	private double euroConversionRate, usdConversionRate, ilsInputValue;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_converted_pannel);

		setupActivityValues();
		setupBundleValues();

		initiateTextToSpeech();

		updateUIComponents();
		callUSEDConversionAlert();

		returnButtonOnClickListener();
		textToSpeechButtonOnClickListener();

	}

	public void initiateTextToSpeech () {
		textToSpeech = new TextToSpeech(getApplicationContext(), i -> {
			if (i == TextToSpeech.SUCCESS) {
				textToSpeech.setLanguage(Locale.ENGLISH);
			}
		});
	}

	private void setupActivityValues () {
		amountUSD = findViewById(R.id.showUSDValue);
		amountILS = findViewById(R.id.showILSAmount);
		amountEuro = findViewById(R.id.showEuroValue);
		textToSpeechButton = findViewById(R.id.readButton);
		returnButton = findViewById(R.id.returnButton);
	}

	private void setupBundleValues () {
		Intent intent = getIntent();
		if (intent == null || intent.getExtras() == null)
			return; //Exit func
		Bundle values = intent.getExtras();

		euroConversionRate = values.getDouble("EURO_RATE");
		usdConversionRate = values.getDouble("USD_RATE");
		ilsInputValue = values.getDouble("ILS_AMOUNT");

	}

	public void returnButtonOnClickListener () {

		returnButton.setOnClickListener(view -> {
			Intent intent = new Intent(convertedPanel.this, MainActivity.class);
			startActivity(intent);
		});

	}

	public void textToSpeechButtonOnClickListener () {
		textToSpeechButton.setOnClickListener(view -> textToSpeech.speak(ilsInputValue + "Israeli shekels are worth, " + ilsInputValue * euroConversionRate + "Euros, or, " + ilsInputValue * usdConversionRate + "United Stated Dollars."
			, TextToSpeech.QUEUE_FLUSH, null, ""));
	}

	private void updateUIComponents () {
		DecimalFormat numberFormatter = new DecimalFormat();

		String temp = numberFormatter.format(ilsInputValue) + "₪ is worth: ";
		amountILS.setText(temp);

		temp = numberFormatter.format(ilsInputValue * euroConversionRate) + '€';
		amountEuro.setText(temp);

		temp = numberFormatter.format(ilsInputValue * usdConversionRate) + '$';
		amountUSD.setText(temp);
	}

	public void callUSEDConversionAlert () {
		new AlertDialog.Builder(convertedPanel.this)
			.setTitle("הודעת מערכת")
			.setMessage(usdConversionRate > 1 ? "שים לב, הסכום בדולרים גדול יותר" : "שים לב, הסכום בדולרים קטן יותר")
			.setPositiveButton("אשר", null)
			.show();
	}

}