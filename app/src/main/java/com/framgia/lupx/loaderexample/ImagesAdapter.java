package com.framgia.lupx.loaderexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pham.xuan.lu on 1/26/16.
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ImageItem> items;

    public ImagesAdapter(Context context, List<ImageItem> items) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_image_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageItem item = items.get(position);
        holder.txtBucketId.setText(String.valueOf(item.bucket_id));
        holder.txtBucketDisplayName.setText(item.bucket_display_name);
        holder.txtDisplayName.setText(item.bucket_name);
        holder.txtPath.setText(item.path);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBucketId;
        private TextView txtBucketDisplayName;
        private TextView txtDisplayName;
        private TextView txtPath;

        public ImageViewHolder(View view) {
            super(view);
            txtBucketId = (TextView) view.findViewById(R.id.txtBucketId);
            txtBucketDisplayName = (TextView) view.findViewById(R.id.txtBucketDisplayName);
            txtDisplayName = (TextView) view.findViewById(R.id.txtDisplayName);
            txtPath = (TextView) view.findViewById(R.id.txtPath);
        }
    }
}