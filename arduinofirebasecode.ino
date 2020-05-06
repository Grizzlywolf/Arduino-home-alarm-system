#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#define FIREBASE_HOST "alarmni-sustav.firebaseio.com" 
#define FIREBASE_AUTH "HTvvbksnVSx6JOKWeZQqxbM3WRDr23BksvhO81iz" 
#define ssid "HUAWEI-72813FCC"
#define password "565ebg357916"     

int inputPin = 13;               
int pirState = LOW;             
int val = 0;
int smokepin = A0;
int sensorThres = 400;
int wifiStatus; 
int loop1 = 52;
int loop2 = 52;

void setup() { 
 pinMode(smokepin, INPUT);
 pinMode(inputPin, INPUT);
 Serial.begin(9600); 
 WiFi.begin(ssid, password); 
 Serial.print("connecting"); 
 while (WiFi.status() != WL_CONNECTED) { 
   Serial.print("."); 
   delay(500); 
 } 
 Serial.println(); 
 Serial.print("connected: "); 
 Serial.println(WiFi.localIP()); 
 Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH); 
   
} 
 
void loop(){
  loop1++;
  loop2++;
  int smokeVal = analogRead(smokepin);
  String sStatus = Firebase.getString("SmokeSensor/status");
  if (sStatus.equals("OFF")){
    loop1 = 52;
    Firebase.setInt("/SmokeSensor/detection",0);
    }
    else if(sStatus.equals("ON")){
      if (loop1 >= 50){    
  if (smokeVal > sensorThres)
  {
     Serial.println(smokeVal);
     Serial.println("Smoke detected!");
     Firebase.setInt("/SmokeSensor/detection",1);
     loop1 = 0;
    }
    else{
      Serial.println(smokeVal);
      Serial.println("No smoke.");
      Firebase.setInt("/SmokeSensor/detection",0);
      loop1 = 52;
      }
    }
   }
   
  val = digitalRead(inputPin);  
  String mStatus = Firebase.getString("MotionSensor/status");
  if (mStatus.equals("OFF")){
    loop2 = 52;
    Firebase.setInt("/MotionSensor/detection",0);
    }
    else if(mStatus.equals("ON")){
      if (loop2 >= 50){
  if (val == HIGH) {            
    if (pirState == LOW) {  
      Serial.println("Motion detected!");
      Firebase.setInt("/MotionSensor/detection",1);
      pirState = HIGH;
      loop2 = 0;
    }
  } else{
    Serial.println("No motion.");
    loop2 = 52;
    if (pirState == HIGH){
      Firebase.setInt("/MotionSensor/detection",0);
      pirState = LOW;

      }
    }
  }
}
Serial.print("Loop1 Counter:");
Serial.println(loop1);
Serial.print("Loop2 Counter:");
Serial.println(loop2); 
delay(50);
}
