package edu.uga.cs.roommateshoppingapp;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * This is an activity controller class for listing the current job leads.
 * The current job leads are listed as a RecyclerView.
 */
public class PurchasedActivity
        extends AppCompatActivity
        {

    public static final String DEBUG_TAG = "ReviewJobLeadsActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(DEBUG_TAG, "ReviewJobLeadsActivity.onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        // use a linear layout manager for the recycler view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // get a Firebase DB instance reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jobleads");

        itemList = new ArrayList<Item>();

        // Set up a listener (event handler) to receive a value for the database reference, but only one time.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method.
        // We can use this listener to retrieve the current list of JobLeads.
        // Other types of Firebase listeners may be set to listen for any and every change in the database
        // i.e., receive notifications about changes in the data in real time (hence the name, Realtime database).
        // This listener will be invoked asynchronously, as no need for an AsyncTask, as in the previous apps
        // to maintain job leads.
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Once we have a DataSnapshot object, knowing that this is a list,
                // we need to iterate over the elements and place them on a List.
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Item jobLead = postSnapshot.getValue(Item.class);
                    Log.i("checkme:name ",jobLead.getName());
                    Log.i("checkme: ",Boolean.toString(jobLead.getPurchased()));
                    if(postSnapshot.child("purchased").getValue(Boolean.class)){
                        itemList.add(jobLead);
                    }

                    Log.d(DEBUG_TAG, "ReviewJobLeadsActivity.onCreate(): added: " + jobLead);
                }
                Log.d(DEBUG_TAG, "ReviewJobLeadsActivity.onCreate(): setting recyclerAdapter");

                // Now, create a itemRecyclerAdapterr to populate a ReceyclerView to display the job leads.
                recyclerAdapter = new itemRecyclerAdapter(itemList);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }



}