# Project Requirements:

## General Requirements:
### Primary - 
* Phone mount tripod attachment with motorized pan and tilt capabilities
* The mount receives signals (via bluetooth) to control the the pan and tilt angles
* A mobile app uses image recognition to generate these angles 
* The app keeps a detected object in frame via physical pan and tilt adjustments

### Secondary -
* Phone mount uses 4 or more integrated sound sensors/microphones to detect positional sound
* App has a sound mode that moves the phone in the direction of a desired sound
* The App uses voice recognition to prioritize phone's position


## Hardware Requirements:
### Primary -
* Motors receive a signal from controller (Arduino) to change angle
* Motor sound does not significantly disrupt phone audio recording
* Motors can rotate mount with phone (0.4-1.0 lb) in quick and precise movements
* Tripod mount can tilt 90 degrees (up and down)
* Tripod mount can pan 180-360 (side to side)

### Secondary -
* Mount can record audio using integrated microphones

## Software Requirements (General):
### Primary - 
* App can record video
* App uses image recognition to identify key objects and tracks bounding vertices
* App uses bounding vertices of object and generates pan and tilt angles
* App connects via bluetooth to phone mount control board
* App can send pan and tilt angles to the mount controller


## Software Requirements (Object Tracking) -
### Primary -
* App can track a human face
* App can track a body

### Secondary - 
* App can learn a specific object/person/face to track
* App uses pose detection to predict future position of person to reduce latency

