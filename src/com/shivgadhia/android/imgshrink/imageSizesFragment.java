package com.shivgadhia.android.imgshrink;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class imageSizesFragment extends Fragment implements OnClickListener{
	private static final String SELECTED_PICTURE = null;
	private String mSelectedImage;



	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(imageSizesFragment.SELECTED_PICTURE, mSelectedImage);
	}

	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			mSelectedImage = savedInstanceState.getString(SELECTED_PICTURE);
			if(mSelectedImage != null){
				// Load list of sizes - set image sources
			}else{
				
			}
		}
		
		
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.imagesizes_fragment_layout, container, false);
		View phoneSizeView = ((ViewStub)v.findViewById(R.id.phoneStub)).inflate();
		View tabletSizeView = ((ViewStub)v.findViewById(R.id.tabletStub)).inflate();
		View tvSizeView = ((ViewStub)v.findViewById(R.id.tvStub)).inflate();
		
		
		prepareSizeView(phoneSizeView, R.string.size_name_phone, R.string.size_dims_phone, R.string.size_size_phone, R.drawable.phone);
		prepareSizeView(tabletSizeView, R.string.size_name_tablet, R.string.size_dims_tablet, R.string.size_size_tablet, R.drawable.tablet);
		prepareSizeView(tvSizeView, R.string.size_name_tv, R.string.size_dims_tv, R.string.size_size_tv, R.drawable.tv);
		return v;
	}
	
	
	private void prepareSizeView(View rootView, int sizeNameRes, int sizeDimsRes, int sizeSizeRes, int imgRes){
		TextView tvSizeName = (TextView) rootView.findViewById(R.id.tvSizeName);
		tvSizeName.setText(sizeNameRes);
		TextView tvSizeDims = (TextView) rootView.findViewById(R.id.tvSizeDims);
		tvSizeDims.setText(sizeDimsRes);
		TextView tvSizeSize = (TextView) rootView.findViewById(R.id.tvSizeSize);
		tvSizeSize.setText(sizeSizeRes);
		ImageView ivDeviceIcon = (ImageView) rootView.findViewById(R.id.ivDeviceIcon);
		ivDeviceIcon.setImageResource(imgRes);
		rootView.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.phoneStub_inf : 
			Toast.makeText(getActivity(), "onCLick called - for Phone", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tabletStub_inf : 
			Toast.makeText(getActivity(), "onCLick called - for Tablet", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tvStub_inf : 
			Toast.makeText(getActivity(), "onCLick called - for TV", Toast.LENGTH_SHORT).show();
			break;
		}
		
		
	}
	
}
