package com.google.mlkit.vision.demo;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {

    private static final String TAG = "BluetoothDeviceAdapter";
    private Context mContext;
    private int mResource;

    public BluetoothDeviceAdapter(Context context, int resource, ArrayList<BluetoothDevice> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the information from the device item
        BluetoothDevice device = getItem(position);
        String name = device.getName();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);

        // Format the information correctly
        String spacer = "                                                                    ";
        tvName.setText(name + spacer);

        return convertView;
    }
}

