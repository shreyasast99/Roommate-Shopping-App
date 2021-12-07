package edu.uga.cs.roommateshoppingapp;

import static edu.uga.cs.roommateshoppingapp.tobuyActivity.DEBUG_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ItemManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_management);
        Button itemList = findViewById(R.id.itemList);
        itemList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), tobuyActivity.class);
                v.getContext().startActivity( intent );
            }
        });
        Button updateAct = findViewById(R.id.updateList);
        updateAct.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateItem.class);
                v.getContext().startActivity( intent );
            }
        });
        Button purchAct = findViewById(R.id.itemsPurchased);
        purchAct.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PurchasedActivity.class);
                v.getContext().startActivity( intent );
            }
        });
        Button settleAct = findViewById(R.id.settle);
        settleAct.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), costActivity.class);
                v.getContext().startActivity( intent );
            }
        });

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                FirebaseAuth.getInstance().signOut();
                Log.d(DEBUG_TAG, "Sign out success????? ");
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity( intent ); */

                AuthUI.getInstance()
                        .signOut(v.getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(v.getContext(), MainActivity.class));
                                finish();
                            }
                        });
            }
        });
    }




}