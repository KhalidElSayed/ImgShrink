package com.shivgadhia.android.imgshrink;

import java.io.File;


import android.R.anim;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class imagePickerFragment extends Fragment {
	
	protected static final int SELECT_PICTURE = 1;
	private static final String SELECTED_PICTURE = "selectedPicture";
	private RelativeLayout btnPickImage;
	private ImageView ivSelectedImage;
	private TextView tvSelectImage;
	private Bitmap bmSelectedImage;
	private float mDensity;

	private ImagePickedListener mImagePickedListener;
	private String mSelectedImage;
	
	public void setImagePickedListener(ImagePickedListener ipl){
		mImagePickedListener = ipl;
	}
	
	public interface ImagePickedListener {
		public abstract void onImagePicked(String filename);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(imagePickerFragment.SELECTED_PICTURE, mSelectedImage);
	}

	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			mSelectedImage = savedInstanceState.getString(SELECTED_PICTURE);
			if(mSelectedImage != null){
				createBitmapThumbnail(mSelectedImage);
			}
		}
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.imagepicker_fragment_layout, container, false);
		btnPickImage = (RelativeLayout)v.findViewById(R.id.vf1_btn_pick);
		btnPickImage.setOnClickListener(mPickImageListener);
		ivSelectedImage = (ImageView)v.findViewById(R.id.ivSelectedImg);
		ivSelectedImage.setOnClickListener(mPickImageListener);
		tvSelectImage = (TextView)v.findViewById(R.id.tvSelectImg);
		mDensity = getActivity().getResources().getDisplayMetrics().density;
		return v;
	}

	private OnClickListener mPickImageListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 Intent intent = new Intent();
             intent.setType("image/*");
             intent.setAction(Intent.ACTION_GET_CONTENT);
             startActivityForResult(Intent.createChooser(intent,
                     "Pick an image"), SELECT_PICTURE);
			
		}
	};
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                //OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                //MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);

                //DEBUG PURPOSE - you can delete this if you want
                if(selectedImagePath!=null)
                    System.out.println(selectedImagePath);
                else System.out.println("selectedImagePath is null");
                if(filemanagerstring!=null)
                    System.out.println(filemanagerstring);
                else System.out.println("filemanagerstring is null");

                //NOW WE HAVE OUR WANTED STRING
                if(selectedImagePath!=null){
                    mSelectedImage = selectedImagePath;
                }else{
                    mSelectedImage = filemanagerstring;
                }
                
                createBitmapThumbnail(mSelectedImage);
            }
        }
	}

//http://en.wikipedia.org/wiki/Display_resolution	
	private void createBitmapThumbnail(String filename){	
		tvSelectImage.setVisibility(View.INVISIBLE);
		bmSelectedImage = BitmapHelper.decodeFile(filename, 480, 640);
		
		ivSelectedImage.setImageBitmap(BitmapHelper.getRoundedCornerBitmap(bmSelectedImage, (int)(10 * mDensity + 0.5f)));
		
		//ivSelectedImage.setImageBitmap(ImageUtilities.rotateAndFrame(bmSelectedImage));
		Animation a = AnimationUtils.loadAnimation(getActivity(),anim.fade_in);
		a.setStartOffset(150);
		a.setDuration(1000);
		a.setFillAfter(true);
		ivSelectedImage.startAnimation(a);
		//ivSelectedImage.setVisibility(View.VISIBLE);
		if(mImagePickedListener != null){
			mImagePickedListener.onImagePicked(filename);
		}
	}
	
	
	@Override
	public void onDestroy() {
		if(bmSelectedImage != null){
			bmSelectedImage.recycle();
		}
		super.onDestroy();
	}


	public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if(cursor!=null)
        {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }
	
	
}
