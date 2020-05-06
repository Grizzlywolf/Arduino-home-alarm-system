package com.example.firebasenovaimplementacija;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.pusher.pushnotifications.PushNotifications;



public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    final DatabaseReference smokesensorstatus = myRef.child("SmokeSensor").child("status");
    final DatabaseReference smokesensor = myRef.child("SmokeSensor").child("detection");
    final DatabaseReference motionsensorstatus = myRef.child("MotionSensor").child("status");
    final DatabaseReference motionsensor = myRef.child("MotionSensor").child("detection");

    Button smokebutton;
    Button motionbutton;
    TextView firetext;
    TextView motiontext;
    ImageView fireimage;
    ImageView motionimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushNotifications.start(getApplicationContext(), "da14c153-e2fa-4db8-9b1b-d4931cc98d22");
        PushNotifications.addDeviceInterest("hello");

        smokebutton = findViewById(R.id.button);
        motionbutton = findViewById(R.id.button2);
        firetext = findViewById(R.id.textView3);
        motiontext = findViewById(R.id.textView4);
        fireimage = findViewById(R.id.imageView);
        motionimage = findViewById(R.id.imageView2);


        smokesensorstatus.addValueEventListener(new ValueEventListener()
        {
            @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String value = dataSnapshot.getValue(String.class);
                smokebutton.setText(value);
        }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        smokesensor.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                if (value == 1){
                    firetext.setVisibility(View.VISIBLE);
                    fireimage.setVisibility(View.VISIBLE);
                }
                else{
                    firetext.setVisibility(View.INVISIBLE);
                    fireimage.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        motionsensorstatus.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                motionbutton.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        motionsensor.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                if (value == 1){
                    motiontext.setVisibility(View.VISIBLE);
                    motionimage.setVisibility(View.VISIBLE);
                }
                else{
                    motiontext.setVisibility(View.INVISIBLE);
                    motionimage.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        smokebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = smokebutton.getText().toString();
                if(buttonText.equals("ON")){
smokesensorstatus.setValue("OFF");
            }
                else{
                    smokesensorstatus.setValue("ON");
                }
        }
        });
        motionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = motionbutton.getText().toString();
                if(buttonText.equals("ON")){
                    motionsensorstatus.setValue("OFF");
                }
                else{
                    motionsensorstatus.setValue("ON");
                }
            }


        });

    }

}
