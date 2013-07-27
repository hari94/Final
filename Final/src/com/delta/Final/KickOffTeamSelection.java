package com.delta.Final;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

public class KickOffTeamSelection extends Activity implements OnClickListener, OnItemSelectedListener {
	Button bGo;
	Spinner sp1,sp2;
	ImageView im1,im2;

	String[] teams={"Arsenal","Chelsea","Liverpool","ManCity","ManUtd"};
	String team1,team2;
	int id1,id2;
	
	
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.kickoff_team_selection);
			im1=(ImageView)findViewById(R.id.ivTeam1);
			im2=(ImageView)findViewById(R.id.ivTeam2);
			sp1=(Spinner)findViewById(R.id.spinnerTeam1);
			sp2=(Spinner)findViewById(R.id.spinnerTeam2);
			bGo=(Button)findViewById(R.id.bGo);
			bGo.setOnClickListener(this);
			
			
			ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teams);
			sp1.setAdapter(adp);
			sp2.setAdapter(adp);
			sp1.setOnItemSelectedListener(this);
			sp2.setOnItemSelectedListener(this);

}



		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			team1=(String) sp1.getSelectedItem();
			team2=(String) sp2.getSelectedItem();
			
			switch(arg0.getId()){
			case R.id.spinnerTeam1:
				if(team1.equals("Arsenal"))
					{
					im1.setImageResource(R.drawable.arsenal);
					im1.setTag(R.drawable.arsenal);
					}
				else if(team1.equals("Chelsea"))
					{
					im1.setImageResource(R.drawable.chelsea);
					im1.setTag(R.drawable.chelsea);
					}
				else if(team1.equals("Liverpool"))
					{
					im1.setImageResource(R.drawable.liverpool);
					im1.setTag(R.drawable.liverpool);
					}
				else if(team1.equals("ManCity"))
					{
					im1.setImageResource(R.drawable.mancity);
					im1.setTag(R.drawable.mancity);
					}
				else if(team1.equals("ManUtd"))
					{
					im1.setImageResource(R.drawable.manutd);
					im1.setTag(R.drawable.manutd);
					}
				break;
			
			case R.id.spinnerTeam2:
				if(team2.equals("Arsenal"))
					{
					im2.setImageResource(R.drawable.arsenal);
					im2.setTag(R.drawable.arsenal);
					}
				else if(team2.equals("Chelsea"))
					{
					im2.setImageResource(R.drawable.chelsea);
					im2.setTag(R.drawable.chelsea);
					}
				else if(team2.equals("Liverpool"))
					{
					im2.setImageResource(R.drawable.liverpool);
					im2.setTag(R.drawable.liverpool);
					}
				else if(team2.equals("ManCity"))
					{
					im2.setImageResource(R.drawable.mancity);
					im2.setTag(R.drawable.mancity);
					}
				else if(team2.equals("ManUtd"))
					{
					im2.setImageResource(R.drawable.manutd);
					im2.setTag(R.drawable.manutd);
					}
				break;			
			}
			//Toast.makeText(this,team1+" "+team2, Toast.LENGTH_SHORT).show();
			
		}



		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(KickOffTeamSelection.this,TeamList.class);
			i.putExtra("team1", team1);
			i.putExtra("team2", team2);
			i.putExtra("id1",(Integer)im1.getTag());
			i.putExtra("id2",(Integer)im2.getTag());
			i.putExtra("goal1", 0);
			i.putExtra("goal2", 0);
			startActivity(i);
			finish();
		}
		
}