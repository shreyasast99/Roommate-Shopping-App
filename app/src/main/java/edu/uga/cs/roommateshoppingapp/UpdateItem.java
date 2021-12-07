package edu.uga.cs.roommateshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class will update the item in case a mistake has been made
 */
public class UpdateItem extends AppCompatActivity {
    Spinner areaSpinner;
    private List<Item> jobLeadsList;
    String[] arraySpinner;
    EditText price;
    Spinner purchSpinner;
    List<String> areas;
    List<String> keys;
    List<Item> items;
    DatabaseReference myRef;

    /**
     * This is when the activity is first created, it sets up the layout and opens connection with database
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        purchSpinner = (Spinner) findViewById(R.id.purchased);
        String [] choices= {"yes","no"};
        ArrayAdapter<String> purchAdapter = new ArrayAdapter<String>(UpdateItem.this, android.R.layout.simple_spinner_item, choices);
        purchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purchSpinner.setAdapter(purchAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("jobleads");
        jobLeadsList = new ArrayList<Item>();
        Log.d("umm ","notice me");
        myRef.addValueEventListener(new ValueEventListener() {
            /**
             * This is creating the spinner of items available for change
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                areas = new ArrayList<String>();
                keys = new ArrayList<String>();
                items = new ArrayList<Item>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("name").getValue(String.class);
                    String key = areaSnapshot.getKey();
                    Item jobLead = areaSnapshot.getValue(Item.class);
                    areas.add(areaName);
                    keys.add(key);
                    items.add(jobLead);
                    //Log.e("wtf",areaName);
                }

                areaSpinner = (Spinner) findViewById(R.id.itemSpinner);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(UpdateItem.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            /**
             * When it has been cancelled
             * @param databaseError
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Button updateAct = findViewById(R.id.submitUpdate);
        updateAct.setOnClickListener( new View.OnClickListener() {
            /**
             * This is loading the data after the item has been selected
             * @param view
             */
            @Override
            public void onClick(View view) {
                TextView itemSel = (TextView) areaSpinner.getSelectedView();
                String item_text = itemSel.getText().toString();//got what user selected
                int index=0;
                for(int i=0;i<areas.size();i++){
                    if(areas.get(i).equals(item_text)){
                        index=i;//got index of key
                        break;
                    }
                }
                String keyOfItem = keys.get(index);//got the key of the item

                Log.e("this is key: ",keyOfItem);
                TextView purchSel = (TextView) purchSpinner.getSelectedView();
                String purch_text = purchSel.getText().toString();
                boolean isPurchased=false;
                if(purch_text.equals("yes")) {
                    isPurchased = true;
                    items.get(index).setPurchased(true);
                }else{
                    isPurchased=false;
                }
                price   = (EditText)findViewById(R.id.Price);
                double purchasePrice =Double.parseDouble(price.getText().toString());
                /*
                HashMap<String, Object> map = new HashMap<>();
                //map.put("itemName", "a");
                map.put("price", purchasePrice);

//
                myRef.child(keyOfItem).child("price").updateChildren(map);
                */
                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getEmail();
                myRef.child(keyOfItem).child("price").setValue(purchasePrice);
                myRef.child(keyOfItem).child("buyer").setValue(uid);
                myRef.child(keyOfItem).child("purchased").setValue(isPurchased);
                Intent intent = new
                        Intent( view.getContext(),
                        tobuyActivity.class );
                startActivity( intent );

            }
        });
        //DELETE
        Button deleteItem = findViewById(R.id.delete);
        deleteItem.setOnClickListener( new View.OnClickListener() {
            /**
             * Deleting an item
             * @param view
             */
            @Override
            public void onClick(View view) {
                TextView itemSel = (TextView) areaSpinner.getSelectedView();
                String item_text = itemSel.getText().toString();//got what user selected
                int index=0;
                for(int i=0;i<areas.size();i++){
                    if(areas.get(i).equals(item_text)){
                        index=i;//got index of key
                        break;
                    }
                }
                String keyOfItem = keys.get(index);//got the key of the item
                myRef.child(keyOfItem).removeValue();//removing the item
                Intent intent = new
                        Intent( view.getContext(),
                        tobuyActivity.class );
                startActivity( intent );
            }
        });
    }

}