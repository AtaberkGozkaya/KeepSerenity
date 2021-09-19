package com.sgcreatives.a0003;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    ImageView top_curve;
    EditText  email, password;
    TextView  email_text, password_text, login_title;
    TextView logo;
    CardView register_card;
    FirebaseAuth fauth = FirebaseAuth.getInstance();
    String userId;
    String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        top_curve = findViewById(R.id.top_curve);

        email = findViewById(R.id.email);
        email_text = findViewById(R.id.email_text);
        password = findViewById(R.id.password);
        password_text = findViewById(R.id.password_text);
        logo = findViewById(R.id.logo);
        login_title = findViewById(R.id.registration_title);
        register_card = findViewById(R.id.register_card);


        Animation top_curve_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        top_curve.startAnimation(top_curve_anim);

        Animation editText_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.edittext_anim);

        email.startAnimation(editText_anim);
        password.startAnimation(editText_anim);

        Animation field_name_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.field_name_anim);

        email_text.startAnimation(field_name_anim);
        password_text.startAnimation(field_name_anim);
        logo.startAnimation(field_name_anim);
        login_title.startAnimation(field_name_anim);

        Animation center_reveal_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.center_reveal_anim);
        register_card.startAnimation(center_reveal_anim);

        Animation new_user_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down_top);

    }
    public void registerButton(View view)
    {
        mEmail = email.getText().toString().trim();
        String rPassword = password.getText().toString().trim();
        if(TextUtils.isEmpty(mEmail))
        {
            email.setError("Email is required");
            return;
        }
        if(TextUtils.isEmpty(rPassword))
        {
            password.setError("Password is required");
            return;
        }
        if(password.length() < 6)
        {
            password.setError("Password must be more than 6 characters");
            return;
        }

        if(fauth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), NavigationPage.class));
            finish();
        }
        fauth.createUserWithEmailAndPassword(mEmail, rPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(Registration.this,"Register Clicked",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), RequiredInformation.class));
                }
                else
                {
                    Toast.makeText(Registration.this,"Error! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public String getmEmail()
    {
        return mEmail;
    }
    public String getUserId()
    {
        userId = fauth.getCurrentUser().getUid();
        return userId;
    }

}
