package com.google.mlkit.vision.demo;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.google.mlkit.vision.demo.CameraSource;
import com.google.mlkit.vision.demo.CameraSourcePreview;

import java.io.File;

public class StopRecordingRunnable implements Runnable {
    private MediaRecorder recorder;

    public StopRecordingRunnable(MediaRecorder recorder2) {
        Log.i("Media", "Stop in Cos");
        // TODO Auto-generated constructor stub
        try {
            this.recorder = recorder2;

        } catch (Exception e) {
            Log.i("Media", "Stop out  Cos" + e.getMessage());
        }

    }

    public void run() {
        Log.i("Media", "Stop in RUN");
        recorder.stop();
        recorder.release();
        Log.i("Media", "Stop out of RUN");

    }
}