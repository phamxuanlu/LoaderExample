package com.framgia.lupx.loaderexample;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pham.xuan.lu on 1/27/16.
 */
public class AlbumFragment extends Fragment {
    private static final String[] PROJECTIONS = new String[]{
        MediaStore.Images.ImageColumns._ID,
        MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DATA
    };
    private static final int ALBUMS_LOADER_ID = 1;
    private RecyclerView rcvAlbums;
    private AlbumAdapter adapter;
    private List<FolderItem> folders;
    private Map<String, Integer> columnIndexs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        rcvAlbums = (RecyclerView) view.findViewById(R.id.rcvAlbums);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity()
            .getApplicationContext(), 2);
        folders = new ArrayList<>();
        adapter = new AlbumAdapter(getActivity().getApplicationContext(), folders);
        rcvAlbums.setAdapter(adapter);
        rcvAlbums.setLayoutManager(gridLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ALBUMS_LOADER_ID,
            null, new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    return new FolderLoader(getActivity());
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    Cursor cursor = new GroupByBucketIdFunc().call(data);
                    folders.clear();
                    folders.addAll(parseData(cursor));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                }
            });
    }

    private List<FolderItem> parseData(Cursor data) {
        List<FolderItem> list = new ArrayList<>();
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
            FolderItem item = new FolderItem();
            item.bucket_id = data.getInt(columnIndexs.get(MediaStore.Images.ImageColumns
                .BUCKET_ID));
            item.bucket_display_name = data.getString(columnIndexs.get(MediaStore.Images
                .ImageColumns.BUCKET_DISPLAY_NAME));
            item.display_name = data.getString(columnIndexs.get(MediaStore.Images.ImageColumns
                .DISPLAY_NAME));
            item.path = data.getString(columnIndexs.get(MediaStore.Images.ImageColumns.DATA));
            list.add(item);
            data.moveToNext();
        }
        return list;
    }
}