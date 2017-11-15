package dpihealth.sk.miband.helpers;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dpihealth.sk.miband.R;
import dpihealth.sk.miband.activities.FitMonitorActivity;
import dpihealth.sk.miband.interfaces.Communication;

/**
 * Created by rybec on 06.11.2017.
 */

public class BluetoothDevicesListAdapter extends RecyclerView.Adapter<BluetoothDevicesListAdapter.ViewHolder> {

private Context context;
private List<BluetoothDevice> devices;

// View holder
static class ViewHolder extends RecyclerView.ViewHolder {

    TextView deviceNameTextView;
    TextView deviceAddressTextView;
    LinearLayout connectButtonLL;

    public ViewHolder(View view) {
        super(view);
        deviceNameTextView = (TextView)view.findViewById(R.id.deviceName);
        deviceAddressTextView = (TextView)view.findViewById(R.id.deviceAddress);
        connectButtonLL = (LinearLayout)view.findViewById(R.id.connectButtonLayout);
    }
}

    public BluetoothDevicesListAdapter(Context context, List<BluetoothDevice> devices) {
        this.context = context;
        this.devices = devices;
//        Toast.makeText(context, "Val " + values[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    public BluetoothDevicesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bluetooth_device_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BluetoothDevicesListAdapter.ViewHolder holder, int position) {
        Resources resources = context.getResources();

        final BluetoothDevice device = devices.get(position);

        holder.deviceNameTextView.setText(device.getName());
        holder.deviceAddressTextView.setText(device.getAddress());

        Button connectButton = new Button(context);
        connectButton.setText("Connect");
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FitMonitorActivity.class);
                intent.putExtra(Communication.INTER_ACTIVITY_DEVICE_BUNDLE_KEY, device);

                context.startActivity(intent);
            }
        });

        holder.connectButtonLL.addView(connectButton);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}