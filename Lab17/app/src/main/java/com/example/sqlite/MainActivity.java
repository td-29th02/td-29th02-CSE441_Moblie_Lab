package com.example.sqlite;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "qlsach.db";
    private SQLiteDatabase database = null;
    private ListView lv;
    private ArrayList<String> mylist;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo views
        initViews();

        // Tạo database và bảng
        createDatabase();

        // Load dữ liệu
        loadData();
    }

    private void initViews() {
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(adapter);
    }

    private void createDatabase() {
        try {
            // Mở hoặc tạo database
            database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Log.d("MainActivity", "Database created/opened successfully");

            // Tạo bảng tbsach
            String createTableSQL = "CREATE TABLE IF NOT EXISTS tbsach (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tensach TEXT NOT NULL, " +
                    "tacgia TEXT NOT NULL, " +
                    "namxb INTEGER DEFAULT 2023)";

            database.execSQL(createTableSQL);
            Log.d("MainActivity", "Table tbsach created successfully");

            // Kiểm tra xem đã có dữ liệu chưa
            Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM tbsach", null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            // Nếu chưa có dữ liệu, thêm dữ liệu mẫu
            if (count == 0) {
                insertSampleData();
            }

            Toast.makeText(this, "Database setup completed!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("MainActivity", "Error creating database: " + e.getMessage());
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void insertSampleData() {
        try {
            // Thêm dữ liệu mẫu
            String[] insertSQL = {
                    "INSERT INTO tbsach(tensach, tacgia, namxb) VALUES('Lập trình Android cơ bản', 'Nguyễn Văn A', 2023)",
                    "INSERT INTO tbsach(tensach, tacgia, namxb) VALUES('Java từ cơ bản đến nâng cao', 'Trần Văn B', 2022)",
                    "INSERT INTO tbsach(tensach, tacgia, namxb) VALUES('Cơ sở dữ liệu SQLite', 'Lê Thị C', 2024)",
                    "INSERT INTO tbsach(tensach, tacgia, namxb) VALUES('Thiết kế giao diện Android', 'Phạm Văn D', 2023)",
                    "INSERT INTO tbsach(tensach, tacgia, namxb) VALUES('Kotlin cho người mới bắt đầu', 'Hoàng Thị E', 2024)"
            };

            for (String sql : insertSQL) {
                database.execSQL(sql);
            }

            Log.d("MainActivity", "Sample data inserted successfully");
            Toast.makeText(this, "Đã thêm dữ liệu mẫu", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("MainActivity", "Error inserting sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadData() {
        if (database == null) {
            Toast.makeText(this, "Database not available", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Clear danh sách cũ
            mylist.clear();

            // Truy vấn dữ liệu từ bảng tbsach
            Cursor cursor = database.query("tbsach", null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Lấy dữ liệu từ các cột
                    @SuppressLint("Range")
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    @SuppressLint("Range")
                    String tensach = cursor.getString(cursor.getColumnIndex("tensach"));
                    @SuppressLint("Range")
                    String tacgia = cursor.getString(cursor.getColumnIndex("tacgia"));
                    @SuppressLint("Range")
                    String namxb = cursor.getString(cursor.getColumnIndex("namxb"));

                    String data = id + " - " + tensach + " - " + tacgia + " (" + namxb + ")";
                    mylist.add(data);

                } while (cursor.moveToNext());

                cursor.close();
                adapter.notifyDataSetChanged();

                Toast.makeText(this, "Đã tải " + mylist.size() + " cuốn sách", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "Loaded " + mylist.size() + " records");

            } else {
                Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                if (cursor != null) cursor.close();
            }

        } catch (Exception e) {
            Log.e("MainActivity", "Error loading data: " + e.getMessage());
            Toast.makeText(this, "Lỗi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null && database.isOpen()) {
            database.close();
            Log.d("MainActivity", "Database closed");
        }
    }
}