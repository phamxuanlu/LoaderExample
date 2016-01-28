package com.framgia.lupx.loaderexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by pham.xuan.lu on 1/27/16.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<FolderItem> folders;

    public AlbumAdapter(Context context, List<FolderItem> items) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.folders = items;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        FolderItem fi = folders.get(position);
        holder.name.setText(fi.bucket_display_name);
        File file = new File(fi.path);
        Picasso.with(context)
            .load(file)
            .resize(200, 200)
            .centerCrop()
            .into(holder.imgvThumbnail);
    }

    @Override
    public int getItemCount() {
        return folders == null ? 0 : folders.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgvThumbnail;
        public TextView name;

        public AlbumViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            imgvThumbnail = (ImageView) view.findViewById(R.id.imgvThumbnail);
        }
    }
}