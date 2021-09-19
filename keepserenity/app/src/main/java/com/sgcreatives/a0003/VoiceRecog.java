package com.sgcreatives.a0003;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.speech.RecognizerIntent;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;


public class VoiceRecog extends AppCompatActivity
{

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    int MY_PERMISSIONS_RECORD_AUDIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recog);

        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }

    private void startVoiceInput() {
        if (checkPermissions())
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {

            }

        }
        else
        {
            requestPermissions();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                    System.out.println(result);
                }
                if (mVoiceInputTv.getText().toString().equals("help")) {
                    Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(newIntent);
                }
                break;
            }

        }
    }

    public boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }
    public void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
    }
}
