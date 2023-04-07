package com.example.tablayoutwithbottomnavigationandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class FragmentSettings extends Fragment {
	TextView textView;
	Button button;
	Button button_btOn;
	ListView listView;
	BluetoothAdapter btAdapter;
	private ArrayList<String> arrayList;
	private ArrayAdapter<String> adapter;
	private static final int REQUEST_ENABLE_BT = 0;

	String[] permissions = {"android.permission.BLUETOOTH_CONNECT"};

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		btAdapter = BluetoothAdapter.getDefaultAdapter();
		textView = view.findViewById(R.id.textviewDev);
		button = view.findViewById(R.id.button_devices);
		button_btOn = view.findViewById(R.id.button_btOn);
		listView = view.findViewById(R.id.listView);
		//listView = (ListView) view.findViewById(R.id.listView);
		arrayList = new ArrayList<>();
		adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, arrayList);

		if (btAdapter.isEnabled()) {
			textView.setText(" ");
			if (ActivityCompat.checkSelfPermission(getContext(),
					android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(permissions, 80);
				return;
			}
			Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
			for (BluetoothDevice device:devices){
				listView.setAdapter(adapter);
				arrayList.add(device.getName() + ":  " + String.valueOf(device));
				adapter.notifyDataSetChanged();
			}
		}


		button_btOn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!btAdapter.isEnabled()){
					requestPermissions(permissions, 80);
					Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(intent, REQUEST_ENABLE_BT);
					}
				}
		});

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*if (btAdapter.isEnabled()) {
					textView.setText(" ");
					if (ActivityCompat.checkSelfPermission(getContext(),
							android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
						requestPermissions(permissions, 80);
						return;
					}
					Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
					for (BluetoothDevice device:devices){
						listView.setAdapter(adapter);
						arrayList.add(device.getName() + ":  " + String.valueOf(device));
						adapter.notifyDataSetChanged();
					}
				}*/
			}
		});
	}

	private void showToast(String msg){
		Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}
}