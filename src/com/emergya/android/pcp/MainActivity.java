package com.emergya.android.pcp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	private SharedPreferences preferences;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(Preferences.PREFERENCES, Context.MODE_PRIVATE);
        
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, 
        		R.array.menu_principal, android.R.layout.simple_list_item_1);
		ListView lstOpciones = (ListView) findViewById(R.id.listView1);
		lstOpciones.setOnItemClickListener(this);
		lstOpciones.setAdapter(adaptador);
    }
    
	@Override
	protected void onResume() {
		establecePreferences();
		super.onResume();
	}
	
    private void establecePreferences(){
		
		View layoutPref = findViewById(R.id.listView1);
		layoutPref=layoutPref.getRootView();
		int color = preferences.getInt(Preferences.BGCOLOR, Color.WHITE);
		layoutPref.setBackgroundColor(color);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
    	switch (arg2) {
		case 0:
//			Toast.makeText(this, "Texto del menu", Toast.LENGTH_LONG).show();
//			NavUtils.navigateUpTo(this, new Intent(this, Preferences.class));
			Intent intentAyuda = new Intent(this, Preferences.class);
			startActivity(intentAyuda);
			break;
		case 1:
//			Toast.makeText(this, "Texto del menu", Toast.LENGTH_LONG).show();
//			NavUtils.navigateUpTo(this, new Intent(this, Preferences.class));
			Intent intentContactos = new Intent(this, ContactsActivity.class);
			startActivity(intentContactos);
			break;
		default:
			Toast.makeText(this, "No Habilitado: " + arg3, Toast.LENGTH_SHORT).show();
			break;
		}
    	
	}
}
