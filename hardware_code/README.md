# Hardware code

## Description
* The AutoCameraMan arduino file is used to broadcoast bluetooth classic to an android phone. This code successfully connects to the AutoCameraMan app and will receive motor instructions when the app is set to facial or pose detection. When the instructions are received the code will function with good motor control.
* The BLE_Server file is used to broadcoast a bluetooth low energy signal. iPhone and Android can connect to both bluetooth low energy (BLE) and bluetooth classic. Bluetooth classic is easier to implement than BLE.   

## Research 
* [Bluetooth/ESP32 Research](https://docs.google.com/document/d/1fT-Hv9j815ZINsvS9MvsHoMjV4GRWzmnJF0-fi07Mzs/edit)
* [Servo DS3225 spec sheet](https://dsservo.com/en/d_file/RDS3225%20datasheet.pdf)
* [Wiring Diagram of board and servos. Scroll down till you see it](https://create.arduino.cc/projecthub/zee9309/presentation-slide-switcher-bot-36b8f6)
