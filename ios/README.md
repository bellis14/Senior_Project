# Setting up for Development 
We did the development for the iphone app on xcode which requires macOS. If you don't have a mac computer then you can either set up a virtual machine using virtualbox or VMware workstation. You can also attempt to dual boot macOS onto the computer you are currently using. You just need to make sure your laptop meets the requirements for this option. The other option is to use the mac lab down in the library. There is a way to configure virtualbox and VMware to perform more smoothly but when we attempted to use it the performance was really bad in terms of their being lag. You would try to move your mouse somewhere and it would take a solid five seconds to move. The drawback of using the mac lab is you can't use sudo commands to download stuff. If you need to download something you will have to find a work around that doesn't require sudo. As of now the IT department has revoked admin access which means you can't download xcode. It is currently downloaded on computer 4 in the mac lab if you want to use that one. That is the computer that was used for development.

## Steps for setting up development on mac computer
At the end of this setup you should be able to build and run the app on your iphone device. This means you are ready to begin adding the functionality of bluetooth. There is a link down below in the section labeled Bluetooth Resources. Go to the link in that section and hit the download button. This will download the code that apple offers to connect to bluetooth classic devices.  
* Download Xcode
* Download cocoapods. Use homebrew not gem.
  * Download Homebrew with these commands. You can find the same commands [here](https://docs.brew.sh/Installation#untar-anywhere)
    * mkdir homebrew && curl -L https://github.com/Homebrew/brew/tarball/master | tar xz --strip 1 -C homebrew
    * Add the path: export PATH="/Users/coolasacucumber/Downloads/homebrew/bin:$PATH" (You will have to run this command any time you want to run pod install and you closed the terminal)
    * The path above will be different for you. I decided to make the homebrew directory in the downloads folder. 
    * eval "$(homebrew/bin/brew shellenv)"
    * brew update --force --quiet
    * chmod -R go-w "$(brew --prefix)/share/zsh"
  * brew install cocoapods (This will take forever just fyi)
* Use cocoapods to import the dependencies in the Podfile
  * Clone the senior project repository into your workspace
  * Change into the ios directory of the repository.
  * run the command: pod install
* Setting up your iphone device
  * Do not use the iOS simulators if you are using M1 chip mac computers. Feel free to try it. If it works great!
  * Follow this [video](https://www.youtube.com/watch?v=bqh6YaMxgbE) setting up your iphone device. 
  * If after setting up the device you get this error when trying to run the app: 
  
     "Showing Recent Messages Failed to register bundle identifier: The app identifier "com.google.mlkit.quickstart.VisionExample" cannot be registered to your development team because it is not available. Change your bundle identifier to a unique string to try again." 
  
     then you need to go into the targets and select the Signings and Capabilities tab and change the Build Identifier from com.google.mlkit.quickstart.VisionExample to something different. I changed it to com.google.mlkit.quickstart.VisionExample222 and hit the try again button. After that it worked for me and then I was able to finish setting up the iphone device by going into the iphones settings >general>VPN & Device Management and then selecting the developer app as was mentioned in the above video. After this last step you should be able to run the app successfully.

## Debugging for setting up development on mac lab computers
https://alexandernadein.medium.com/undefined-symbols-and-where-to-find-them-b98f16f5f805
https://githubhot.com/repo/googlesamples/mlkit/issues/398
https://developer.apple.com/forums/thread/130493
https://developer.apple.com/forums/thread/130493

## Setting up virtual machine
* [iOS development setup using a virtual machine on VMware workstation](https://www.youtube.com/watch?v=-5FpROxjHsw)

# Bluetooth Resources
* Download [this](https://developer.apple.com/documentation/corebluetooth/using_core_bluetooth_classic). Its apples code for connecting bluetooth devices.

# ML Kit Vision Quickstart Sample App

## Introduction

This ML Kit Vision iOS Quickstart app demonstrates how to use and integrate various vision based ML Kit features into your app.

## Feature List

Features that are included in this demo app:
* [Object Detection](https://developers.google.com/ml-kit/vision/object-detection/ios) - Detect, track, and classify objects in real time and static images
* [Custom Object Detection (Birds)](https://developers.google.com/ml-kit/vision/object-detection/custom-models/ios) - Detect, track, and classify birds with a custom TensorFlow Lite model
* [Face Detection](https://developers.google.com/ml-kit/vision/face-detection/ios) - Detect faces in real time and static images
* [Text Recognition](https://developers.google.com/ml-kit/vision/text-recognition/v2/ios) - Recognize text in real time and static images
* [Barcode Scanning ](https://developers.google.com/ml-kit/vision/barcode-scanning/ios)- Scan barcodes in real time and static images
* [Image Labeling](https://developers.google.com/ml-kit/vision/image-labeling/ios) - Label images in real time and static images
* [Custom Image Labeling (Birds)](https://developers.google.com/ml-kit/vision/image-labeling/custom-models/ios) - Label images of birds with a custom TensorFlow Lite model
* [Pose Detection](https://developers.google.com/ml-kit/vision/pose-detection/ios) - Detect the position of the human body in real time.
* [Selfie Segmentation](https://developers.google.com/ml-kit/vision/selfie-segmentation/ios) - Segment people from the background in real time.

## Getting Started

* Run the sample code on your iOS device or simulator
* Try extending the code to add new features and functionality

## How to use the app

This app supports two usage scenarios: Live Camera mode and Static Image mode.

### Static Image Scenario
When the app is first opened, you will begin in Static Image mode. Use the carousel to select a specific API and hit “detect” to exercise the API on the preloaded images. In order to add your own images from the camera roll, select the camera roll icon on the top of the screen.

### Live Camera Scenario
This mode uses the camera preview as input instead of static images. In order to enter this mode, select the video camera icon in the top right corner of the screen.

## Support
-------

- [Stack Overflow](https://stackoverflow.com/questions/tagged/google-mlkit)

License
-------

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
