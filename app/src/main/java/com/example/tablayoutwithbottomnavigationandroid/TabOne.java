package com.example.tablayoutwithbottomnavigationandroid;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tablayoutwithbottomnavigationandroid.databinding.FragmentTabOneBinding;

public class TabOne extends Fragment {

	ItemViewModel viewModel;
	TextView textViewSeekbar1;
	TextView textViewSeekbar2;
	TextView textViewSeekbar3;
	SeekBar seekBar1;
	SeekBar seekBar2;
	SeekBar seekBar3;
	private FragmentTabOneBinding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		binding = FragmentTabOneBinding.inflate(inflater, container, false);
		View view = binding.getRoot();

		return view;
	}
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

		binding.seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				String value = "A" + progress;
				binding.seekBar2.setProgress(0);
				binding.seekBar3.setProgress(0);
				binding.textViewSeekbarVal1.setText(value);
				viewModel.setData(value);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		binding.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				String value = "B" + progress;
				binding.seekBar1.setProgress(0);
				binding.seekBar3.setProgress(0);
				binding.textViewSeekbarVal2.setText(value);
				viewModel.setData1(value);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});

		binding.seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				String value = "C" + progress;
				binding.seekBar1.setProgress(0);
				binding.seekBar2.setProgress(0);
				binding.textViewSeekbarVal3.setText(value);
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