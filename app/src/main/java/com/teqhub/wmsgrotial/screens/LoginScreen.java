package com.teqhub.wmsgrotial.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.teqhub.wmsgrotial.R;

public class LoginScreen extends AppCompatActivity {

    TextView logintypetext;
    EditText email,password;
    Button loginBtn;

    FirebaseFirestore firebaseFirestore;

    FirebaseAuth mAuth;

    CollectionReference accountData;

    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);


        FirebaseApp firebaseApp = FirebaseApp.initializeApp(this);
        logintypetext = findViewById(R.id.logintypetext);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();





          firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();

        firebaseFirestore.setFirestoreSettings(settings);


        Bundle extras = getIntent().getExtras();

        String type = extras.getString("type");

        logintypetext.setText(type+" Login");

        String userAccountType = type +"Accounts";

        Log.d("TAG", "onCreate: "+userAccountType);

        accountData = firebaseFirestore.collection("WMSDatabase")
                .document("accounts")
                .collection(userAccountType);






        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().length()>0 && password.getText().length()>0){

                    verifyAccount(email.getText().toString(),password.getText().toString());

                }else {
                    Toast.makeText(LoginScreen.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void verifyAccount(String email,String password) {

       // query = accountData.whereEqualTo("email",email).whereEqualTo("password",password);

        accountData.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(DocumentSnapshot documentSnapshot :queryDocumentSnapshots.getDocuments()){

                    if(documentSnapshot.get("email").equals(email) && documentSnapshot.get("password").equals(password)){

                        loginUser(email,password);
                        
                        Toast.makeText(LoginScreen.this, "User Found", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(LoginScreen.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

              

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(LoginScreen.this, "User Not Found "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                          //  Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginScreen.this, "Login Successfully",
                                    Toast.LENGTH_SHORT).show();
                       //     FirebaseUser user = mAuth.getCurrentUser();
                         //   updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }
                    }
                });
    }
}