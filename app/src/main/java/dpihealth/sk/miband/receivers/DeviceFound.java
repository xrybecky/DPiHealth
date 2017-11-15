package dpihealth.sk.miband.receivers;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import dpihealth.sk.miband.MainActivity;
import dpihealth.sk.miband.R;
import dpihealth.sk.miband.interfaces.RequestCodes;

public class DeviceFound extends BroadcastReceiver {

    private Handler handler;

    public DeviceFound(Handler handler){
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        if(device != null){
            Message mes = new Message();
            mes.what = RequestCodes.NEW_DEVICE_FOUND_MSG;
            mes.obj = device;

            handler.sendMessage(mes);
        }
    }
}
