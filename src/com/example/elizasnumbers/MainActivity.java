package com.example.elizasnumbers;
import java.util.ArrayList;
import java.util.Random;

import com.example.elizasnumbers.R;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

// This class represents the Main Activity, which deals with guessing Eliza's
// number.
public class MainActivity extends ActionBarActivity {
	public final static int GUESSES = 12;
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	private static final Random rand = new Random();
	public static int mysteryNumber = rand.nextInt(1000) + 1;
	public static int remGuesses = GUESSES;
	private static String guessList1 = "";
	private static String guessList2 = "";
	private static ArrayList<Integer> guesses = new ArrayList<Integer>(GUESSES);
	private static EditText editText;
	private static TextView pastGuesses1;
	private static TextView pastGuesses2;
	private static TextView remaining;
	private static TextView bestGuessesText;
	private static String guessString;
	private static boolean finishedGame = false;
	
	SharedPreferences sharedPref;
	private int bestGuesses;
	private String bestGuessesString;

	
	/**
	 * Creates this activity from a given saved instance state if it exists.
	 * @param savedInstanceState A Bundle of the last saved instance.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		bestGuesses = sharedPref.getInt("Best Guesses", GUESSES + 1);
		bestGuessesString = sharedPref.getString("Best Guesses String", "---");
		
		setContentView(R.layout.activity_main);

		pastGuesses1 = (TextView) findViewById(R.id.view_guesses1);
		pastGuesses2 = (TextView) findViewById(R.id.view_guesses2);
		remaining = (TextView) findViewById(R.id.rem_guesses);
		bestGuessesText = (TextView) findViewById(R.id.best_guesses);
		
		pastGuesses1.setText(guessList1);
		pastGuesses2.setText(guessList2);
		remaining.setText(Integer.toString(remGuesses));
		bestGuessesText.setText(Html.fromHtml("Best Guesses: <b><font " +
				"color='#E8BD5F'>" + bestGuessesString + "</font></b>"));
		
		if (savedInstanceState != null) {
			String remGuessesSaved = savedInstanceState.getString("Remaining Guesses");
			String pastGuesses1Saved = savedInstanceState.getString("Past Guesses 1");
			String pastGuesses2Saved = savedInstanceState.getString("Past Guesses 2");
			Integer bestGuessesSaved = savedInstanceState.getInt("Best Guesses");
			String bestGuessesStringSaved = savedInstanceState.getString("Best Guesses String");
			  
			pastGuesses1.setText(pastGuesses1Saved);
			pastGuesses2.setText(pastGuesses2Saved);
			remaining.setText(remGuessesSaved);
			bestGuessesText.setText("Best Guesses: " + bestGuessesStringSaved);
			bestGuesses = bestGuessesSaved;
			bestGuessesString = bestGuessesStringSaved;
		}
	}

	/**
	 * Restarts this activity if a game has been finished.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		if (finishedGame) {
			this.restartActivity();
		}
	}
	
	/**
	 * Saves parameters to a Bundle of a saved instance state.
	 * @param savedInstanceState The given Bundle of the saved instance.
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("Remaining Guesses", Integer.toString(remGuesses));
		savedInstanceState.putString("Past Guesses 1", guessList1);
		savedInstanceState.putString("Past Guesses 2", guessList2);
		savedInstanceState.putInt("Best Guesses", bestGuesses);
		savedInstanceState.putString("Best Guesses String", bestGuessesString);
		// Save state to the savedInstanceState
		super.onSaveInstanceState(savedInstanceState);
	}

	/**
	 * Retrieves parameters from a Bundle of a saved instance state.
	 * @param savedInstanceState The given Bundle of the saved instance.
	 */
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		  // Restore state from savedInstanceState
		  super.onRestoreInstanceState(savedInstanceState);
		  String remGuessesSaved = savedInstanceState.getString("Remaining Guesses");
		  String pastGuesses1Saved = savedInstanceState.getString("Past Guesses 1");
		  String pastGuesses2Saved = savedInstanceState.getString("Past Guesses 2");
		  Integer bestGuessesSaved = savedInstanceState.getInt("Best Guesses");
		  String bestGuessesStringSaved = savedInstanceState.getString("Best Guesses String");
		  
		  pastGuesses1.setText(pastGuesses1Saved);
		  pastGuesses2.setText(pastGuesses2Saved);
		  remaining.setText(remGuessesSaved);
		  bestGuessesText.setText("Best Guesses: " + bestGuessesStringSaved);
		  bestGuesses = bestGuessesSaved;
		  bestGuessesString = bestGuessesStringSaved;
	}
	
	/**
	 * After each guess, retains the guess list and remaining guesses.
	 */
	@Override
	protected void onResume() {
		pastGuesses1.setText(guessList1);
		pastGuesses2.setText(guessList2);
		remaining.setText(Integer.toString(remGuesses));
	    super.onResume();
	}

	/*
	====================================================
	OPTIONS MENU UNUSED FOR NOW
	====================================================
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	*/
	
	/**
	 * Returns a given number's position relative to Eliza's mystery number.
	 * @param guess The integer guessed by the user.
	 * @return String The position relative to Eliza's mystery number. Can be
	 * one of "high", "equal", or "low".
	 */
	private String decide(int guess) {
		if (guess > mysteryNumber) {
			return "high";
		}
		else if (guess == mysteryNumber) {
			return "equal";
		}
		else {
			return "low";
		}
	}
	
	private void addToGuesses(int guess) {
		if (remGuesses < Math.ceil(GUESSES / 2.0)) {
			guessList2 += guess + " is too " + guessString + "\n";
		}
		else {
			guessList1 += guess + " is too " + guessString + "\n";
		}
	}
	
	/**
	 * Resets all of the parameters of this activity that can change after a
	 * new game is started.
	 */
	private void reset() {
		finishedGame = false;
		mysteryNumber = rand.nextInt(1000) + 1;
		guessList1 = "";
		guessList2 = "";
		remGuesses = GUESSES;
		guesses = new ArrayList<Integer>(GUESSES);
	}
	
	/**
	 * Restarts this activity and it's parameters.
	 */
	private void restartActivity() {
		this.reset();
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
	
	/**
	 * Sets the new record for smallest number of guesses if the user has
	 * achieved a new record.
	 */
	private void setBest() {
		int currentGuesses = GUESSES - remGuesses;
		if (currentGuesses < bestGuesses) {
			bestGuesses = currentGuesses;
			bestGuessesString = Integer.toString(bestGuesses);

			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt("Best Guesses", bestGuesses);
			editor.putString("Best Guesses String", bestGuessesString);
			editor.commit();
		}
	}
	
	/*
	===========================================
	  ONCLICK METHODS
	=========================================== */
	
	/**
	 * Resets Eliza's mystery number.  OnClick method from the "Reset" button.
	 * @param view A given view argument, mandated by the onClick function.
	 */
	public void resetMysteryNumber(View view) {
		this.reset();
		this.onResume();
	}
	
	/**
	 * Guesses the input number.  OnClick method from the "Guess" button.
	 * @param view A given view argument, mandated by the onClick function.
	 */
	public void sendMessage(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		Intent loseIntent = new Intent(this, DisplayLoserActivity.class);
		Intent winIntent = new Intent(this, DisplayWinnerActivity.class);
		editText = (EditText) findViewById(R.id.edit_message);
		guessString = editText.getText().toString();
		try {
			int guessInt = Integer.parseInt(guessString);
			if (guessInt < 1 || guessInt > 1000) {
				guessString = "Please input a number between 1 and 1000.";
			}
			else if (guesses.contains(guessInt)) {
				guessString = "You've already guessed this number!";
			}
			else if (remGuesses == 1 && guessInt != mysteryNumber) {
				finishedGame = true;
				startActivity(loseIntent);
			}
			else {
				remGuesses--;
				guesses.add(guessInt);
				guessString = decide(guessInt);
				if (guessString == "equal") {
					this.setBest();
					finishedGame = true;
					startActivity(winIntent);
					finish();
				}
				else {
					this.addToGuesses(guessInt);
				}
			}
		}
		catch (Exception e) {
			guessString = "Please input a number.";
		}
		// Hide the soft keyboard after each guess.
		editText.setText("");
		InputMethodManager imm = (InputMethodManager)getSystemService(
		      Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		startActivity(intent);
	}
}
