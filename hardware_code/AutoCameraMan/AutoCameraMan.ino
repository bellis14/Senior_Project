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
//int tiltAngle = 0;
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
  panRight();
  panLeft();
  //tiltUp();
  //tiltDown();
  
  //Send data
  /*if (Serial.available()) {
    SerialBT.write(Serial.read());
  }*/
  
  // Receive data
  if (SerialBT.available()) {
    //Serial.write(SerialBT.read());
    char getstr = SerialBT.read();
    switch(getstr) {
      case '0': Serial.println("panRight"); break;
      case '1': Serial.println("panLeft"); break;
      case '2': Serial.println("tiltUp"); break;
      case '3': Serial.println("tiltDown"); break;
      default:  break;
    }
  }
  delay(20);
}


void tiltUp()
{
   for(int tiltAngle = 0; tiltAngle <= angleMax; tiltAngle +=angleStep) {
        tiltServo.write(tiltAngle);
        //Serial.println(angle);
        delay(20);
    }
}

void tiltDown()
{
  for(int tiltAngle = 180; tiltAngle >= angleMin; tiltAngle -=angleStep) {
        tiltServo.write(tiltAngle);
        //Serial.println(angle);
        delay(20);
    }
}

void panRight()
{
   for(int panAngle = 0; panAngle <= angleMax; panAngle +=angleStep) {
        panServo.write(panAngle);
        delay(20);
    }
}

void panLeft()
{
  for(int panAngle = 180; panAngle >= angleMin; panAngle -=angleStep) {
        panServo.write(panAngle);
        delay(20);
    }
}
