package com.example.android.soulreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signup extends AppCompatActivity implements View.OnClickListener{


    private TextView textViewUserEmail;
    private DatabaseReference databaseReference;
    private Button buttonLogout;
    private Button buttonAddPeople;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editPhoneNumber;

    FirebaseAuth firebaseAuth;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editPhoneNumber = (EditText) findViewById(R.id.editPhoneNumber);
        editTextName = (EditText) findViewById(R.id.editTextName);
        textViewUserEmail = (TextView)findViewById(R.id.textViewUserEmail);



                buttonAddPeople = (Button) findViewById(R.id.buttonAddPeople);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        textViewUserEmail.setText("Welcome "+user.getEmail());


        buttonAddPeople.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }
    private void saveUserInformation(){
        String name=editTextName.getText().toString().trim();
        String phn=editPhoneNumber.getText().toString().trim();
        String add=editTextAddress.getText().toString().trim();
        UserInformation userInformation = new UserInformation(name,add,phn);

        FirebaseUser user =firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this,"information saved",Toast.LENGTH_LONG).show();




    }

public void onClick(View view)
{
    if(view == buttonLogout)
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }
    if(view == buttonAddPeople)
        saveUserInformation();

}
}

