package com.example.project_class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ImagesListAdapter extends ArrayAdapter<ImageInfo> {
    boolean isToggled = false;
    int _layout;
    TextView txt1, txt2;
    ImageView imgView;

    public ImagesListAdapter(@NonNull Context context, int resource, @NonNull List<ImageInfo> objects) {
        super(context, resource, objects);
        _layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(_layout, null);
        }

        if (_layout == R.layout.images_item) {
            TextView txt2_t = (TextView) v.findViewById(R.id.txtDateHidden);
            TextView txt1_t = (TextView) v.findViewById(R.id.txtPlaceHidden);
            ImageView imgView_t = (ImageView) v.findViewById(R.id.imgDisplay);
            txt1 = txt1_t;
            txt2 = txt2_t;
            imgView = imgView_t;
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isToggled = !isToggled;
                    if (isToggled) {
                        txt1_t.setVisibility(View.VISIBLE);
                        txt2_t.setVisibility(View.VISIBLE);
                    } else {
                        txt1_t.setVisibility(View.INVISIBLE);
                        txt2_t.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else if(_layout==R.layout.images_item_list){
            txt1 = (TextView) v.findViewById(R.id.txtPlace_list);
            txt2 = (TextView) v.findViewById(R.id.txtDate_list);
            imgView = (ImageView) v.findViewById(R.id.img_list);
        }
        ImageInfo img = getItem(position);
        if (img != null) {
            txt1.setText(img.getPlace());
            txt2.setText(img.getDate());
            imgView.setImageBitmap(img.getBitmap());

        }
        return v;
    }
}
