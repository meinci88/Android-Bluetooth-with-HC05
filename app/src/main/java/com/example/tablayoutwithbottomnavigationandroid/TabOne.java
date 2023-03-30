package com.example.tablayoutwithbottomnavigationandroid;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class TabOne extends Fragment {

	ItemViewModel viewModel;
	TextView textViewSeekbar1;
	TextView textViewSeekbar2;
	TextView textViewSeekbar3;
	SeekBar seekBar1;
	SeekBar seekBar2;
	SeekBar seekBar3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_tab_one, container, false);
	}
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		textViewSeekbar1 = view.findViewById(R.id.textViewSeekbarVal1);
		textViewSeekbar2 = view.findViewById(R.id.textViewSeekbarVal2);
		textViewSeekbar3 = view.findViewById(R.id.textViewSeekbarVal3);
		viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

		seekBar1 = view.findViewById(R.id.seekBar1);
		seekBar2 = view.findViewById(R.id.seekBar2);
		seekBar3 = view.findViewById(R.id.seekBar3);

		seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				String value = "A" + progress;
				//viewModel.setData(String.valueOf(seekBar1.getProgress()));
				textViewSeekbar1.setText(value);
				viewModel.setData(value);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				String value = "B" + progress;
				//viewModel.setData(String.valueOf(seekBar1.getProgress()));
				textViewSeekbar2.setText(value);
				viewModel.setData1(value);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				String value = "C" + progress;
				//viewModel.setData(String.valueOf(seekBar1.getProgress()));
				textViewSeekbar3.setText(value);
				viewModel.setData2(value);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});


	}
}