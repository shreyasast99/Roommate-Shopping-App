package edu.uga.cs.roommateshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/***
 * This class is for when the user needs to register an account for the first time
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "RegisterActivity";

    private EditText emailEditText;
    private EditText passworEditText;
    private Button   registerButton;
    private List<user> userList;

    /**
     * This is for when the activity is first creates, it lays out the edit text boxes
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = (EditText) findViewById( R.id.editText );
        passworEditText = (EditText) findViewById( R.id.editText5 );

        registerButton = (Button) findViewById( R.id.button3 );
        registerButton.setOnClickListener( new RegisterButtonClickListener() );
    }

    /**
     * This class is for the register button
     */
    private class RegisterButtonClickListener implements View.OnClickListener {
        /**
         * This method saves the account info when the button is clicked
         * @param view
         */
        @Override
        public void onClick(View view) {
            final String email = emailEditText.getText().toString();
            final String password = passworEditText.getText().toString();

            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            user currentUser = new user(email, 0.0F);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users");
            myRef.push().setValue(currentUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        /**
                         * When it successful registers a user
                         * @param aVoid
                         */
                        @Override
                        public void onSuccess(Void aVoid) {

                            Log.d(DEBUG_TAG, "I DID ITTTTTTTT");
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "User add for " + currentUser.getName(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        /**
                         * When it is a failed attempt on registering a user
                         * @param e
                         */
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to create a user for " + currentUser.getName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            // This is how we can create a new user using an email/password combination.
            // Note that we also add an onComplete listener, which will be invoked once
            // a new user has been created by Firebase.  This is how we will know the
            // new user creation succeeded or failed.
            // If a new user has been created, Firebase already signs in the new user;
            // no separate sign in is needed.
            firebaseAuth.createUserWithEmailAndPassword( email, password )
                    .addOnCompleteListener( RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        /**
                         * Once the process of registering a user is complete
                         * @param task
                         */
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText( getApplicationContext(),
                                        "Registered user: " + email,
                                        Toast.LENGTH_SHORT ).show();

                                // Sign in success, update UI with the signed-in user's information
                                Log.d( DEBUG_TAG, "createUserWithEmail: success" );

                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                Intent intent = new Intent( RegisterActivity.this, ItemManagementActivity.class );
                                startActivity( intent );

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(DEBUG_TAG, "createUserWithEmail: failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
    }
}
