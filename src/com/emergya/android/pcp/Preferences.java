package com.emergya.android.pcp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Preferences extends Activity implements OnItemSelectedListener, TextWatcher {

	public static final String USERNAME = "uname";
	public static final String BGCOLOR = "bgcolor";
	public static final String BGINDEX = "bgcolor_index";
	public static final String PREFERENCES = "PCP_Preferences";

	//Manejadores de los elementos de la interfaz
	private EditText userInput;
	private Spinner bgSpinner;
	private TextView titulo;
	
	//Manejador del objeto Preferencias
	private SharedPreferences preferences;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        
        //Enlace de las prefrencias
        preferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        
        //Enlace de los controles de la interfaz gráfica
        userInput = (EditText) findViewById(R.id.userNameInput);
        bgSpinner = (Spinner) findViewById(R.id.colorSpinner);
        titulo = (TextView)findViewById(R.id.tittlePreferences);
        
        //Inicialización del spinner
        ArrayAdapter<CharSequence> colores = ArrayAdapter.createFromResource(this,
        		R.array.colores, android.R.layout.simple_spinner_item);
        colores.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bgSpinner.setAdapter(colores);
        bgSpinner.setOnItemSelectedListener(this);
        
        //Inicialización del EditText
        userInput.addTextChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_preferences, menu);
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
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    }
    
	@Override
	protected void onResume() {
		restorePreferences();
		super.onResume();
	}
	
	private void restorePreferences(){
		String us = preferences.getString(USERNAME, "empty");
		titulo.setText(getText(R.string.tittlePreferences) + " " + us);
		userInput.setText(us);
		
		View layoutPref = titulo.getRootView();
		int color = preferences.getInt(BGCOLOR, Color.WHITE);
		bgSpinner.setSelection(preferences.getInt(BGINDEX, 0));
		layoutPref.setBackgroundColor(color);
	}
	
	private void savePreferences(){
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(USERNAME, userInput.getText().toString());
		editor.putInt(BGCOLOR, Color.parseColor(
			((TextView)bgSpinner.getSelectedView()).getText().toString()));
		editor.putInt(BGINDEX,bgSpinner.getSelectedItemPosition());
		editor.commit();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
		if(arg0.getId()==R.id.colorSpinner){
			View layoutPref = titulo.getRootView();
			layoutPref.setBackgroundColor(Color.parseColor(((TextView)arg1).getText().toString()));
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterTextChanged(Editable s) {
		titulo.setText(getText(R.string.tittlePreferences) 
				+ " " + userInput.getText().toString());
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
	}
	
	@Override 
	protected void onSaveInstanceState(Bundle outState) {
		//savePreferences();
	}
	
	@Override 
	protected void onPause() {
		savePreferences();
		super.onPause();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
	}

}
