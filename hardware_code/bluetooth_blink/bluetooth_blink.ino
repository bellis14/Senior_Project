//www.elegoo.com

#include "BluetoothSerial.h"
#include <Servo_ESP32.h>

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

BluetoothSerial SerialBT;
static const int servoPin = 18; //printed G14 on the board
Servo_ESP32 servo1;
int angle =0;
int angleStep = 5;
int angleMin =0;
int angleMax = 180;
 
void setup() {
  Serial.begin(115200);
  SerialBT.begin("AutoCameraMan Base"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
  servo1.attach(servoPin);
}
 
void loop() {
  servoRight();
  delay(2000);
  servoLeft();
  delay(2000);
  
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
        Serial.println(angle);
        delay(20);
    }
}

void servoLeft()
{
  for(int angle = 180; angle >= angleMin; angle -=angleStep) {
        servo1.write(angle);
        Serial.println(angle);
        delay(20);
    }
}
