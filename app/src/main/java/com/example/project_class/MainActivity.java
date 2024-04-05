package com.example.project_class;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    EditText edtID, edtHoten, edtNamsinh, edtPlace, edtDate;
    Button btnInsert, btnLoad, btnUpdate, btnDelete;
    TextView txtTittleList;
    ListView listSinhVien;
    SinhVienAdapter adapter;
    ArrayList<SinhVien> arrSinhVien;
    MyDBHelper db = new MyDBHelper(MainActivity.this);
    ImageView imgPic;
    byte[] byteArray = null;
    ArrayList<ImageInfo> imageInfoArrayList;
    private
    ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                            Toast.makeText(MainActivity.this, bitmap + "", Toast.LENGTH_SHORT).show();
                            imgPic.setImageBitmap(bitmap);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byteArray = stream.toByteArray();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitWidget();
        imageInfoArrayList = new ArrayList<ImageInfo>();
        arrSinhVien = new ArrayList<SinhVien>();

        adapter = new SinhVienAdapter(MainActivity.this, R.layout.sinhvien_item, arrSinhVien);
        listSinhVien.setAdapter(adapter);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtHoten.getText().toString();
                int year = Integer.parseInt(edtNamsinh.getText().toString());
                long insertResult = db.Insert(name, year);
                if (insertResult == 0) {
                    Toast.makeText(MainActivity.this, "Insert Failed", Toast.LENGTH_SHORT).show();
                } else {
                    loadDatabase();
                    Toast.makeText(MainActivity.this, name + " has been added to " + db.getDatabaseName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatabase();
                Toast.makeText(MainActivity.this, db.getCount() + " " + arrSinhVien.size(), Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(edtID.getText().toString());
                String name = edtHoten.getText().toString();
                int yearob = Integer.parseInt(edtNamsinh.getText().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure to update information for sinh vien \"" + name + "\" ?")
                        .setTitle("Update Information");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long resultUpdate = db.Update(id, name, yearob);
                        if (resultUpdate == 0) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        } else {
                            insertImages(id, byteArray);
                            loadDatabase();
//                            Toast.makeText(MainActivity.this, "Updated " + id + " " + byteArray.length, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(edtID.getText().toString());
                String name = edtHoten.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure to delete sinh vien\"" + name + "\" ?").setTitle("Delete record");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long resultDelete = db.Delete(id);
                        Toast.makeText(MainActivity.this, resultDelete + "", Toast.LENGTH_SHORT).show();
                        if (resultDelete == 0) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        } else {
                            db.updateIDAfterDelete();
                            loadDatabase();
                            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }

        });

        listSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = db.DisplayAll("Sinhvien_Images", position + 1);
                Toast.makeText(MainActivity.this, cursor.getCount() + "", Toast.LENGTH_SHORT).show();
                editInformation(position);
            }
        });

        imgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryLauncher.launch("image/*");
            }
        });

        listSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = db.DisplayAll("Sinhvien_Images");
                if (cursor != null) {
                    Intent intent = new Intent(MainActivity.this, DisplayImagesAsList.class);
                    intent.putExtra("id", position+1);
                    startActivity(intent);
                }
                return false;
            }
        });

    }

    private void insertImages(int sv_id, byte[] byteArray) {
        if (byteArray != null) {
            String place = edtPlace.getText().toString();
            String date = edtDate.getText().toString();
            long result = db.InsertToSinhvien_Images(sv_id, byteArray, place, date);
            if (result == 0) {
                Toast.makeText(this, "Insert image fail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, place + "  " + date, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "Add image success", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void editInformation(int position) {
        SinhVien sv = arrSinhVien.get(position);
        edtID.setText(String.valueOf(sv.getId()));
        edtHoten.setText(sv.getName());
        edtNamsinh.setText(String.valueOf(sv.getYearOB()));
    }

    private void loadDatabase() {
        txtTittleList.setText("Danh sách sinh viên");
        arrSinhVien.clear();
        /* StringBuffer buffer = new StringBuffer();*/
        Cursor cursor = db.DisplayAll("Sinhvien");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getID())));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getNAME()));
            int yearOB = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.getYEAROB())));
            arrSinhVien.add(new SinhVien(id, name, yearOB));
                   /* buffer.append(id);
                    buffer.append("-");
                    buffer.append(name);
                    buffer.append("-");
                    buffer.append(yearOB);
                    buffer.append("-");*/
        }
        adapter.notifyDataSetChanged();
    }

    private void InitWidget() {
        edtID = (EditText) findViewById(R.id.editID);
        edtHoten = (EditText) findViewById(R.id.editHoten);
        edtNamsinh = (EditText) findViewById(R.id.editNamsinh);
        edtPlace = (EditText) findViewById(R.id.edtPlace);
        edtDate = (EditText) findViewById(R.id.editDate);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        listSinhVien = (ListView) findViewById(R.id.listSinhVien);
        txtTittleList = (TextView) findViewById(R.id.txtTittleList);
        imgPic = (ImageView) findViewById(R.id.imgSelectedPic);
    }

}