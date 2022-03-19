package com.google.mlkit.vision.demo;

import static com.arthenica.mobileffmpeg.FFmpeg.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.FFmpeg.RETURN_CODE_SUCCESS;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.google.mlkit.vision.demo.CameraSource;
import com.google.mlkit.vision.demo.CameraSourcePreview;

import java.io.File;
import java.util.Random;

public class StopRecordingRunnable implements Runnable {
    private MediaRecorder recorder;
    private CameraSource cameraSource;
    private Context context;

    public StopRecordingRunnable(MediaRecorder recorder2, CameraSource cameraSource, Context context) {
        Log.i("Media", "Stop in Cos");
        this.cameraSource = cameraSource;
        this.context = context;
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

        recorder.stop();
        recorder.reset();
        CreateVideo();
        Log.i("Media", "Stop out of RUN");

    }
    public void CreateVideo(){
        Log.d("byte", "Image Directory " + cameraSource.fileName);

        cameraSource.n = 0;
        //take photos in file and convert to mp4
        int rc = FFmpeg.execute("-y -framerate " + cameraSource.frameRate + " -start_number 1 -i sdcard/DCIM/Camera/saved_images/img" + cameraSource.num +"_" +"%d.jpg -i " + cameraSource.fileName + " sdcard/DCIM/Camera/final" + cameraSource.num + ".mp4");

        if (rc == RETURN_CODE_SUCCESS) {
            Log.d("ffmpeg", "FFmpeg Success");

            //Clear the folder of images after the video is created.
            File myDir = new File("sdcard/DCIM/Camera/saved_images");
            deleteDirectory(myDir);
        } else if (rc == RETURN_CODE_CANCEL) {
            Log.d("ffmpeg", "FFmpeg Cancelled");
        } else {
            Log.d(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", rc));
        }


    }

    public static boolean deleteDirectory(File path) {
// TODO Auto-generated method stub
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }


}