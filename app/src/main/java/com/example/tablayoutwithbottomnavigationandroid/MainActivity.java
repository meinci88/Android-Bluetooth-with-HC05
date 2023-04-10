package com.example.tablayoutwithbottomnavigationandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tablayoutwithbottomnavigationandroid.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
	static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	BluetoothSocket btSocket = null;
	TabLayout tabLayout;
	ViewPager viewPager;
	ViewPagerAdapter viewPagerAdapter;
	BottomNavigationView bottomNavigationView;
	FrameLayout frameLayout;

	TextView textView1;
	TextView textView2;
	BluetoothAdapter btAdapter;
	String [] permissions={"android.permission.BLUETOOTH_CONNECT"};

	private ItemViewModel viewModel;
	private ActivityMainBinding binding;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//requestPermissions(permissions, 80);

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		View view = binding.getRoot();
		setContentView(view);

		btAdapter = BluetoothAdapter.getDefaultAdapter();

		BTconnect();


		viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

		viewModel.getSelectedItem().observe(this, item ->{
			try {
				OutputStream outputStream = btSocket.getOutputStream();
				//textView.setText(item);
				outputStream.write(item.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
				BTconnect();
			}
		});

		viewModel.getSelectedItem1().observe(this, item1 ->{
			try {
				OutputStream outputStream = btSocket.getOutputStream();
				//textView1.setText(item1);
				outputStream.write(item1.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
				BTconnect();
			}
		});

		viewModel.getSelectedItem2().observe(this, item2 ->{
			try {
				OutputStream outputStream = btSocket.getOutputStream();
				//textView2.setText(item2);
				outputStream.write(item2.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
				BTconnect();
			}
		});

		//viewPager = findViewById(R.id.viewPager);
		viewPagerAdapter = new ViewPagerAdapter(this);
		binding.viewPager.setAdapter(viewPagerAdapter);
		bottomNavigationView = findViewById(R.id.bottomNav);
		frameLayout = findViewById(R.id.frameLayout);

		//region Tablayout addOnTabSelectedListener
		binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				binding.viewPager.setVisibility(View.VISIBLE);
				binding.frameLayout.setVisibility(View.GONE);
				binding.viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
				binding.viewPager.setVisibility(View.VISIBLE);
				binding.frameLayout.setVisibility(View.GONE);
			}
		});

		//endregion

		binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageSelected(int position) {
				switch (position) {
					case 0:
					case 1:
					case 2:
						binding.tabLayout.getTabAt(position).select();
				}
				super.onPageSelected(position);
			}
		});

	//region Bottom Navigation	setOnItemSelectedListener
		bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
			@SuppressLint("NonConstantResourceId")
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				binding.frameLayout.setVisibility(View.VISIBLE);
				binding.viewPager.setVisibility(View.GONE);
				switch (item.getItemId()) {

					case R.id.bottom_home:
						getSupportFragmentManager().beginTransaction()
								.replace(R.id.frameLayout, new FragmentHome()).commit();

						return true;
					case R.id.bottom_settings:
						getSupportFragmentManager().beginTransaction()
								.replace(R.id.frameLayout, new FragmentSettings()).commit();

						return true;
					case R.id.bottom_downloads:
						getSupportFragmentManager().beginTransaction()
								.replace(R.id.frameLayout, new FragmentDownloads()).commit();
						return true;
				}
				return false;
			}
		});
	//endregion

	}
	@SuppressLint("MissingPermission")
	public void BTconnect() {
		//BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothDevice HC05A = btAdapter.getRemoteDevice("98:D3:32:70:E3:82");
		//BluetoothDevice HC05B = btAdapter.getRemoteDevice("98:D3:32:70:E0:FD");
		try {
			btSocket = HC05A.createRfcommSocketToServiceRecord(MY_UUID);
			String dev;
			String devName;
			ImageView imageView = (ImageView) findViewById(R.id.bluetoothImage1);
			imageView.setBackgroundResource(R.drawable.rainbowcol);
			btSocket.connect();
		} catch (IOException e) {
			e.printStackTrace();
			ImageView imageView = (ImageView)findViewById(R.id.bluetoothImage1);
			imageView.setBackgroundResource(R.drawable.rainbowcol1);
		}
	}
}