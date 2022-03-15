# Setting up for Development 
If you don't have a mac computer then you can either set up a virtual machine using virtualbox or VMware workstation. The other option is to use the mac lab down in the library. There is a way to configure virtualbox and VMware to perform more smoothly but when we attempted to use it the performance was really bad in terms of their being lag. You would try to move your mouse somewhere and it would take a solid five seconds to move. Using the mac lab has been the best option so far. The drawback of using the mac lab is you can't use sudo commands to download stuff. If you need to download something you will have to find a work around that doesn't require sudo. 

## Steps for setting up development on mac lab computer
* Download Xcode
* Download cocoapods. Use homebrew not gem.
  * Download Homebrew with these commands. You can find the same commands [here](https://docs.brew.sh/Installation#untar-anywhere)
    * mkdir homebrew && curl -L https://github.com/Homebrew/brew/tarball/master | tar xz --strip 1 -C homebrew
    * Add the path: export PATH="/Users/coolasacucumber/Downloads/homebrew/bin:$PATH" 
    * The path above will be different for you. I decided to make the homebrew directory in the downloads folder. 
    * eval "$(homebrew/bin/brew shellenv)"
    * brew update --force --quiet
    * chmod -R go-w "$(brew --prefix)/share/zsh"
  * brew install cocoapods
* Use cocoapods to import the dependencies in the Podfile
  * Change into the ios directory.'
  * run the command: pod install

## Setting up virtual machine
* [iOS development setup using a virtual machine on VMware workstation](https://www.youtube.com/watch?v=-5FpROxjHsw)

##Debugging
https://alexandernadein.medium.com/undefined-symbols-and-where-to-find-them-b98f16f5f805


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
