package com.example.android.soulreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button loginButton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private void updateUI(FirebaseUser firebaseUser) {
//        if (firebaseUser != null) {
//            startActivity(new Intent(MainActivity.this, signup.class));
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }


    public void getDetails(FirebaseUser firebaseUser) {
        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        String phonenumber = firebaseUser.getPhoneNumber();
        String address = firebaseUser.getPhoneNumber();
        boolean emailVerified = firebaseUser.isEmailVerified();
        String uid = firebaseUser.getUid();
    }



/*addOnCompleteListener(new OnCompleteListener<Void>) {
        @Override
        public void onComplete(Task task); {
            if (task.isSuccessful()) {
                Log.d(TAG, "Email sent.");
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);

        buttonRegister = findViewById(R.id.buttonRegister);

        editTextEmail = findViewById(R.id.editTextEmail);

        editTextPassword = findViewById(R.id.editTextPassword);

        loginButton = findViewById(R.id.loginButton);

        buttonRegister.setOnClickListener(this);
        loginButton.setOnClickListener(this);


    }

    private void loginUser() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                            EditText editText = (EditText) findViewById(R.id.editText);
//                            String message = editText.getText().toString();
        intent.putExtra("email", "hi");
        startActivity(intent);
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();

        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //PROFILE STARTS HERE
                            Toast.makeText(MainActivity.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();

                            updateUI(firebaseUser);
                            getDetails(firebaseUser);
                            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
//                            EditText editText = (EditText) findViewById(R.id.editText);
//                            String message = editText.getText().toString();
                            intent.putExtra("email", editTextEmail.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Could not register", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            //updateUI(null);
                        }
                    }
                });
    }
    //user.sendEmailVerification();

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            registerUser();
        }
        if (view == loginButton) {
            loginUser();

        }

    }

}