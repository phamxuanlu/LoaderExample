package com.framgia.lupx.loaderexample;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.MediaStore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import rx.functions.Func1;

/**
 * Created by pham.xuan.lu on 1/27/16.
 */
public class GroupByBucketIdFunc implements Func1<Cursor, Cursor> {
    private HashMap<String, Integer> columnIndexs;

    @Override
    public Cursor call(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        columnIndexs = new HashMap<>();
        String[] projections = cursor.getColumnNames();
        for (int i = 0; i < projections.length; i++) {
            columnIndexs.put(projections[i], cursor.getColumnIndex(projections[i]));
        }
        MatrixCursor bucketCursor = new MatrixCursor(cursor.getColumnNames(), cursor.getCount());
        cursor.moveToFirst();
        Set<Long> bucketIds = new HashSet<>();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(columnIndexs.get(MediaStore.Images.ImageColumns.BUCKET_ID));
            if (!bucketIds.contains(id)) {
                bucketCursor.addRow(new Object[]{
                    cursor.getLong(columnIndexs.get(MediaStore.Images.ImageColumns._ID)),
                    id,
                    cursor.getString(columnIndexs.get(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)),
                    cursor.getString(columnIndexs.get(MediaStore.Images.ImageColumns.DISPLAY_NAME)),
                    cursor.getString(columnIndexs.get(MediaStore.Images.ImageColumns.DATA))
                });
            }
            bucketIds.add(id);
            cursor.moveToNext();
        }
        return bucketCursor;
    }
}