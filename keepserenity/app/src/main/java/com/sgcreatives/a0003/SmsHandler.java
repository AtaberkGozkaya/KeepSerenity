package com.sgcreatives.a0003;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;

import android.app.Activity;


import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class SmsHandler extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String id, cemail, cphone;
    Vector<Contact> contacts = new Vector<>();
    String userId;
    DatabaseReference reference;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_handler);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        id = fauth.getCurrentUser().getUid();
        userId = fauth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("Contacts").child(userId);

        DocumentReference documentReference = fstore.collection("users").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                cemail = value.getString("contact-email");
                cphone = value.getString("contact-phone");
            }
        });
        getData(contacts);
        sendSMSMessage();


    }

    private void startMailing()
    {
        startActivity(new Intent(getApplicationContext(), MailHandler.class));
    }

    protected void sendSMSMessage() {


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //getData(contacts);
                    SmsManager smsManager = SmsManager.getDefault();
                    String message = getCompleteAddressString(MainActivity.latitude,MainActivity.longtitude);

                    //Log.d("TAG", "enter size : "+size);
                  //  Log.d("TAG", "enter size : "+contacts.size());

                   // Log.d("TAG", "before size : "+contacts.size());
                    //for(int i = 0;i <contacts.size()  ;i++) {
                      //  Log.d("TAG", "inside size : "+ contacts.size());
                      //  smsManager.sendTextMessage("05319255262", null, message, null, null);
                   // }
                    Log.d("TAG", "after size : "+contacts.size());
                    finish();
                    startMailing();

                } else {
                    return;
                }
            }
        }

    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction :", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction :", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction :", "Canont get Address!");
        }
        return strAdd;
    }
    private void getData(final Vector<Contact> contact)
    {
       // int size;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SmsManager smsManager = SmsManager.getDefault();
                String message = getCompleteAddressString(MainActivity.latitude,MainActivity.longtitude);
                if(snapshot.getValue() != null)
                {
                    for(DataSnapshot snap: snapshot.getChildren())
                    {

                         Contact cnt =  snap.getValue(Contact.class);

                         //String  k = cnt.phoneNumber.toString();
                       // Log.d("TAG", "added : "+k);
                        contact.add(cnt);
                       // Log.d("TAG", "added : "+contact.size());
                    }
                    size = contact.size();
                    Log.d("TAG", "size: : "+size    );
                    for(int i = 0;i <contact.size()  ;i++) {
                    //    Log.d("TAG", "inside size : "+ contacts.size());
                        smsManager.sendTextMessage(contact.elementAt(i).phoneNumber, null, message, null, null);
                        new MailHandler(message,contact.elementAt(i).name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SmsHandler.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("TAG", "func end : : "+size    );

    }
}