package com.example.android.soulreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class WelcomeActivity extends AppCompatActivity {
    EditText editText;
    Button order,logout;
    String email;
    TableLayout welcomeBook;
    TextView welcomeName;
    private static final String TAG = "WelcomeActivity";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        welcomeBook = findViewById(R.id.datafetch); // Root ViewGroup in which you want to add textviews
        welcomeName = (TextView) findViewById(R.id.welcomeName);
        email = getIntent().getStringExtra("loggedInEmail");
        welcomeName.setText(welcomeName.getText() + " " + email);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        order = (Button) findViewById(R.id.order);
        logout = (Button) findViewById(R.id.logout);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, OrderActivity.class);
//                            EditText editText = (EditText) findViewById(R.id.editText);
//                            String message = editText.getText().toString()
                intent.putExtra("loggedInEmail", email);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TextView tv = new TextView(WelcomeActivity.this); // Prepare textview object programmatically
                                tv.setText(document.getData().toString());
                                tv.setId(Integer.parseInt(document.getId()));
                                welcomeBook.addView(tv); // Add to your ViewGroup using this method
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}

