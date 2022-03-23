# ML Kit Vision Quickstart Sample App

## Introduction

This ML Kit Quickstart app demonstrates how to use and integrate various vision based ML Kit features into your app.

## Feature List

Features that are included in this Quickstart app:
* [Object Detection](https://developers.google.com/ml-kit/vision/object-detection/android) - Detect, track, and classify objects in real time and static images
* [Face Detection](https://developers.google.com/ml-kit/vision/face-detection/android) - Detect faces in real time and static images
* [Text Recognition](https://developers.google.com/ml-kit/vision/text-recognition/android) - Recognize text in real time and static images
* [Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning/android)  - Scan barcodes in real time and static images
* [Image Labeling](https://developers.google.com/ml-kit/vision/image-labeling/android) - Label images in real time and static images
* [Custom Image Labeling - Birds](https://developers.google.com/ml-kit/vision/image-labeling/custom-models/android) - Label images of birds with a custom TensorFlow Lite model.
* [Pose Detection](https://developers.google.com/ml-kit/vision/pose-detection/android) - Detect the position of the human body in real time.
* [Selfie Segmentation](https://developers.google.com/ml-kit/vision/selfie-segmentation/android) - Segment people from the background in real time.

<img src="../screenshots/quickstart-picker.png" width="220"/> <img src="../screenshots/quickstart-image-labeling.png" width="220"/> <img src="../screenshots/quickstart-object-detection.png" width="220"/> <img src="../screenshots/quickstart-pose-detection.png" width="220"/>

## Getting Started

* Run the sample code on your Android device or emulator
* Try extending the code to add new features and functionality

## How to use the app

This app supports three usage scenarios: Live Camera, Static Image, and CameraX enabled live camera.

### Live Camera scenario
It uses the camera preview as input and contains these API workflows: Object detection & tracking, Face Detection, Text Recognition, Barcode Scanning, Image Labeling, and Pose Detection. There's also a settings page that allows you to configure several options:
* Camera
    * Preview size - Specify the preview size of rear/front camera manually (Default size is chosen appropriately based on screen size)
    * Enable live viewport - Toggle between blocking camera preview by API processing and result rendering or not
* Object detection / Custom Object Detection
    * Enable multiple objects -- Enable multiple objects to be detected at once
    * Enable classification -- Enable classification for each detected object
* Face Detection
    * Landmark mode -- Toggle between showing no or all facial landmarks
    * Contour mode -- Toggle between showing no or all contours
    * Classification mode -- Toggle between showing no or all classifications (smiling, eyes open/closed)
    * Performance mode -- Toggle between two operating modes (Fast or Accurate)
    * Face tracking -- Enable or disable face tracking
    * Minimum face size -- Choose the proportion of the head width to the image width
* Pose Detection
    * Performance mode -- Allows you to switch between "Fast" and "Accurate" operation mode
    * Show in-frame likelihood -- Displays InFrameLikelihood score for each landmark
    * Visualize z value -- Uses different colors to indicate z difference (red: smaller z, blue: larger z)
    * Rescale z value for visualization -- Maps the smallest z value to the most red and the largest z value to the most blue. This makes z difference more obvious
    * Run classification -- Classify squat and pushup poses. Count reps in streaming mode.
* Selfie Segmentation
    * Enable raw size mask -- Asks the segmenter to return the raw size mask which matches the model output size.

### Static Image scenario
The static image scenario is identical to the live camera scenario, but instead relies on images fed into the app through the gallery.

### CameraX Live Preview scenario
The CameraX live preview scenario is very similar to the native live camera scenario, but instead relies on CameraX live preview. Note: CameraX is only supported on API level 21+.

## Support

* [Documentation](https://developers.google.com/ml-kit/guides)
* [API Reference](https://developers.google.com/ml-kit/reference/android)
* [Stack Overflow](https://stackoverflow.com/questions/tagged/google-mlkit)

## License

Copyright 2020 Google, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.

# Video / Audio Recording Resources
One major pillar of this application is the ability to record a video while the application is tracking a face.
The purpose of writing this is to document how that was accomplished as well as the various issues that were encounter along the way so as to help future developers better understand the task at hand.

## The Goal
The goal when we set out was to create an application that could track a face or person while recording video in the background. Below will outline the various ways the internet has attempted to solve this issues as well as the solution that was implemented here. 

## Standard Video Recording
https://developer.android.com/training/camera/videobasics This is a link to Android official documentation that outlines the standard way to record video. This does not work in this specific application and the reason this does not is because it hijacks the camera from the core functionality of the application. It essentially switches to a new application temporarily pausing everything else.

## MediaRecorder Solution
https://developer.android.com/guide/topics/media/mediarecorder This link is androids second more involved way of recording video. This solution works for audio recording because no other part of the code is using the microphone, so complete control of the microphone does not affect the rest of the application. This however does not work for video recording for a similar reason as the standard recording solution. Trying to record video with the media recorder also steal the camera from the core application functionality eliminating its ability to preform any type of image recognition. 

## Present Solution
The current solution has been to use the MediaRecorder object to record sound on a background thread while at the same time saving frames already being captured by the camera for image recognition. This solution involves two threaded classes that interface to Runnable: StopRecordingRunnable and StartRecordingRunnable. These handle the starting and stopping of the audio recording as well as the creation of the video. 
The creation of the video involves taking the frames that have been saved to a unique directory within the phones storage, and taking the most recent audio recording and putting them together using a library called FFmpeg. Documentation and links are below. The following objects and methods are used in this process and can be studied to gain a better understanding of how this whole process works. 

### LivePreviewActivity
-	recordButton.setOnClickListener
This is the logic that handels the record button toggling from white to red. As well as kick off the video recording functionality by creating a StartRecordingRunnable thread and starting it.

### StartRecordingRunnable
-	This object is responsible for setting a recording flag that exists in CameraSource, and setting up the media Recorder object for audio and starting it. 
### StopRecordingRunnable
-	This object is responsible for bringing it all together. It releases the recording flag to signal CameraSource to stop saving frames. It releases the mediaRecorder to stop recording audio, and it takes the frames and audio and creates a binary executable that is used by FFmpeg to create a final video. It also cleans up memory by deleting all of the frames after the video is created.
### CameraSource
-	This object is a major component of the core functionality of the application. It is responsible for setting up the camera for use by the application. This is why the camera frames are saved here. 
-	onPreviewFrame This method is a part of the camera hardware and is called every time the camera has a new frame. This method checks to see if the recording flag is true and if it is it passes the frame in the form of a byte array to the SaveImage method. 
-	SaveImage this method takes a byte array and converts it into a YuvImage. The YuvImage is then converted into a bitmap, then creates a matrix to rotate the bitmap 90 degrees, then the bitmap is converted into a jpg to be saved. There is probably a better way to do this but this is what worked. 
-	In CameraSource ther is a global variable numFrames, this variable is important because it keeps track of the number of frames that are saved during a recording session. This is needed to save the frames in numerical order as well as to calculate the frame rate to be used in video creation later.
### FFmpeg
-	This library is added in the manifests file and is used to create a video with the file of frames that are saved when the record button is pushed. 
-	Below are useful links for using FFmpeg
-	https://www.geeksforgeeks.org/how-to-use-ffmpeg-in-android-with-example/
-	https://github.com/tanersener/mobile-ffmpeg
-	https://codingpoint.tech/how-to-use-ffmpeg-in-android/
 

* Solution to use multiple camera streams simultaneously
* https://developer.android.com/training/camera2/multiple-camera-streams-simultaneously
