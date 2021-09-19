package com.sgcreatives.a0003;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Vector;

public class MailHandler {
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String id, cemail, cphone;
    Vector<String> contacts = new Vector<>();
    String userId;
    DatabaseReference reference;

    MailHandler(String str,String recieve)
    {
        sendEmail("keepserenitycorp@gmail.com","serenity.keep",recieve,"Violence detected",str);
    }




    private void sendEmail(final String Sender,final String Password,final String Receiver,final String Title,final String Message)
    {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Sender,Password);
                    sender.sendMail(Title, "<b>"+Message+"</b>", Sender, Receiver);


                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
    }

}

