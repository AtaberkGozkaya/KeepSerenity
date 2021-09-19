package com.sgcreatives.a0003;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class add_Contact extends AppCompatActivity {
    TextView name_text,  email_text,phone_text;
    Button button;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    FirebaseAuth fauth = FirebaseAuth.getInstance();
    String userId;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        name_text = findViewById(R.id.nameText);
        email_text = findViewById(R.id.emailText);
        phone_text = findViewById(R.id.phoneText);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__contact);
        userId = fauth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Contacts").child(userId);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Contact contact = new Contact();
                contact.name = name_text.toString();
                contact.phoneNumber = phone_text.toString();
                reference.child(userId).setValue(contact).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(add_Contact.this,"succes",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}