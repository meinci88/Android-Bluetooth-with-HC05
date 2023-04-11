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
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;

public class FragmentSettings extends Fragment {
	ItemViewModel viewModel;
	Spinner spinner;
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
		viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

		btAdapter = BluetoothAdapter.getDefaultAdapter();
		button = view.findViewById(R.id.button_devices);
		button_btOn = view.findViewById(R.id.button_btOn);
		listView = view.findViewById(R.id.listView);
		spinner = view.findViewById(R.id.spinner);
		arrayList = new ArrayList<>();
		adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, arrayList);

		if (btAdapter.isEnabled()) {
			if (ActivityCompat.checkSelfPermission(getContext(),
					android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(permissions, 80);
				return;
			}
			Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
			for (BluetoothDevice device:devices){
				spinner.setAdapter(adapter);
				listView.setAdapter(adapter);
				arrayList.add(device.getName() + ":  " + String.valueOf(device));
				adapter.notifyDataSetChanged();
			}
		}
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//**** ListView Item select *****
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						viewModel.setName(listView.getItemAtPosition(position).toString());// Position der angeklickten ListView
					}
				});

		button_btOn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				arrayList.clear();
				if (!btAdapter.isEnabled()){
					requestPermissions(permissions, 80);
					Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(intent, REQUEST_ENABLE_BT);
					}
				if (btAdapter.isEnabled()) {
					showToast("Bluetooth ist schon  eingeschaltet");
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
			}
		});

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				arrayList.clear();
				if (btAdapter.isEnabled()) {

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
				}else {
					listView.setAdapter(adapter);
					arrayList.clear();
					adapter.notifyDataSetChanged();
					showToast("Bluetooth ist momentan  nicht eingeschaltet");
				}
			}
		});
	}

	private void showToast(String msg){
		Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}
}