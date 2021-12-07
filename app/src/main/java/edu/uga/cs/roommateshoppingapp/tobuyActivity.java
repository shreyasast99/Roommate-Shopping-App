package edu.uga.cs.roommateshoppingapp;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
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
public class tobuyActivity
            extends AppCompatActivity
            implements AddItemDialogFragment.AddItemDialogListener{

    public static final String DEBUG_TAG = "ReviewJobLeadsActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    String temp;

    public static List<Item> itemList;
    private List<String> idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(DEBUG_TAG, "ReviewJobLeadsActivity.onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_buy_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        FloatingActionButton floatingButton = findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddItemDialogFragment();
                showDialogFragment(newFragment);
            }
        });


        // use a linear layout manager for the recycler view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // get a Firebase DB instance reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jobleads");

        itemList = new ArrayList<Item>();
        idList = new ArrayList<String>();

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
                    if(postSnapshot.child("purchased").getValue(Boolean.class)==false){
                        itemList.add(jobLead);
                    }
                    temp = postSnapshot.getKey();
                    idList.add(temp);
                    //itemList.add(jobLead);
                    Log.d(DEBUG_TAG, "ReviewJobLeadsActivity.onCreate(): added: " + jobLead);


                }
                Log.d(DEBUG_TAG, "ReviewJobLeadsActivity.onCreate(): setting recyclerAdapter");

                // Now, create a itemRecyclerAdapter to populate a RecyclerView to display the job leads.
                recyclerAdapter = new itemRecyclerAdapter(itemList);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

    // this is our own callback for a DialogFragment which adds a new job lead.
    public void onFinishNewItemDialog(Item jobLead) {
        // add the new job lead
        // Add a new element (JobLead) to the list of job leads in Firebase.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jobleads");

        // First, a call to push() appends a new node to the existing list (one is created
        // if this is done for the first time).  Then, we set the value in the newly created
        // list node to store the new job lead.
        // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
        // the previous apps to maintain job leads.
        myRef.push().setValue(jobLead)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        // Update the recycler view to include the new job lead
                        itemList.add(jobLead);
                        recyclerAdapter.notifyItemInserted(itemList.size() - 1);

                        Log.d(DEBUG_TAG, "Job lead saved: " + jobLead);
                        // Show a quick confirmation
                        Toast.makeText(getApplicationContext(), "Job lead created for " + jobLead.getName(),
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to create a Job lead for " + jobLead.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void showDialogFragment(DialogFragment newFragment) {
        newFragment.show(getSupportFragmentManager(), null);
    }
}