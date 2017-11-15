package dpihealth.sk.miband.handlers;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.betomaluje.miband.ActionCallback;
import com.betomaluje.miband.MiBand;
import com.betomaluje.miband.model.VibrationMode;

import dpihealth.sk.miband.MainActivity;
import dpihealth.sk.miband.interfaces.RequestCodes;

/**
 * Created by rybec on 30.10.2017.
 */

public class MessageHandler extends Handler {

    private MainActivity activity;

    public MessageHandler(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message msg) {

        switch (msg.what) {
            case RequestCodes.NEW_DEVICE_FOUND_MSG:

                if(msg.obj != null){
                    BluetoothDevice device = (BluetoothDevice)msg.obj;
                }

                break;
            case RequestCodes.NEW_DEVICE_CONNECTED_MSG:

                break;
            default:
                break;
        }

    }
}
