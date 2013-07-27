package com.delta.Final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {
	Button bKickOff,bBuild,bExit,bStats;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_menu);
		bKickOff=(Button)findViewById(R.id.bKickOff);
		bBuild=(Button)findViewById(R.id.bLeague);
		bStats=(Button)findViewById(R.id.bStats);
		bExit=(Button)findViewById(R.id.bExit);
		bKickOff.setOnClickListener(this);
		bBuild.setOnClickListener(this);;
		bStats.setOnClickListener(this);;
		bExit.setOnClickListener(this);;
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i=new Intent();
		//i.setClass(Main.this, TeamList.class);
		switch(v.getId()){
		case R.id.bKickOff:
			i.setClass(MainMenu.this, KickOffTeamSelection.class);
			startActivity(i);
			break;
		case R.id.bStats:
			
			break;
		case R.id.bLeague:
			i.setClass(MainMenu.this, LeagueChooseTeam.class);
			i.putExtra("season", "new");
			startActivity(i);
			break;
		case R.id.bExit:
			finish();
			break;
		}
		
		//i.putExtra("team1", team1);
		//i.putExtra("team2", team2);
		//i.putExtra("goal1", 1);
		//i.putExtra("goal2", 2);
		

	}
	
}
