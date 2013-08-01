package com.delta.Final;

import java.util.Random;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Knockout extends Activity implements OnClickListener{
ImageView imLine1,imLine2,imLine3,imLine4,imLine5,imLine6,imLine7,imLine8;
TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15;
Button b;
boolean cup=true,paused=false;
Bundle bundle;
Handler mainHandler;
Thread th;
Team t[]=new Team[8];
int i,round=0;
Object obj;
Team semi1,semi2,semi3,semi4,f1,f2,winners;
PlayerDatabase info[]=new PlayerDatabase[8];
String[] teams={"team1","team2","BMunich","BDortmund","Juventus","PSG","Barcelona","RealMadrid"};
int[] imageId={0,0,R.drawable.bmunich,R.drawable.bvb,R.drawable.juventus,R.drawable.psg,R.drawable.barca,R.drawable.rm};
int[][] level={	
				{0,1,0,1,1,0,1,0,2,2},
				{0,0,1,1,1,2,2,2,3,3},
				{0,1,4,2,2,3,3,3,1,4},
				{3,1,1,2,2,3,0,4,4,5},
				{5,3,2,0,3,1,4,4,5,5},
				{4,3,1,3,2,4,4,4,5,6}
				
			};
Random r;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.knockout_map);
		mainHandler=new Handler();
		obj=new Object();
		bundle=getIntent().getExtras();
		tv1=(TextView)findViewById(R.id.tvKnockoutTeam1);
		tv2=(TextView)findViewById(R.id.tvKnockoutTeam2);
		tv3=(TextView)findViewById(R.id.tvKnockoutTeam3);
		tv4=(TextView)findViewById(R.id.tvKnockoutTeam4);
		tv5=(TextView)findViewById(R.id.tvKnockoutTeam5);
		tv6=(TextView)findViewById(R.id.tvKnockoutTeam6);
		tv7=(TextView)findViewById(R.id.tvKnockoutTeam7);
		tv8=(TextView)findViewById(R.id.tvKnockoutTeam8);
		tv9=(TextView)findViewById(R.id.tvKnockoutTeam9);
		tv10=(TextView)findViewById(R.id.tvKnockoutTeam10);
		tv11=(TextView)findViewById(R.id.tvKnockoutTeam11);
		tv12=(TextView)findViewById(R.id.tvKnockoutTeam12);
		tv13=(TextView)findViewById(R.id.tvKnockoutTeam13);
		tv14=(TextView)findViewById(R.id.tvKnockoutTeam14);
		tv15=(TextView)findViewById(R.id.tvKnockoutChampions);
		imLine1=(ImageView)findViewById(R.id.ivKnockoutLine1);
		imLine2=(ImageView)findViewById(R.id.ivKnockoutLine2);
		imLine3=(ImageView)findViewById(R.id.ivKnockoutLine3);
		imLine4=(ImageView)findViewById(R.id.ivKnockoutLine4);
		imLine5=(ImageView)findViewById(R.id.ivKnockoutLine5);
		imLine6=(ImageView)findViewById(R.id.ivKnockoutLine6);
		imLine7=(ImageView)findViewById(R.id.ivKnockoutLine7);
		imLine8=(ImageView)findViewById(R.id.ivKnockoutLine8);
		b=(Button)findViewById(R.id.bKnockoutSimulate);
		tv1.setText(bundle.getString("team1"));
		tv2.setText(bundle.getString("team2"));
		teams[0]=bundle.getString("team1");
		teams[1]=bundle.getString("team2");
		imageId[0]=bundle.getInt("id1");
		imageId[1]=bundle.getInt("id2");
		b.setOnClickListener(this);
		
		for(int i=0;i<teams.length;i++){
			t[i]=new Team(teams[i],imageId[i]);			
		}
		for(int x=0;x<teams.length;x++){
			info[x]=new PlayerDatabase(Knockout.this);
			info[x].DATABASE_TABLE=teams[x];
			info[x].open();
			t[x].ovr=info[x].getAvg();
			Log.d("Intial",t[x].ovr+" "+info[x].getAvg());
			t[x].setMax();
			info[x].close();
		}
		
		
		r=new Random();
		Runnable runnable=new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(cup)
				{
					if(round==0)
					{
						semi1=result(t[0],t[2]);
						semi2=result(t[1],t[3]);
						semi3=result(t[4],t[6]);
						semi4=result(t[5],t[7]);
						synchronized(obj){
							while(paused){
								try {
									obj.wait();
									Log.d("Quarters", "wait");
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
					}
					else if(round==1)
					{
						f1=result(semi1,semi3);
						f2=result(semi2,semi4);
						Log.d("Check", semi1.name+semi2.name+semi3.name+semi4.name+f1.name+f2.name);
					}
					else
						{
						winners=result(f1,f2);
						tv13.setText(f1.name);
						tv14.setText(f2.name);
						tv15.setTag(winners.name);
						cup=false;
						}
					
				//**************Main Thread***************************
				mainHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
					tv5.setText(semi1.name);
					tv6.setText(semi2.name);
					tv7.setText(semi3.name);
					tv8.setText(semi4.name);
						
					}
				});
				//*************Main Thread****************************
				round++;
				}
			}
			
		};
		
		new Thread(runnable).start();
	}
	

	private class Team{
		private String name;
		public float ovr;
		private int gp,gs,ga,win,loss,draw;
		int[] maxRand=new int[10];
		int ImageId,pos;
		public int pts;
		//boolean semis,finals;
		public Team(String team, int imageId){
			gp=gs=ga=win=loss=draw=0;		
			ImageId=imageId;
			name=team;
			//semis=finals=false;			
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


	private Team result(Team t1, Team t2) {
		// TODO Auto-generated method stub
		int g1=t1.getMax(r.nextInt(10));
		int g2=t2.getMax(r.nextInt(10));
		int random=r.nextInt(2);
		//tv.setText(t1+" : "+t2);
		Log.d("Score",t1.name+" "+g1+"-"+g2+" "+t2.name);
		if(g1>g2){
			return t1;
		}else if(g1<g2){
			return t2;
		}else{
			if(random==0)
				return t1;
			else 
				return t2;
		}
		
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(!paused)
			paused=true;
		else
			paused=false;
		//Intent intent=new Intent(Knockout.this,);
	}
	
}
