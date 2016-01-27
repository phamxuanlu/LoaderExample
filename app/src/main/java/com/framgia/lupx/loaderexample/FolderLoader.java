package com.framgia.lupx.loaderexample;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by pham.xuan.lu on 1/27/16.
 */
public class FolderLoader extends CursorLoader {
    private static final String[] PROJECTIONS = new String[]{
        MediaStore.Images.ImageColumns._ID,
        MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DATA
    };
    private static final Uri FOLDER_PHOTO_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    public FolderLoader(Context context) {
        super(context,
            FOLDER_PHOTO_URI,
            PROJECTIONS,
            null,
            null,
            null);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = super.loadInBackground();
        Cursor bucketCursor = new GroupByBucketIdFunc().call(cursor);
        cursor.close();
        return bucketCursor;
    }
}