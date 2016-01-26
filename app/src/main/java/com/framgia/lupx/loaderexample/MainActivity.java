package com.framgia.lupx.loaderexample;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int IMAGE_LOADER_ID = 1;
    private final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private final String[] PROJECTIONS = new String[]{
        MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DATA
    };
    private Map<String, Integer> columnIndexs;
    private List<ImageItem> listImages;
    private RecyclerView lstResult;
    private ImagesAdapter adapter;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLoaderManager().initLoader(IMAGE_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
            IMAGE_URI,
            PROJECTIONS,
            null,
            null,
            null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (listImages != null && listImages.size() > 0) {
            listImages.clear();
        }
        listImages.addAll(parseData(data));
        adapter.notifyDataSetChanged();
        Log.v("LOADER", "FINISH");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private List<ImageItem> parseData(Cursor data) {
        List<ImageItem> list = new ArrayList<>();
        if (data == null || data.getCount() == 0) {
            return list;
        }
        data.moveToFirst();
        if (columnIndexs == null) {
            columnIndexs = new HashMap<>();
            for (String key : PROJECTIONS) {
                columnIndexs.put(key, data.getColumnIndex(key));
            }
        }
        while (!data.isAfterLast()) {
            ImageItem item = new ImageItem();
            item.bucket_id = data.getInt(columnIndexs.get(MediaStore.Images.ImageColumns
                .BUCKET_ID));
            item.bucket_display_name = data.getString(columnIndexs.get(MediaStore.Images
                .ImageColumns.BUCKET_DISPLAY_NAME));
            item.bucket_name = data.getString(columnIndexs.get(MediaStore.Images.ImageColumns
                .DISPLAY_NAME));
            item.path = data.getString(columnIndexs.get(MediaStore.Images.ImageColumns.DATA));
            list.add(item);
            data.moveToNext();
        }
        return list;
    }

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
