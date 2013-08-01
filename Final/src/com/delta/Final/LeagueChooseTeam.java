package com.delta.Final;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class LeagueChooseTeam extends Activity implements OnClickListener {

	ImageView im1, im2, im3, im4, im5, im6, im7, im8, im9, im10;
	TextView tv;
	Button b;
	//String[] teams = { "BuildMyTeam", "Arsenal", "Chelsea", "Liverpool","ManCity", "ManUtd" };
	//String myTeam;
	Bundle bundle;
	
	ScrollView sv;
	SharedPreferences prefs;
	SharedPreferences.Editor edit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.league_choose_team_logo);
		
		bundle=getIntent().getExtras();
		prefs = getSharedPreferences("MyPrefs", 0);
		edit=prefs.edit();
		tv=(TextView)findViewById(R.id.tvChooseLogo);
		sv=(ScrollView)findViewById(R.id.scrollView1);
		im1 = (ImageView) findViewById(R.id.ivCustom1);
		im2 = (ImageView) findViewById(R.id.ivCustom2);
		im3 = (ImageView) findViewById(R.id.ivCustom3);
		im4 = (ImageView) findViewById(R.id.ivCustom4);
		im5 = (ImageView) findViewById(R.id.ivCustom5);
		im6 = (ImageView) findViewById(R.id.ivCustom6);
		im7 = (ImageView) findViewById(R.id.ivCustom7);
		im8 = (ImageView) findViewById(R.id.ivCustom8);
		im9 = (ImageView) findViewById(R.id.ivCustom9);
		im10 = (ImageView) findViewById(R.id.ivCustom10);
		b = (Button) findViewById(R.id.bStart);
		
		im1.setOnClickListener(this);
		im2.setOnClickListener(this);
		im3.setOnClickListener(this);
		im4.setOnClickListener(this);
		im5.setOnClickListener(this);
		im6.setOnClickListener(this);
		im7.setOnClickListener(this);
		im8.setOnClickListener(this);
		im9.setOnClickListener(this);
		im10.setOnClickListener(this);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		b.setVisibility(View.INVISIBLE);

	}

	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		switch (arg0.getId()) {
		case R.id.ivCustom1:
			edit.putInt("customId", R.drawable.custom1);
			break;
		case R.id.ivCustom2:
			edit.putInt("customId", R.drawable.custom2);
			break;
		case R.id.ivCustom3:
			edit.putInt("customId", R.drawable.custom3);
			break;
		case R.id.ivCustom4:
			edit.putInt("customId", R.drawable.custom4);
			break;
		case R.id.ivCustom5:
			edit.putInt("customId", R.drawable.custom5);
			break;
		case R.id.ivCustom6:
			edit.putInt("customId", R.drawable.custom6);
			break;
		case R.id.ivCustom7:
			edit.putInt("customId", R.drawable.custom7);
			break;
		case R.id.ivCustom8:
			edit.putInt("customId", R.drawable.custom8);
			break;
		case R.id.ivCustom9:
			edit.putInt("customId", R.drawable.custom9);
			break;
		case R.id.ivCustom10:
			edit.putInt("customId", R.drawable.custom10);
			break;
		}
		
		edit.commit();
		Intent i=new Intent(LeagueChooseTeam.this,ChoosePlayers.class);
		i.putExtra("season", bundle.getString("season"));			
		startActivity(i);
		finish();
		
		
	}
}
