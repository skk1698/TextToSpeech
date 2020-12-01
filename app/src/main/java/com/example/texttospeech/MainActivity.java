package com.example.texttospeech;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private EditText textToConvertIntoSpeech;
    private String toSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Project : ","Text To Speech Converter");
        textToConvertIntoSpeech = findViewById(R.id.text_to_convert_into_speech);
        Button textToSpeechConverter = findViewById(R.id.text_to_speech_converter);
        Button stopButton = findViewById(R.id.stop_button);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.SUCCESS) {
                    toSpeak = "Text To Speech engine Initialization failed!";
                    speak();

                }
            }
        });

        textToSpeechConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSpeak = textToConvertIntoSpeech.getText().toString();
                if (toSpeak.isEmpty()) {
                    toSpeak = "No Text Availabe, Please Enter Text First!";
                }
                speak();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textToSpeech != null) {
                    textToSpeech.stop();
                }
            }
        });
    }

    private void speak() {
        SeekBar seekBarPitch = findViewById(R.id.seek_bar_pitch);
        SeekBar seekBarSpeed = findViewById(R.id.seek_bar_speed);
        float pitch = (float) seekBarPitch.getProgress() / 50;
        if (pitch < 0.1) {
            pitch = 0.1f;
        }
        float speed = (float) seekBarSpeed.getProgress() / 50;
        if (speed < 0.1) {
            speed = 0.1f;
        }

        textToSpeech.setPitch(pitch);
        textToSpeech.setSpeechRate(speed);

        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
}

