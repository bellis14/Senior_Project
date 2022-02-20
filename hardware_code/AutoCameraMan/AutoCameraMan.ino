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
int panAngle = 0;
int tiltAngle = 0;
int angleStep = 5;
int angleMin = 0;
int angleMax = 180;
 
void setup() {
  Serial.begin(115200);
  SerialBT.begin("AutoCameraMan"); //Bluetooth device name
  tiltServo.attach(tiltServoPin); 
  panServo.attach(panServoPin);
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
  delay(20);
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
      tiltServo.write(tiltAngle);
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
      tiltServo.write(tiltAngle);
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
      panAngle += angleStep;
      panServo.write(panAngle);
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
      panAngle -= angleStep;
      panServo.write(panAngle);
    }
}
