package com.delta.Final;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class League extends Activity implements OnClickListener{
TextView tv,tvNextMatch;
ImageView iv1,iv2;
ListView lv;
AnimationDrawable frameAnimation;
Button b;
Handler mainHandler;
String[] teams={"MyTeam","Arsenal","Chelsea","Liverpool","ManCity","ManUtd"};
int[] imageId={0,R.drawable.arsenal,R.drawable.chelsea,R.drawable.liverpool,R.drawable.mancity,R.drawable.manutd};
int[][] level={	
				{0,1,0,1,1,0,1,0,2,2},
				{0,0,1,1,1,2,2,2,3,3},
				{0,1,4,2,2,3,3,3,1,4},
				{3,1,1,2,2,3,0,4,4,5},
				{5,3,2,0,3,1,4,4,5,5},
				{4,3,1,3,2,4,4,4,5,6}
			};
int t1,t2,i,j,id1,id2,balance;
Random r=new Random();
ManagerDatabase manager;
Team t[]=new Team[6];
PlayerDatabase info[]=new PlayerDatabase[6];
String[] standings=new String[7];
String team1,team2,T2,T1;
Object obj=new Object();
boolean newSeason=false;
boolean mPaused=false;
SharedPreferences prefs;
SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.league);
		prefs = getSharedPreferences("MyPrefs", 0);
		editor=prefs.edit();
		
		imageId[0]=prefs.getInt("customId", R.drawable.barca);
		manager=new ManagerDatabase(this);
		manager.open();
		manager.deletetAll();
		mainHandler=new Handler();
		b=(Button)findViewById(R.id.bSimulate);
		iv1=(ImageView)findViewById(R.id.ivLeagueTeam1);
		iv2=(ImageView)findViewById(R.id.ivLeagueTeam2);
		tv=(TextView)findViewById(R.id.tvOtherMatchScore);
		tvNextMatch=(TextView)findViewById(R.id.tvNextMatch);
		lv=(ListView)findViewById(R.id.lvStandings);
		b.setOnClickListener(this);
		b.setVisibility(View.INVISIBLE);
		for(int i=0;i<teams.length;i++){
			t[i]=new Team(teams[i],imageId[i]);			
		}
		for(int x=0;x<teams.length;x++){
			info[x]=new PlayerDatabase(League.this);
			info[x].DATABASE_TABLE=teams[x];
			info[x].open();
			t[x].ovr=info[x].getAvg();
			Log.d("Intial",t[x].ovr+" "+info[x].getAvg());
			t[x].setMax();
			info[x].close();
		}
		//t[0]=new Team(teams[0]);
		//tv.setText(t[0].gamesPlayed());
		Runnable runnable=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(i=0;i<teams.length;i++)
				{
					for(j=0;j<teams.length;j++)
					{
						synchronized (obj) {
							while(mPaused){
								try {
									Log.d("Waiting","wait()");
									obj.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						mainHandler.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								
								if(i!=j)
								{	
									Log.d("Thread", String.valueOf(i)+"&&"+String.valueOf(j));
									tvNextMatch.setText("\tMatch Result\n"+teams[i]+" vs "+teams[j]);
									id1=t[i].getImageId();
									id2=t[j].getImageId();
									iv1.setBackgroundResource(id1);
									iv2.setBackgroundResource(id2);
									t1=t[i].getMax(r.nextInt(10));
									t2=t[j].getMax(r.nextInt(10));
									tv.setText(t1+" : "+t2);
									//Log.d("Score",t[i].getMax()+" "+t[i].ovr+" "+t[j].getMax()+" "+t[j].ovr);
									if(t1>t2){
										t[i].incGamesWon();
										t[i].incPoints(3);
										t[j].incGamesLost();
									}else if(t1<t2){
										t[j].incPoints(3);
										t[j].incGamesWon();
										t[i].incGamesLost();
									}else{
										t[i].incPoints(1);
										t[j].incPoints(1);
										t[j].incGamesDrawn();
										t[i].incGamesDrawn();
									}
									
									t[i].setGoalsScored(t1);
									t[i].setGoalsAgainst(t2);
									t[j].setGoalsScored(t2);
									t[i].setGoalsAgainst(t1);
									t[i].incGamesPlayed();
									t[j].incGamesPlayed();
									
									if(i==0 || j==0){										
										tvNextMatch.setText("\tComing Up\n"+teams[i]+" vs "+teams[j]);
										tv.setText("");
										b.setVisibility(View.VISIBLE);
										mPaused=true;
										team1=teams[i];
										team2=teams[j];
										
									}
									else{
										b.setVisibility(View.INVISIBLE);
										//Display match result using tv
									}
										
									
									if(i==5&&j==4){
										Team temp;
										iv1.setBackgroundResource(R.drawable.tile);
										balance=t[0].win*3+t[0].draw;
										manager.insertData("Harish",balance , t[0].win, t[0].loss, t[0].draw);
										Toast.makeText(League.this, manager.getData(), Toast.LENGTH_LONG).show();
										balance=Integer.parseInt(manager.getBal("Harish"));
										
										manager.close();
										tv.setText("");
										tvNextMatch.setText("CHAMPIONS!!!");
										tvNextMatch.setTextSize(20);
										for(int p=0;p<teams.length-1;p++){
											for(int x=0;x<teams.length-1;x++){
												if(t[x].pts<t[x+1].pts){
													temp = t[x];
													t[x]=t[x+1];
													t[x+1]=temp;													
											}											
										  }
										}
										for(int k=0;k<teams.length;k++)
											t[k].pos=k+1;
										
										standings[0]="TEAM\t\t\t\t\tP  W  L  D  PTS";
										
										for(int s=1;s<=teams.length;s++)
											standings[s]=t[s-1].getData();
										b.setVisibility(View.VISIBLE);
										b.setText("Start Next Season");
										newSeason=true;
										iv1.setBackgroundResource(R.drawable.sport_soccer);
										iv2.setBackgroundResource(t[0].getImageId());
										// Get the background, which has been compiled to an AnimationDrawable object.
										frameAnimation = (AnimationDrawable) iv1.getBackground();
										 // Start the animation (looped playback by default).
										 frameAnimation.start();
										 
										ArrayAdapter<String> adp=new ArrayAdapter<String>(League.this, R.layout.single_standings, R.id.tvSingleStandings, standings);
										//TextView tvFirst=(TextView)findViewById(R.id.tvSingleStandings);
										//tvFirst.setBackgroundResource(R.drawable.tile_selected);
										lv.setAdapter(adp);
										//Log.d("kmk",lv.getItemIdAtPosition(0)+"");
										
									}
								}								
				
							}
						});
						delay();
					}
				}
			}			
		};
		
		Thread th = new Thread(runnable);
		th.start();		
			
		
		//String str="";
		//Toast.makeText(this,t[3].getData() , Toast.LENGTH_LONG).show();
		
		
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mPaused=true;
		Log.d("onPause", "Paused");
		
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mPaused=false;
		synchronized (obj) {
			
			obj.notify();
		}
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
	
	private class Team{
		private String name;
		public float ovr;
		private int gp,gs,ga,win,loss,draw;
		int[] maxRand=new int[10];
		int ImageId,pos;
		public int pts;
		public Team(String team, int imageId){
			gp=gs=ga=win=loss=draw=0;		
			ImageId=imageId;
			name=team;
		}
		
		public void incGamesPlayed(){
			gp++;
		}
		
		public void incGamesWon(){
			win++;
		}
		
		public void incGamesLost(){
			loss++;
		}
		public void incGamesDrawn(){
			draw++;
		}
		public void incPoints(int p){
			pts+=p;			
		}
		public void setGoalsScored(int goalScored){
			gs+=goalScored;
		}
		public void setGoalsAgainst(int goalAgainst){
			ga+=goalAgainst;
		}
		public int getImageId(){
			return ImageId;
		}
		public void setMax(){
			if(ovr<80)
				maxRand=level[0];
			else if(ovr>=80 && ovr<82)
				maxRand=level[1];
			else if(ovr>=82 && ovr<85)
				maxRand=level[2];
			else if(ovr>=85 && ovr<87)
				maxRand=level[3];
			else if(ovr>=87 && ovr<89)
				maxRand=level[4];
			else
				maxRand=level[5];
		}
		public int getMax(int i){
			return maxRand[i];
		}
		
		public String getData(){
			if(name.equals("Liverpool"))
				return name+"\t\t\t"+String.valueOf(gp)+"  "+String.valueOf(win)+"  "+String.valueOf(loss)+"  "+String.valueOf(draw)+"  "+String.valueOf(pts);
			return name+"\t\t\t\t"+String.valueOf(gp)+"  "+String.valueOf(win)+"  "+String.valueOf(loss)+"  "+String.valueOf(draw)+"  "+String.valueOf(pts);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		if(!newSeason){
			intent.setClass(League.this,TeamList.class);
			intent.putExtra("goal1", t1);
			intent.putExtra("goal2", t2);
			intent.putExtra("id1", id1);
			intent.putExtra("id2", id2);
			intent.putExtra("team1", team1);
			intent.putExtra("team2", team2);
			startActivity(intent);
		}
		else{
			//intent.setClass(League.this, ChoosePlayers.class);
			//intent.putExtra("season", "end");
			//intent.putExtra("balance", balance);
			intent.setClass(League.this, Knockout.class);
			intent.putExtra("team1", t[0].name);
			intent.putExtra("team2", t[1].name);
			intent.putExtra("id1", t[0].ImageId);
			intent.putExtra("id2", t[1].ImageId);
			startActivity(intent);
			finish();
		}
		
		
		
	}

}
