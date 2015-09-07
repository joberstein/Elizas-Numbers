package com.example.elizasnumbers;

import com.example.elizasnumbers.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayWinnerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int num = MainActivity.mysteryNumber;
		int guesses = MainActivity.GUESSES - MainActivity.remGuesses;
		Intent intent = new Intent();
		intent.putExtra("Completed", "true");

		// Set the text view as the activity layout.
		setContentView(R.layout.activity_display_winner);
		
		// Create the text view.
		TextView textView = (TextView) findViewById(R.id.winner_text);
		textView.setText(Html.fromHtml("Awesome job, you guessed the correct " +
				"number in <b><font color='#E8BD5F'>" + guesses + "</font>" +
				"</b> guesses! <br><br>Eliza's number was <b><font " +
				"color='#E8BD5F'>" + num + "</font></b>.<br><br> " 
				+ "Keep playing to try to beat your best number of " +
				"guesses!"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_winner, menu);
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
	
	public void restartGame(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
