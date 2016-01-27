package com.framgia.lupx.loaderexample;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by pham.xuan.lu on 1/27/16.
 */
public class PhotoLoader extends CursorLoader {
    private final String[] PROJECTIONS = new String[]{
        MediaStore.Images.ImageColumns._ID,
        MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DATA
    };
    private final Uri PHOTO_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    public PhotoLoader(Context context) {
        super(context);
        setUri(PHOTO_URI);
        setProjection(PROJECTIONS);
    }

    public void setBucketId(long bucketId) {
        String bucket = String.valueOf(bucketId);
        setSelection(MediaStore.Images.ImageColumns.BUCKET_ID + " = ?");
        setSelectionArgs(new String[]{bucket});
    }
}