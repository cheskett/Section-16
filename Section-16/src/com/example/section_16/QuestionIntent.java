package com.example.section_16;

import java.util.Random;

import com.example.section_16.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionIntent extends Activity {

	SQLiteDatabase mydb;
	Question q_array[];
	Question q;

	int index;
	int ans;
	private static String DBNAME = "QUESTIONS.db";    // THIS IS THE SQLITE DATABASE FILE NAME.
	private static String TABLE = "MY_TABLE";       // THIS IS THE TABLE NAME
	private static String SCORETABLE = "SCORE_TABLE";

	public QuestionsDataSource datasource;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_intent);

		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		ActionBar actionBar = getActionBar();
		actionBar.hide();

		datasource = new QuestionsDataSource(this);
		datasource.open();
		q = datasource.retrieveQuestion();


		TextView tv = (TextView)findViewById(R.id.questionBox);
		//tv.setText("NAME " + s.name + "\n\n SCORE: " + s.score );
		tv.setText(q.question + "\nA: " + q.answer_A + "\nB: " + q.answer_B+ "\nC: " + q.answer_C+ "\nD: " + q.answer_D);
		tv.setMovementMethod(new ScrollingMovementMethod());
	}
	public void insertHighScore(Score s){
		try{
			mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
			mydb.execSQL("INSERT INTO " + SCORETABLE + "(NAME, SCORE)" +
					" VALUES('"+s.name+"','"+s.score+"')");
			mydb.close();
		}catch(Exception e){
			// Toast.makeText(getApplicationContext(), "Error in inserting into table", Toast.LENGTH_LONG);
		}
	}

	public void onBackPressed() {

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.continue_game, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	public void confirmAnswer(View view){
		RadioGroup rg= (RadioGroup) findViewById(R.id.radiogroup);
		int id = rg.getCheckedRadioButtonId();
		View radioButton = rg.findViewById(id);
		index = rg.indexOfChild(radioButton);
		if( index == -1){
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Warning!");
			alertDialog.setMessage("Choose an answer...");
			alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// here you can add functions
				}
			});
			alertDialog.show();
			return;
		}
		ans=4;

		if(q.correct.equals("A")){
			ans = 0;
		}else if(q.correct.equals("B")){
			ans = 1;
		}else if(q.correct.equals("C")){
			ans = 2;
		}else if(q.correct.equals("D")){
			ans = 3;
		}
		
		String resp=null;
		if(ans == index){
			resp = "Correct!!!";
		}else{
			resp = "Try again...";
		}
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Section-16");
		alertDialog.setMessage(resp);
		alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
				Intent resultIntent = new Intent();
				if(ans == index)
					resultIntent.putExtra("1", "1");
				else
					resultIntent.putExtra("0", "0");
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
		alertDialog.show();
	}
	
	public void displayHint(View view){
		Toast.makeText(getApplicationContext(), q.hint, Toast.LENGTH_SHORT).show();
	}
}
