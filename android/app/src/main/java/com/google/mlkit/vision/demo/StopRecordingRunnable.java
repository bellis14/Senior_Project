package com.google.mlkit.vision.demo;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.google.mlkit.vision.demo.CameraSource;
import com.google.mlkit.vision.demo.CameraSourcePreview;

import java.io.File;

public class StopRecordingRunnable implements Runnable {
    private MediaRecorder recorder;
    private CameraSource cameraSource;

    public StopRecordingRunnable(MediaRecorder recorder2, CameraSource cameraSource) {
        Log.i("Media", "Stop in Cos");
        this.cameraSource = cameraSource;
        // TODO Auto-generated constructor stub
        try {
            this.recorder = recorder2;

        } catch (Exception e) {
            Log.i("Media", "Stop out  Cos" + e.getMessage());
        }

    }

    public void run() {
        Log.i("Media", "Stop in RUN");
        //Set the record flag to 0 to signal the thread to stop saving frames
        cameraSource.ReleaseFlag();


        //Use the frames saved in "sdcard/DCIM/Camera/saved_images" and convert to mp4
        cameraSource.CreateVideo("sdcard/DCIM/Camera/saved_images"); //Path hardcoded for now
        recorder.stop();
        recorder.reset();
        Log.i("Media", "Stop out of RUN");

    }
}