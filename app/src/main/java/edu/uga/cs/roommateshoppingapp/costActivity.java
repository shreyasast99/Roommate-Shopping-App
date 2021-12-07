package edu.uga.cs.roommateshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

public class costActivity extends AppCompatActivity {
    Spinner areaSpinner;
    private List<Item> jobLeadsList;
    String[] arraySpinner;
    Spinner purchSpinner;
    List<String> emails;
    List<String> buyers;
    List<Double> itemPrice;
    List<String> keysUser;
    List<String> keysItem;
    List<Double> totalPrices;
    List<String> isPurchasedKeys;
    //List<Item> items;
    DatabaseReference myUserRef;
    DatabaseReference myItemRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myUserRef = database.getReference("users");
        myItemRef = database.getReference("jobleads");
        jobLeadsList = new ArrayList<Item>();
        Log.d("umm ","notice me");
        myUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                emails = new ArrayList<String>();
                keysUser = new ArrayList<String>();
                //items = new ArrayList<Item>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String email = areaSnapshot.child("name").getValue(String.class);
                    String key = areaSnapshot.getKey();
                    //Item jobLead = areaSnapshot.getValue(Item.class);
                    emails.add(email);
                    keysUser.add(key);
                    //items.add(jobLead);
                    //Log.e("wtf",areaName);
                }
                //Log.i("the name: ",areas.get(2));
                areaSpinner = (Spinner) findViewById(R.id.spentByRoommate);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(costActivity.this, android.R.layout.simple_spinner_item, emails);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //ITEMS
        myItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                buyers = new ArrayList<String>();
                keysItem = new ArrayList<String>();
                itemPrice = new ArrayList<Double>();
                isPurchasedKeys = new ArrayList<String>();
                //items = new ArrayList<Item>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    if(areaSnapshot.child("buyer").exists()) {
                        String buyer = areaSnapshot.child("buyer").getValue(String.class);
                        double price = areaSnapshot.child("price").getValue(double.class);
                        String key = areaSnapshot.getKey();
                        buyers.add(buyer);
                        itemPrice.add(price);
                        keysItem.add(key);
                    }
                    String key = areaSnapshot.getKey();
                    if(areaSnapshot.child("purchased").getValue(Boolean.class)==true){
                        isPurchasedKeys.add(key);
                    }
                    //Item jobLead = areaSnapshot.getValue(Item.class);

                    //items.add(jobLead);
                    //Log.e("wtf",areaName);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //gonna get the total prices again i guess
        myUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                totalPrices = new ArrayList<Double>();
                //items = new ArrayList<Item>();
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    double totalCost = areaSnapshot.child("spent").getValue(Double.class);
                    Log.e("THE TOTAL COST",Double.toString(totalCost));
                    totalPrices.add(totalCost);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Button settleCosts = findViewById(R.id.settleTheCosts);
        settleCosts.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //for loop that adds the costs of each roommate
                //double totalPrice=0;
                for(int i=0;i<emails.size();i++){
                    double totalPrice=0;
                    for(int j=0;j<buyers.size();j++){
                        //Log.e("buyer: ",buyers.get(j));
                        //Log.e("email: ",emails.get(i));

                        if(buyers.get(j).equals(emails.get(i))){
                            totalPrice = totalPrice+itemPrice.get(j);
                            Log.e("buyer: ",buyers.get(j));
                            Log.e("email: ",emails.get(i));
                            Log.e("totalPrice: ",Double.toString(totalPrice));
                        }


                        //Log.e("buyer: ",buyers.get(j));
                    }
                    Log.e("totalPrice after: ",Double.toString(totalPrice));
                    myUserRef.child(keysUser.get(i)).child("spent").setValue(totalPrice);
                }

                TextView userSel = (TextView) areaSpinner.getSelectedView();
                String item_text = userSel.getText().toString();//got what user selected
                int index=0;
                for(int i=0;i<emails.size();i++){
                    if(emails.get(i).equals(item_text)){
                        index=i;//got index of key
                        break;
                    }
                }
                String keyOfItem = keysUser.get(index);//got the key of the item

                Log.e("this is key: ",keyOfItem);

                TextView roommate = findViewById(R.id.perRoommate);
                roommate.setText("Roommate's Expense: "+Double.toString(totalPrices.get(index)));

                TextView collectiveCost = findViewById(R.id.totalCost);
                int collectiveTotal=0;
                for(int i=0;i<totalPrices.size();i++){
                    collectiveTotal+=totalPrices.get(i);
                }
                collectiveCost.setText("Total Cost: "+Double.toString(collectiveTotal));

                TextView average = findViewById(R.id.averageCost);
                average.setText("Average Cost: "+Double.toString(collectiveTotal/totalPrices.size()));
                //also delete all the items in the recently purchased list
                for(int i=0;i<isPurchasedKeys.size();i++){
                    myItemRef.child(isPurchasedKeys.get(i)).removeValue();
                    Log.e("is it going in here", isPurchasedKeys.get(i));
                }
                /*
                Intent intent = new
                        Intent( view.getContext(),
                        tobuyActivity.class );
                startActivity( intent );


                 */
            }
        });
    }
}