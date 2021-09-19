package com.sgcreatives.a0003;

        import android.Manifest;
        import android.content.Intent;
        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import androidx.cardview.widget.CardView;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.sgcreatives.a0003.Registration;

        import androidx.annotation.NonNull;
        import androidx.core.app.ActivityCompat;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;

        import java.util.HashMap;
        import java.util.Map;

public class RequiredInformation extends AppCompatActivity {

    ImageView top_curve;
    EditText email, phone;
    TextView email_text, password_text, login_title;
    TextView logo;
    CardView register_card;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    FirebaseAuth fauth = FirebaseAuth.getInstance();
    String userId;
    DatabaseReference reference;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required_information);
        top_curve = findViewById(R.id.top_curve);

        email = findViewById(R.id.email);
        email_text = findViewById(R.id.email_text);
        phone = findViewById(R.id.password);
        password_text = findViewById(R.id.password_text);
        logo = findViewById(R.id.logo);
        login_title = findViewById(R.id.registration_title);
        register_card = findViewById(R.id.register_card);
        userId = fauth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Contacts").child(userId);
        ActivityCompat.requestPermissions(RequiredInformation.this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
        Animation top_curve_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        top_curve.startAnimation(top_curve_anim);

        Animation editText_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.edittext_anim);

        email.startAnimation(editText_anim);
        phone.startAnimation(editText_anim);

        Animation field_name_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.field_name_anim);

        email_text.startAnimation(field_name_anim);
        password_text.startAnimation(field_name_anim);
        logo.startAnimation(field_name_anim);
        login_title.startAnimation(field_name_anim);

        Animation center_reveal_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.center_reveal_anim);
        register_card.startAnimation(center_reveal_anim);

        Animation new_user_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down_top);


    }
    public void submitButton(View view)
    {
        String mEmail = email.getText().toString();
        String rPassword = phone.getText().toString();
        userId = fauth.getCurrentUser().getUid();
        String id = reference.push().getKey();
        //DocumentReference documentReference = fstore.collection("users").document(userId);
       // Map<String,Object> user = new HashMap<>();
        Contact contact = new Contact();
        contact.name = mEmail;
        contact.phoneNumber = rPassword;
        //user.put("contact-email", mEmail);
       // user.put("contact-phone", rPassword);
       // documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
          //  @Override
          //  public void onSuccess(Void aVoid) {
       //         Log.d("TAG","on Success: User Profile is Created for"+userId);
           // }
        //});
        reference.child(id).setValue(contact).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(RequiredInformation.this,"succes",Toast.LENGTH_SHORT).show();
                }
            }
        });
        startActivity(new Intent(getApplicationContext(), NavigationPage.class));
    }

}
