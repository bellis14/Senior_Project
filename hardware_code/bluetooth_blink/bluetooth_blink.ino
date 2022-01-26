//www.elegoo.com

//#define LED 13    //Define 13 pin for LED
//bool state = LOW; //The initial state of the function is defined as a low level
//char getstr;      //Defines a function that receives the Bluetooth character


#include <Servo.h>
#include <SoftwareSerial.h>
#define servoPin 3
SoftwareSerial mySerial(1, 0);
Servo servo;
int servoPos = 0;
char Incoming_value = 0;                //Variable for storing Incoming_value

void setup() 
{
  Serial.begin(9600);         //Sets the data rate in bits per second (baud) for serial data transmission
  servo.attach(servoPin);
  Serial.println("Enter AT commands:");
  mySerial.begin(9600);
  //pinMode(13, OUTPUT);        //Sets digital pin 13 as output pin
}
void loop()
{
  /*if(Serial.available() > 0)  
  {
    Incoming_value = Serial.read();      //Read the incoming data and store it into variable Incoming_value
    Serial.print(Incoming_value);        //Print Value of Incoming_value in Serial monitor
    Serial.print("\n");        //New line 
    if(Incoming_value == '1')            //Checks whether value of Incoming_value is equal to 1 
    {
      //digitalWrite(13, HIGH);  //If value is 1 then LED turns ON
      servoLeft();
    }
    else if(Incoming_value == '0')       //Checks whether value of Incoming_value is equal to 0
    {
      //digitalWrite(13, LOW);   //If value is 0 then LED turns OFF
      servoRight();
    }
  }  */  
  if (mySerial.available())
    Serial.write(mySerial.read());
  if (Serial.available())
    mySerial.write(Serial.read()); 
 
}        

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
}
/*void setup() {
  pinMode(LED, OUTPUT);
  Serial.begin(9600);
}

//Control LED sub function
void stateChange() {
  state = !state; 
  digitalWrite(LED, state);  
}

void loop() {
  if(Serial.available())
  {
    //The Bluetooth serial port to receive the data in the function
    getstr = Serial.read();
    Serial.print(getstr);
    if(getstr == 'a'){
      stateChange();
    }
  }
}*/
