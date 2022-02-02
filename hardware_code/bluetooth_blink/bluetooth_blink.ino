//www.elegoo.com

#include "BluetoothSerial.h"
 
#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

BluetoothSerial SerialBT;
 
void setup() {
  Serial.begin(115200);
  //Serial.begin(9600);
  SerialBT.begin("AutoCameraMan"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
}
 
void loop() {
  if (Serial.available()) {
    SerialBT.write(Serial.read());
  }
  if (SerialBT.available()) {
    Serial.write(SerialBT.read());
  }
  delay(20);
}

/*
void servoRight()
{
  for (servoPos = 55; servoPos <= 125; servoPos++)
   {
     servo.write(servoPos);
     delay(15);
   }
}

void servoLeft()
{
  for (servoPos = 125; servoPos >= 55; servoPos--)
   {
     servo.write(servoPos);
     delay(15);
   }
}*/
