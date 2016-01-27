package com.framgia.lupx.loaderexample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ImageItem> listImages;
    private RecyclerView lstResult;
    private ImagesAdapter adapter;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listImages = new ArrayList<>();
        lstResult = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lstResult.setLayoutManager(lm);
        adapter = new ImagesAdapter(this, listImages);
        lstResult.setAdapter(adapter);
        progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
//    private List<ImageItem> parseData(Cursor data) {
//        List<ImageItem> list = new ArrayList<>();
//        if (data == null || data.getCount() == 0) {
//            return list;
//        }
//        data.moveToFirst();
//        if (columnIndexs == null) {
//            columnIndexs = new HashMap<>();
//            for (String key : PROJECTIONS) {
//                columnIndexs.put(key, data.getColumnIndex(key));
//            }
//        }
//        while (!data.isAfterLast()) {
//            ImageItem item = new ImageItem();
//            item.bucket_id = data.getInt(columnIndexs.get(MediaStore.Images.ImageColumns
//                .BUCKET_ID));
//            item.bucket_display_name = data.getString(columnIndexs.get(MediaStore.Images
//                .ImageColumns.BUCKET_DISPLAY_NAME));
//            item.bucket_name = data.getString(columnIndexs.get(MediaStore.Images.ImageColumns
//                .DISPLAY_NAME));
//            item.path = data.getString(columnIndexs.get(MediaStore.Images.ImageColumns.DATA));
//            list.add(item);
//            data.moveToNext();
//        }
//        return list;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
