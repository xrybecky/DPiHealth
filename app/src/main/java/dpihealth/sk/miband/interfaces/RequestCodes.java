package dpihealth.sk.miband.interfaces;

/**
 * Created by rybec on 30.10.2017.
 */

public interface RequestCodes {

    // REQUESTS
    int ENABLE_BT_REQUEST = 1;

    // MESSAGES
    int NEW_DEVICE_FOUND_MSG = 101;
    int NEW_DEVICE_CONNECTED_MSG = 102;

    // ACTIONS
    String DEVICE_CONNECTED_ACTION = "sk.dphealth.action.device_connected";

}
