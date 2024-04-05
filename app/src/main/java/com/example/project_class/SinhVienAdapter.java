package com.example.project_class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.ResourceCursorAdapter;

import java.util.List;

public class SinhVienAdapter extends ArrayAdapter<SinhVien> {
    public SinhVienAdapter(@NonNull Context context, int resource, @NonNull List<SinhVien> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v =vi.inflate(R.layout.sinhvien_item,null);
        }
        SinhVien sv = getItem(position);
        if(sv!=null){
            TextView txt1 =(TextView) v.findViewById(R.id.txtID);
            TextView txt2 =(TextView) v.findViewById(R.id.txtHoten);
            TextView txt3 =(TextView) v.findViewById(R.id.txtNamsinh);
            txt1.setText(String.valueOf(sv.getId()));
            txt2.setText(sv.getName());
            txt3.setText(String.valueOf(sv.getYearOB()));
        }
        return v;
    }
}
