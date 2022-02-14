//www.elegoo.com

#include "BluetoothSerial.h"
#include <Servo_ESP32.h>

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

BluetoothSerial SerialBT;
static const int servoPin = 18; //printed 18 on the board
static const int panServoPin = 5; //printed 5 on the board
Servo_ESP32 servo1;
Servo_ESP32 servo2;
int panAngle = 0;
int angle = 0;
int angleStep = 5;
int angleMin =0;
int angleMax = 180;
 
void setup() {
  Serial.begin(115200);
  SerialBT.begin("AutoCameraMan"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
  servo1.attach(servoPin);
  servo2.attach(panServoPin);
}
 
void loop() {
  servoRight();
  servoLeft();
  
  if (Serial.available()) {
    SerialBT.write(Serial.read());
  }
  if (SerialBT.available()) {
    Serial.write(SerialBT.read());
  }
  delay(20);
}


void servoRight()
{
   for(int angle = 0; angle <= angleMax; angle +=angleStep) {
        servo1.write(angle);
        //Serial.println(angle);
        delay(20);
    }
}

void servoLeft()
{
  for(int angle = 180; angle >= angleMin; angle -=angleStep) {
        servo1.write(angle);
        //Serial.println(angle);
        delay(20);
    }
}

void panRight()
{
   for(int panAngle = 0; panAngle <= angleMax; panAngle +=angleStep) {
        servo2.write(panAngle);
        delay(20);
    }
}

void panLeft()
{
  for(int panAngle = 180; panAngle >= angleMin; panAngle -=angleStep) {
        servo2.write(panAngle);
        delay(20);
    }
}
