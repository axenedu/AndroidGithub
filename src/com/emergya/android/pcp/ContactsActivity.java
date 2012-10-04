package com.emergya.android.pcp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactsActivity extends Activity {

	private ListView listView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        
        this.listView = (ListView)findViewById(R.id.contactView);
        
        List<String> peopleList = getPeopleContent();
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, peopleList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contacts, menu);
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
    
    private List<String> getPeopleContent() {
		List<String> peopleList = new ArrayList<String>();
		
		String [] contactProjection = new String[]
			    {
					ContactsContract.Contacts._ID,
					ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
					ContactsContract.Contacts.LOOKUP_KEY
			    };
		
		
		ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, contactProjection, null, null, null);
                
        if (cursor!=null) {
        	int idIndex   = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
            int nameIndex = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
        	while (cursor.moveToNext()){
        		String id  = cursor.getString(idIndex);
        		String name = cursor.getString(nameIndex);
        		
        		peopleList.add(name + " : " + getPrimaryPhone(id));
        	} 
        	cursor.close();
        }
                            
		return peopleList;
	}
    
    private String getPrimaryPhone(String id)
    {
    	String phone = "";
    	
    	String [] contactRawProjection = new String[]
			    {
					ContactsContract.RawContacts._ID,
			    };
    	
    	Cursor c = getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI,
    	          contactRawProjection,
    	          ContactsContract.RawContacts.CONTACT_ID + "=?",
    	          new String[]{id}, null);
    	
    	if(c!=null){
    		int idIndex = c.getColumnIndexOrThrow(ContactsContract.RawContacts._ID);
    		if(c.moveToNext()){
    			String idRaw = c.getString(idIndex);
    			Log.v("ID de Raw", idRaw);
    			String dataPhone = getDataPhone(idRaw);
    			if(dataPhone!=null)
    				phone=dataPhone;
    		}
        	c.close();
    	}
    	
    	return phone;
    }
    
    private String getDataPhone(String idRaw){
    	
    	String phone = null;
    	String [] phoneDataProjection = new String[]
			    {
					ContactsContract.Data.DATA1,
					ContactsContract.Data.MIMETYPE
					
			    };
    	
    	Cursor c = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
  	          phoneDataProjection,
  	          ContactsContract.Data.CONTACT_ID + "=?" + " AND " 
  	          + ContactsContract.Data.MIMETYPE + "=?",
  	          new String[]{idRaw, 
    			ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE}, null);
    	
    	if(c!=null){
    		int phoneIndex = c.getColumnIndexOrThrow(ContactsContract.Data.DATA1);
    		//int mime = c.getColumnIndexOrThrow(ContactsContract.Data.MIMETYPE);
    		while(c.moveToNext()){
    			String dataPhone = c.getString(phoneIndex);
    			//String mimeType = c.getString(mime);
    			//Log.v("MimeType",mimeType + " Vs " + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
    			if(dataPhone!=null)
    				phone=dataPhone;
    		}
        	c.close();
    	}
    	
    	return phone;
    }

}
