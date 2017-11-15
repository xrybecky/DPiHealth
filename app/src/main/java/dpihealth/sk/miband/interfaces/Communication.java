package dpihealth.sk.miband.interfaces;

import android.bluetooth.BluetoothDevice;

import java.util.UUID;

/**
 * Created by rybec on 06.11.2017.
 */

public interface Communication {

    // services
    UUID UUID_MILI_SERVICE = UUID.fromString("0000fee0-0000-1000-8000-00805f9b34fb");
    UUID UUID_SERVICE_GENERIC_ACCESS = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    UUID UUID_SERVICE_VIBRATE = UUID.fromString("00001802-0000-1000-8000-00805f9b34fb");
    UUID UUID_SERVICE_HEART_RATE = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
    UUID UUID_SERVICE_002 = UUID.fromString("0000fee1-0000-1000-8000-00805f9b34fb");
    UUID UUID_SERVICE_003 = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");


    // characteristics
    UUID UUID_CHAR_pair = UUID.fromString("0000ff0f-0000-1000-8000-00805f9b34fb");
    UUID UUID_CHAR_CONTROL_POINT = UUID.fromString("0000ff05-0000-1000-8000-00805f9b34fb");
    UUID UUID_CHAR_REALTIME_STEPS = UUID.fromString("0000ff06-0000-1000-8000-00805f9b34fb");
    UUID UUID_CHAR_ACTIVITY = UUID.fromString("0000ff07-0000-1000-8000-00805f9b34fb");
    UUID UUID_CHAR_LE_PARAMS = UUID.fromString("0000ff09-0000-1000-8000-00805f9b34fb");
    UUID UUID_CHAR_DEVICE_NAME = UUID.fromString("0000ff02-0000-1000-8000-00805f9b34fb");
    UUID UUID_CHAR_BATTERY = UUID.fromString("0000ff0c-0000-1000-8000-00805f9b34fb");
    UUID UUID_CHAR_HEART_RATE_MEASUREMENT = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");

    String INTER_ACTIVITY_DEVICE_BUNDLE_KEY = "sk.dpihealth.devices.CONNECTING_DEVICE";

    public void onStepsUpdate(int steps);
    public void onDeviceConnectingFailed(BluetoothDevice device);
    public void onDeviceConnected(BluetoothDevice device);
    public void onDeviceDisconnected(BluetoothDevice device);
}
