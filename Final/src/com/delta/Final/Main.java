package com.delta.Final;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main extends Activity implements OnClickListener  {
private static final String TAG = "Priority";
TextView tvLoading;
ImageView ivIntro;
ProgressBar pb;
boolean endIntro=false;
AnimationDrawable frameAnimation;
String[] teams={"Arsenal","Chelsea","Liverpool","ManCity","ManUtd","Barcelona","RealMadrid","BMunich","BDortmund","PSG","Juventus"};
String[][] pos={
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"},
				{"G","D","D","D","D","M","M","M","A","A","A"}
			   };
String[][] name={
				{"Sczcezny","Gibbs","Mertesacker","Vermaelen","Sagna","Arteta","Wilshere","Cazorla","Walcott","Giroud","Podolski"},
				{"Cech","Luiz","Terry","Ivanovic","Cole","Hazard","Mata","Lampard","Ramires","Ba","Torres"},
				{"Reina","Enrique","Agger","Skrtel","Johnson","Henderson","Coutinho","Gerard","Downing","Sturridge","Suarez"},
				{"Hart","Lescott","Richards","Kompany","Kolarov","Navas","Yaya","Nasri","Silva","Dzeko","Aguero"},
				{"De Gea","Rafael","Smalling","Vidic","Evra","Valencia","Fletcher","Kagawa","Young","Rooney","Van Persie"},
				{"Pinto","Alves","Pique","Puyol","Alba","Busquets","Xavi","Iniesta","Pedro","Messi","Neymar"},
				{"Casillas","Pepe","Ramos","Marcelo","Coentrao","Alonso","Ozil","Isco","Di Maria","Benzema","Ronaldo"},
				{"Neuer","Boateng","Dante","Van Buyten","Lahm","Schweinsteiger","Goetze","Muller","Ribery","Madzukic","Robben"},
				{"Weindenfeller","Schmelzer","Hummels","Subotic","Hornschuh","Bender","Reus","Mkhitaryan","Blaszczykowski","Lewandowski","Kirch"},
				{"Douchez","Digne","Silva","Sakho","Marquinhos","Menez","Thiago","Veratti","Pastore","Cavani","Ibrahimovic"},
				{"Buffon","De Ceglie","Chiellini","Bonucci","Barzagli","Pogba","Marchisio","Pirlo","Pepe","Tevez","Vucinic"}
				};

int[][] ovr={
			{85,84,85,86,84,87,94,87,86,90,90},
			{87,83,81,85,84,83,83,87,86,90,90},
			{87,83,81,85,84,83,83,87,86,90,90},
			{87,83,81,85,84,83,83,87,86,90,90},
			{87,83,81,85,84,83,83,87,86,90,90},
			{85,84,85,86,84,87,94,87,86,90,90},
			{87,83,81,85,84,83,83,87,86,90,90},
			{87,83,81,85,84,83,83,87,86,90,90},
			{87,83,81,85,84,83,83,87,86,90,90},
			{87,83,81,85,84,83,83,87,86,90,90},
			{87,83,81,85,84,83,83,87,86,90,90}
			};

//String team1,team2;
int i;
Handler mainHandler;
SharedPreferences prefs;

//Dialog d;
PlayerDatabase t[]=new PlayerDatabase[11] ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences("MyPrefs", 0);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putInt("customId", 0);
		editor.commit();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		ivIntro=(ImageView)findViewById(R.id.ivIntro);
		tvLoading=(TextView)findViewById(R.id.tvIntroLoading);
		pb=(ProgressBar)findViewById(R.id.progressBarMain);
		pb.setProgress(0);
		/*im2=(ImageView)findViewById(R.id.ivTeam2);
		sp1=(Spinner)findViewById(R.id.spinnerTeam1);
		sp2=(Spinner)findViewById(R.id.spinnerTeam2);*/
		// Get the background, which has been compiled to an AnimationDrawable object.
		
		//final TextView tv = new TextView(this);
		//d=new Dialog(this);
		/*ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teams);
		sp1.setAdapter(adp);
		sp2.setAdapter(adp);
		sp1.setOnItemSelectedListener(this);
		sp2.setOnItemSelectedListener(this);
		//load();	
		*/
		tvLoading.setOnClickListener(this);
		mainHandler=new Handler();
		Runnable runnable=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(i=0;i<teams.length;i++){
					
					t[i]=new PlayerDatabase(Main.this);
					t[i].DATABASE_TABLE=teams[i];
					t[i].open();
					t[i].deleteAll();
					for(int j=0;j<11;j++){
						Log.d("Loop", pos[i][j]+name[i][j]+ovr[i][j]);
						Log.d("Loop", i+" "+j);
						t[i].insertData(pos[i][j], name[i][j], ovr[i][j]);
					}						
					t[i].close();
					
					mainHandler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							frameAnimation = (AnimationDrawable) ivIntro.getBackground();
							 // Start the animation (looped playback by default).
							frameAnimation.start();
										
							pb.setProgress(i*25);
							tvLoading.setText("LOADING DATABASES");
							if(i==10)
								{
								pb.setVisibility(View.INVISIBLE);
								tvLoading.setText("TOUCH HERE TO CONTINUE");
								endIntro=true;
								}
							
								
						}
					});
					delay();
				}
			}

			
		};
		Thread th=new Thread(runnable);
		th.start();
		int tid=android.os.Process.myTid();
		Log.d(TAG,"priority before change = " + android.os.Process.getThreadPriority(tid));
        Log.d(TAG,"priority before change = "+Thread.currentThread().getPriority());
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_DISPLAY);
        Log.d(TAG,"priority after change = " + android.os.Process.getThreadPriority(tid));
        Log.d(TAG,"priority after change = " + Thread.currentThread().getPriority());
		//Toast.makeText(this,(CharSequence) sp1.getSelectedItem(), Toast.LENGTH_SHORT).show();
	}

	/*private void load() {
		// TODO Auto-generated method stub
		PlayerDatabase t[]=new PlayerDatabase[5] ;
		for(int i=0;i<teams.length;i++)
		{
			t[i]=new PlayerDatabase(this);
			t[i].DATABASE_TABLE=teams[i];
			t[i].open();
			t[i].deleteAll();
			for(int j=0;j<11;j++){
				//Log.d("Loop", pos[i][j]+name[i][j]+ovr[i][j]);
				Log.d("Loop", i+" "+j);
				//t[i].insertData(pos[i][j], name[i][j], ovr[i][j]);
			}						
			t[i].close();			
		}
		PlayerDatabase myteam=new PlayerDatabase(Main.this);
		myteam.open();
		myteam.close();			
	}
*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void delay() {
		// TODO Auto-generated method stub
	try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
	/*@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i=new Intent();
		//i.setClass(Main.this, TeamList.class);
		switch(v.getId()){
		case R.id.bKickOff:
			i.setClass(Main.this, KickOffTeamSelection.class);
			break;
		case R.id.bStats:
			break;
		case R.id.bLeague:
			i.setClass(Main.this, ChoosePlayers.class);
			i.putExtra("season", "new");
			break;
		case R.id.bExit:
			finish();
			break;
		}
		
		//i.putExtra("team1", team1);
		//i.putExtra("team2", team2);
		//i.putExtra("goal1", 1);
		//i.putExtra("goal2", 2);
		startActivity(i);
	}*/

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i=new Intent();
		i.setClass(Main.this, MainMenu.class);
		if(endIntro)
		{
			startActivity(i);
			finish();
		}
		
	}

	/*@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		 team1=(String) sp1.getSelectedItem();
		 team2=(String) sp2.getSelectedItem();
		
		switch(arg0.getId()){
		case R.id.spinnerTeam1:
			if(team1.equals("Arsenal"))
				im1.setImageResource(R.drawable.arsenal);
			else if(team1.equals("Chelsea"))
				im1.setImageResource(R.drawable.chelsea);
			else if(team1.equals("Liverpool"))
				im1.setImageResource(R.drawable.liverpool);
			else if(team1.equals("ManCity"))
				im1.setImageResource(R.drawable.mancity);
			else if(team1.equals("ManUtd"))
				im1.setImageResource(R.drawable.manutd);		
			break;
		
		case R.id.spinnerTeam2:
			if(team2.equals("Arsenal"))
				im2.setImageResource(R.drawable.arsenal);
			else if(team2.equals("Chelsea"))
				im2.setImageResource(R.drawable.chelsea);
			else if(team2.equals("Liverpool"))
				im2.setImageResource(R.drawable.liverpool);
			else if(team2.equals("ManCity"))
				im2.setImageResource(R.drawable.mancity);
			else if(team2.equals("ManUtd"))
				im2.setImageResource(R.drawable.manutd);
			break;			
		}
		Toast.makeText(this,team1+" "+team2, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}*/
}
