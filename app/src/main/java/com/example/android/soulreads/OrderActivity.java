package com.example.android.soulreads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {


    Button placeOrder;
    private EditText bnEdit;
    private EditText AddressEdit;
    private TextView mText;
    private static final String TAG = "OrderActivity";

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        bnEdit = (EditText) findViewById(R.id.textView4);
        bnEdit.getText().toString();
        AddressEdit = (EditText) findViewById(R.id.textView4);
        AddressEdit.getText().toString();
        placeOrder = findViewById(R.id.placeOrder);
        email = getIntent().getStringExtra("loggedInEmail");

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> order = new HashMap<>();
                order.put("ISBN", bnEdit.getText().toString());
                order.put("Address", AddressEdit.getText().toString());
                order.put("email", email);

// Add a new document with a generated ID
                db.collection("orderBooks")
                        .add(order)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Order successful!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Order unsuccessful!", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
