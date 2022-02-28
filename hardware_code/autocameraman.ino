//For a better understanding of the bluetooth code go to https://randomnerdtutorials.com/esp32-bluetooth-classic-arduino-ide/

#include "BluetoothSerial.h"
#include <Servo_ESP32.h>
#define tiltServoPin 18
#define panServoPin 5
#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

BluetoothSerial SerialBT;
//static const int tiltServoPin = 18; //printed 18 on the board
//static const int panServoPin = 5; //printed 5 on the board
Servo_ESP32 tiltServo, panServo;
int panAngle = 90;
int prevPanAngle =90;
int tiltAngle = 0;
int prevTiltAngle = 0;
int angleStep = 1;
int angleMin = 0;
int angleMax = 180;
 
void setup() {
  Serial.begin(115200);
  SerialBT.begin("AutoCameraMan"); //Bluetooth device name
  tiltServo.attach(tiltServoPin); 
  panServo.attach(panServoPin);
  
  panServo.write(0);
  delay(1000);
  panServo.write(270);
  delay(1000);
  panServo.write(panAngle);
  delay(1000);
  
}
 
void loop() {
  //panRight();
  //panLeft();
  //tiltUp();
  //tiltDown();
  
  //Send data back to the app but be aware
  //nothing has been configured on the app
  //to receive or process data from the ESP32
  /*if (Serial.available()) {
    SerialBT.write(Serial.read());
  }*/
  
  // Receive data
  if (SerialBT.available()) {
    //Serial.write(SerialBT.read());
    char getstr = SerialBT.read();
    switch(getstr) {
      case '0': panRight(); break;
      case '1': panLeft();break;
      case '2': tiltUp(); break;
      case '3': tiltDown(); break;
      default:  break;
    }
  }
  if (prevPanAngle != panAngle){
  panServo.write(panAngle);
  Serial.println(panAngle);
  tiltServo.write(tiltAngle);
  }
}


void tiltUp()
{
   /*for(int tiltAngle = 0; tiltAngle <= angleMax; tiltAngle +=angleStep) {
        tiltServo.write(tiltAngle);
        //Serial.println(angle);
        delay(20);
    }*/

    //Going to need to find out what the sweet spot is for the angle range. Maybe 90
    Serial.println("tiltDown");
    if (tiltAngle < 180) {
      tiltAngle += angleStep;
    }
    
}

void tiltDown()
{
  /*for(int tiltAngle = 180; tiltAngle >= angleMin; tiltAngle -=angleStep) {
        tiltServo.write(tiltAngle);
        //Serial.println(angle);
        delay(20);
    }*/

    Serial.println("tiltDown");
    if (tiltAngle > 0) {
      tiltAngle -= angleStep;
    }
}

void panRight()
{
   /*for(int panAngle = 0; panAngle <= angleMax; panAngle +=angleStep) {
        panServo.write(panAngle);
        delay(20);
    }*/

    // Want to get a full 270 degrees out of pan
    Serial.println("panRight");
    if (panAngle < 180) {
      prevPanAngle = panAngle;
      panAngle += angleStep;
    }
}

void panLeft()
{
  /*for(int panAngle = 180; panAngle >= angleMin; panAngle -=angleStep) {
        panServo.write(panAngle);
        delay(20);
    }*/
    Serial.println("panLeft");
    if (panAngle > 0) {
      prevPanAngle = panAngle;
      panAngle -= angleStep;
    }
}
