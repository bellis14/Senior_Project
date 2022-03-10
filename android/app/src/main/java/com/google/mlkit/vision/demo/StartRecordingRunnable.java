package com.google.mlkit.vision.demo;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.google.mlkit.vision.demo.kotlin.LivePreviewActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class StartRecordingRunnable implements Runnable {
    private MediaRecorder recorder;
    private CameraSource cameraSource;
    private CameraSourcePreview preview;
    private String selectedModel;
    private GraphicOverlay graphicOverlay;
    private Camera camera;

    public StartRecordingRunnable( MediaRecorder r , CameraSourcePreview preview, CameraSource cameraSource, GraphicOverlay graphicOverlay) {
        Log.i("Media", "start cons");
        this.recorder = r;
        this.cameraSource = cameraSource;
        this.preview = preview;
        this.graphicOverlay = graphicOverlay;
        this.camera = cameraSource.getCamera();

    }

    public void run() {
        // TODO Auto-generated method stub
        Log.i("Media", "IN RUN start Recording");
        //Set flag to 1 to start saving frames into directory "sdcard/DCIM/Camera/saved_images"
        cameraSource.SetFlag();
        try {
            startRecording();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startRecording() throws IOException {
        Log.i("Media", "IN Method start Recording");
        Log.i("Media", "create variable");

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        Log.i("Media", "1");
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        Log.i("Media", "2");
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        Log.i("Media", "3");
        recorder.setOutputFile("/sdcard/DCIM/Camera/AA_" + System.currentTimeMillis() + ".mp4");
        Log.i("Media", "/sdcard/DCIM/Camera/AA_" + System.currentTimeMillis() + ".mp4");

        try{
            Log.i("Media", "prepare");
            recorder.prepare();
            Log.i("Media", "before");
            recorder.start();
            Log.i("Media", "after");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filePath, "MediaRecorderSample");

        if(!file.exists())
            file.mkdirs();
        Log.i("***********************", filePath);
        //return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4" );
        return ("/sdcard/DCIM/Camera/" + System.currentTimeMillis() + ".mp4");
    }

}
