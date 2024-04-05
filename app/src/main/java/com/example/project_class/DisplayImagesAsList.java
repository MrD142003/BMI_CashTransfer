package com.example.project_class;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class DisplayImagesAsList extends AppCompatActivity {
    GridView gridView;
    TextView txtCheck;
    MyDBHelper db = new MyDBHelper(DisplayImagesAsList.this);
    ArrayList<ImageInfo> arrImagesInfo;
    ImagesListAdapter adapter;


    @Override
    protected void onStart() {
        super.onStart();
        db.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.closeDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_grid_types_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.showTypeGrid){
            gridViewState();
        }
        else{
            listViewState();
        }
        return super.onOptionsItemSelected(item);
    }

    private void listViewState() {
        gridView.setNumColumns(1);
        arrImagesInfo = new ArrayList<ImageInfo>();
        adapter = new ImagesListAdapter(DisplayImagesAsList.this, R.layout.images_item_list, arrImagesInfo);
        gridView.setAdapter(adapter);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        Cursor cursor = db.DisplayAll("Sinhvien_Images", id);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getSvId()))) == id) {
                    int imageId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getID())));
                    byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow(MyDBHelper.getImgBitmap()));
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    String place = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getPlace()));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getDate()));
                    arrImagesInfo.add(new ImageInfo(bitmap, place, date));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_images_as_grid_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InitWidget();
        gridViewState();
    }

    private void gridViewState(){
        gridView.setNumColumns(3);
        arrImagesInfo = new ArrayList<ImageInfo>();
        adapter = new ImagesListAdapter(DisplayImagesAsList.this, R.layout.images_item, arrImagesInfo);
        gridView.setAdapter(adapter);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        Cursor cursor = db.DisplayAll("Sinhvien_Images", id);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getSvId()))) == id) {
                    int imageId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getID())));
                    byte[] imageData = cursor.getBlob(cursor.getColumnIndexOrThrow(MyDBHelper.getImgBitmap()));
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    String place = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getPlace()));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getDate()));
                    arrImagesInfo.add(new ImageInfo(bitmap, place, date));
                }
            }
        }
        adapter.notifyDataSetChanged();

    }
    private void InitWidget() {
        gridView = (GridView) findViewById(R.id.imagesAsGridView);
    }
}