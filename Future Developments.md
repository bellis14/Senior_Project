# Potential Future Developments

## User Interface
* UI has 2 modes you select from at startup; face (<6 ft away) and pose (>6 ft away)

## Optimization
* Research [hardware configuration](https://developers.google.com/android/reference/com/google/mlkit/vision/pose/PoseDetectorOptionsBase) for possible performance improvements
* Reduce resolution of image being processed for face/pose detection (while maintaining resolution for recording)
* **Create seperate thread to enable recording without drastically decreasing performance**

## Additional Features
* On device microphones that enable device to [look towards the loudest sound](https://www.youtube.com/watch?v=CgrFabQvES4) in the event the camera fails to find a person over a period of time (allows user to clap loudly or shout "HEY!" for the camera to find it)
* Gesture/voice controls (ie. hand gestures to start/stop recording)
* Create the same app but on iOS for iPhones. The repository includes the documentation for setting up and building the ios code on xcode but no additional features like bluetooth have been added to it, so it's a blank canvas really. 
* Have a device to a person carries on them and the camera mount will follow that device around wherever it goes.

