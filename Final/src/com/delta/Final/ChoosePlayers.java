package com.delta.Final;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ChoosePlayers extends Activity implements OnItemClickListener, OnClickListener, OnItemLongClickListener {

TextView tvBal,tvPlayersRem;
Button b;
ListView lvPlayers;
String[] pos={"G","G","G","D","D","D","D","D","D","M","M","M","M","M","A","A","A","A","A"};
String[] name={"Valdes","Fabiasnki","Buffon","Varane","Shawcross","Jenkinson","Hummels","Boateng","Ramos","Bale","Diaby","Malouda","Goetze","Gourcuff","Jovetic","Afobe","Remy","Altidore","Falcao"};
int[] ovr={80,76,90,82,78,78,84,85,87,88,78,76,90,84,85,76,80,82,87};
int[] price={3,0,15,4,1,1,5,6,12,20,1,0,15,7,10,0,1,4,15};
int cost,count=0,bal,countExtra,countRemove;
String s="";
String seasonStatus;
Bundle bundle;
boolean itemclick=true;
//SharedPreferences prefs;
//SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.choose_players);
				
		//prefs=getSharedPreferences("MyPrefs", 0);
		
		
		tvBal=(TextView)findViewById(R.id.tvBalance);
		tvPlayersRem=(TextView)findViewById(R.id.tvPlayersRemaining);
		lvPlayers=(ListView)findViewById(R.id.lvChoosePlayers);
		bundle=getIntent().getExtras();
		seasonStatus=bundle.getString("season");
		b=(Button)findViewById(R.id.bStartLeague);
		b.setVisibility(View.INVISIBLE);
		b.setOnClickListener(this);
		if(seasonStatus.equals("new"))
		{
			bal=40;
			PlayerDatabase del=new PlayerDatabase(this);
			del.DATABASE_TABLE="MyTeam";
			del.open();
			del.deleteAll();
			
			del.close();
			
			
			ChoosePlayersDatabase entry=new ChoosePlayersDatabase(this);
			entry.open();
			entry.deletetAll();
			for(int i=0;i<pos.length;i++)
				entry.insertData(pos[i], name[i], ovr[i], price[i]);
			entry.close();		
		}
		else
			{
			bal=bundle.getInt("balance");
			tvPlayersRem.setText("");
			b.setVisibility(View.VISIBLE);
			b.setText("Continue");
			countExtra=0;
			countRemove=0;
			}
		Toast.makeText(this,String.valueOf(bal), Toast.LENGTH_SHORT).show();
		tvBal.setText("Available Balance: "+bal);
		ChoosePlayersDatabase info=new ChoosePlayersDatabase(this);
		info.open();//String str=info.getData();		
		String[] getName=info.getData().split(";");		
		info.close();
		
		
		ArrayAdapter<String> adp=new ArrayAdapter<String>(this,R.layout.single_player ,R.id.tvSinglePlayerName, getName);
		
		lvPlayers.setAdapter(adp);	
		lvPlayers.setOnItemClickListener(this);
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(itemclick)
		{
		String[] str=arg0.getItemAtPosition(arg2).toString().split("\t\t");
		cost=Integer.parseInt(str[3]);
		
		if(cost<=bal)
			{
			if(!s.contains(str[1]))
				{
				s=s+str[1];	
				//count++;
				bal-=cost;
				PlayerDatabase add=new PlayerDatabase(this);
				add.DATABASE_TABLE="MyTeam";
				add.open();
				add.insertData(str[0], str[1], Integer.parseInt(str[2]));
				Toast.makeText(this,"Added", Toast.LENGTH_SHORT).show();

				
				add.close();
				ChoosePlayersDatabase del=new ChoosePlayersDatabase(this);
				del.open();
				del.delete(str[1]);
				del.close();
				if(seasonStatus.equals("new"))
				{	
					count++;
					tvPlayersRem.setText("Players remaining to be bought: "+String.valueOf(11-count));	
				}
				else
					countExtra++;
				}
			else
				Toast.makeText(this,"Player Already Chosen", Toast.LENGTH_SHORT).show();
				//arg1.setBackgroundColor(Color.parseColor("#000000"));
				//arg1.setClickable(true);
			}
		else
			Toast.makeText(this,"Not Enough Money!", Toast.LENGTH_LONG).show();
		tvBal.setText("Available Balance: "+String.valueOf(bal));
		if(count==11)
			b.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(seasonStatus.equals("end")){
		remove();
		}
		else{
		Intent intent=new Intent(this,League.class);		
		startActivity(intent);
		finish();
		}
	}

	private void remove() {
		// TODO Auto-generated method stub
		
		itemclick=false;
		b.setVisibility(View.INVISIBLE);
		PlayerDatabase rmve=new PlayerDatabase(ChoosePlayers.this);
		rmve.DATABASE_TABLE="MyTeam";		
		rmve.open();
		rmve.getData();
		String[] getlist=rmve.getData().split("\n");		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.single_player ,R.id.tvSinglePlayerName, getlist);
		lvPlayers.setAdapter(adapter);
		lvPlayers.setOnItemLongClickListener(this);
		rmve.close();
		//countRemove++;
		//tvPlayersRem.setText("Players remaining to be removed: "+String.valueOf(countExtra-countRemove));
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		if(countRemove<countExtra)
			{
			String[] dataRemove=arg0.getItemAtPosition(arg2).toString().split("\t\t");
			PlayerDatabase rmveplayer=new PlayerDatabase(ChoosePlayers.this);
			rmveplayer.DATABASE_TABLE="MyTeam";		
			rmveplayer.open();
			rmveplayer.delete(dataRemove[1]);
			countRemove++;
			Toast.makeText(this,"Player Removed", Toast.LENGTH_SHORT).show();
			tvPlayersRem.setText("Players remaining to be removed: "+String.valueOf(countExtra-countRemove));
			String[] currlist=rmveplayer.getData().split("\n");		
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.single_player ,R.id.tvSinglePlayerName, currlist);
			lvPlayers.setAdapter(adapter);
			rmveplayer.close();			
			}
		else if(countRemove==countExtra)
			{
			Toast.makeText(this,"Can't Remove Any more Players!!!", Toast.LENGTH_SHORT).show();
			seasonStatus="new";
			b.setVisibility(View.VISIBLE);
			b.setText("Start");
			
			}
		return false;
	}

}
