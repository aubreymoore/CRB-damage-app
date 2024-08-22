## CRB-damage-app

### Description

This Android application demonstrates live detection of coconut rhinoceros beetle damage using a YOLOv8 machine learning model. YOLOv8 (You Only Look Once version 8) is known for its real-time object detection capabilities.

The code and work done to integrate YOLOv8 for mobile use is from [https://github.com/surendramaran/YOLO](https://github.com/surendramaran/YOLO) and it is licensed under the [MIT License](https://github.com/surendramaran/YOLO/blob/main/YOLOv8-Object-Detector-Android-Tflite/LICENSE).

### Installation

Clone this repository and open the resulting folder with Android Studio. Then compile the app and save it on an attached Android device.

This worked on an Ubuntu 22.04 laptop on which **git** and **Android Studio 2024.1.1** where installed:
```
cd ~/Desktop
git clone http://github.com/aubreymoore/CRB-damage-app CRB-damage-app-test
android-studio CRB-damage-app-test
``` 
