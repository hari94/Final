package com.delta.Final;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Match extends Activity implements OnClickListener{
	Button b;
	Handler mainHandler;
	Thread t;
	Object obj;
	TextView tvteam1,tvteam2,score;
	ImageView iv1,iv2;
	Random r,rd;
	int t1,t2,h1,h2,dr1,dr2;
	boolean mPaused=false;
	int i=0;
	
	//String team1,team2;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			Log.d("Match", "Started new Intent");
			setContentView(R.layout.match);
			Bundle bundle=getIntent().getExtras();
			
			
			final TextView tv=(TextView)findViewById(R.id.tv);
			tvteam1=(TextView)findViewById(R.id.tvTeam1);
			tvteam2=(TextView)findViewById(R.id.tvTeam2);
			iv1=(ImageView)findViewById(R.id.ivMatchTeam1);
			iv2=(ImageView)findViewById(R.id.ivMatchTeam2);
			score=(TextView)findViewById(R.id.tvScore);
			b=(Button)findViewById(R.id.button1);
			
			iv1.setImageResource(bundle.getInt("id1"));
			iv2.setImageResource(bundle.getInt("id2"));
			
			b.setOnClickListener(this);
			mainHandler= new Handler();
			obj=new Object();
			//r=new Random();
			//r2=new Random();
			rd=new Random();
			//rd2=new Random();
			
			tvteam1.setText(bundle.getString("team1"));
			tvteam2.setText(bundle.getString("team2"));
			t1=bundle.getInt("goal1");
			t2=bundle.getInt("goal2");
			//t1=r1.nextInt(5);
			//t2=r2.nextInt(5);
			 dr1=1+rd.nextInt(4);
			 dr2=1+rd.nextInt(4);
			  h1=(int)(t1/dr1);
			  h2=(int)(t2/dr2);
			Runnable runnable=new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(i!=91){
						
						mainHandler.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
							tv.setText(String.valueOf(i)+"'");
							if(i==45){
								//Toast.makeText(Match.this, dr1+" "+dr2, Toast.LENGTH_SHORT).show();
								b.setVisibility(View.VISIBLE);
								score.setText(String.valueOf(h1)+":"+String.valueOf(h2));
							}
							else
								b.setVisibility(View.INVISIBLE);
							if(i==90)
								
								score.setText(String.valueOf(t1)+":"+String.valueOf(t2));
							
								
							}
							
							
						});
						if(i==45){
							pause();
							
							synchronized (obj) {
								while(mPaused){
									try {
										obj.wait();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}	
						delay();
						i++;
					}
				}

				
			};
			t=new Thread(runnable);
			t.start();
			
			
		}
		
		protected void pause() {
			// TODO Auto-generated method stub
				mPaused=true;			
		}		
		
		protected void resume() {
			// TODO Auto-generated method stub
			
			mPaused=false;
					synchronized (obj) {
					
						obj.notify();
					}
				
		
		}
		protected void delay() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		Intent in=new Intent();
		in.setClass(Match.this, League.class);
		if(i==91)
				{
				startActivity(in);
				}
		
			resume();
		}

	

}
