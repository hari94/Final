package com.delta.Final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TeamList extends Activity implements OnClickListener {
ListView lvPos1,lvName1,lvOvr1,lvPos2,lvName2,lvOvr2;
Button b;
/*String[] pos={"G","D","D","D","D","M","M","M","A","A","A"};
String[] name={"Reina","Enrique","Agger","Skrtel","Johnson","Henderson","Coutinho","Gerard","Downing","Sturridge","Suarez"};
int[] ovr={87,83,81,85,84,83,83,87,86,90,90};
*/
String[] POS1,NAMES1,OVR1,POS2,NAMES2,OVR2;
String team1,team2;
int goal1,goal2,id1,id2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.team_list);
		Bundle bundle=getIntent().getExtras();
		team1=bundle.getString("team1").toString();
		team2=bundle.getString("team2").toString();
		goal1=bundle.getInt("goal1");
		goal2=bundle.getInt("goal2");
		id1=bundle.getInt("id1");
		id2=bundle.getInt("id2");
		lvPos1=(ListView)findViewById(R.id.lvPosition1);
		lvName1=(ListView)findViewById(R.id.lvNames1);
		lvOvr1=(ListView)findViewById(R.id.lvOvr1);
		lvPos2=(ListView)findViewById(R.id.lvPosition2);
		lvName2=(ListView)findViewById(R.id.lvNames2);
		lvOvr2=(ListView)findViewById(R.id.lvOvr2);
		b=(Button)findViewById(R.id.bStartMatch);
		b.setOnClickListener(this);
		/*PlayerDatabase team=new PlayerDatabase(this);
		team.DATABASE_TABLE="Liverpool";
		team.open();
		team.deleteAll();
		//Toast.makeText(this, "deleting manu", Toast.LENGTH_SHORT).show();
		for(int i=0;i<11;i++)
			team.insertData(pos[i], name[i], ovr[i]);		
		team.close();
		Toast.makeText(this, "created", Toast.LENGTH_SHORT).show();
		*/
		PlayerDatabase info1=new PlayerDatabase(this);
		info1.DATABASE_TABLE=team1;
		info1.open();
		try{
			String getPos=info1.getAllPos();
			String getName=info1.getAllName();
			String getOvr=info1.getAllOvr();
			POS1=getPos.split(";");
			NAMES1=getName.split(";");
			OVR1=getOvr.split(";");
		}catch(Exception e){
			e.printStackTrace();
		}
		Toast.makeText(this,team1+ String.valueOf(info1.getAvg()), Toast.LENGTH_LONG).show();
		info1.close();
		
		//Toast.makeText(this,team2, Toast.LENGTH_SHORT).show();
		PlayerDatabase info=new PlayerDatabase(this);
		info.DATABASE_TABLE=team2;
		info.open();
		try{
			String getPos=info.getAllPos();
			String getName=info.getAllName();
			String getOvr=info.getAllOvr();
			POS2=getPos.split(";");
			NAMES2=getName.split(";");
			OVR2=getOvr.split(";");
		}catch(Exception e){
			e.printStackTrace();
		}
		Toast.makeText(this, team2+String.valueOf(info.getAvg()), Toast.LENGTH_LONG).show();
		info.close();
		
				
	ArrayAdapter<String> adp1=new ArrayAdapter<String>(this, R.layout.single_pos1, R.id.tvSinglePos1,POS1);
	ArrayAdapter<String> adp2=new ArrayAdapter<String>(this, R.layout.single_name1, R.id.tvSingleName1,NAMES1);
	ArrayAdapter<String> adp3=new ArrayAdapter<String>(this, R.layout.single_ovr1, R.id.tvSingleOvr1,OVR1);
	
	ArrayAdapter<String> adp4=new ArrayAdapter<String>(this, R.layout.single_pos2, R.id.tvSinglePos2,POS2);
	ArrayAdapter<String> adp5=new ArrayAdapter<String>(this, R.layout.single_name2, R.id.tvSingleName2,NAMES2);
	ArrayAdapter<String> adp6=new ArrayAdapter<String>(this, R.layout.single_ovr2, R.id.tvSingleOvr2,OVR2);
	
	lvPos1.setAdapter(adp1);
	lvName1.setAdapter(adp2);	
	lvOvr1.setAdapter(adp3);
	
	lvPos2.setAdapter(adp4);
	lvName2.setAdapter(adp5);	
	lvOvr2.setAdapter(adp6);
	
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(TeamList.this,Match.class);
		intent.putExtra("goal1",goal1);
		intent.putExtra("goal2",goal2);
		intent.putExtra("id1", id1);
		intent.putExtra("id2", id2);
		intent.putExtra("team1", team1);
		intent.putExtra("team2", team2);
		startActivity(intent);
		finish();
	}

}
