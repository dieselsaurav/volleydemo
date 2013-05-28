package io.appstud.android.volleydemo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import io.appstud.android.volleydemo.R;
import io.appstud.android.volleydemo.helpers.BitmapLruCache;
import io.appstud.android.volleydemo.models.Track;

import java.util.List;

public class TrackAdapter extends ArrayAdapter<Track> {

    List<Track> mTracks;
    Activity mContext;
    ImageLoader mImageLoader;
    RequestQueue mRequestQueue;

    public TrackAdapter(Activity context, List<Track> tracks) {
        super(context, R.layout.list_item_track, tracks);
        mTracks = tracks;
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache());
    }

    static class ViewHolder {
        public TextView artistName;
        public TextView trackName;
        public NetworkImageView albumArt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            row = inflater.inflate(R.layout.list_item_track, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.artistName = (TextView) row.findViewById(R.id.tv_li_track_artist_name);
            viewHolder.trackName = (TextView) row.findViewById(R.id.tv_li_track_name);
            viewHolder.albumArt = (NetworkImageView) row.findViewById(R.id.iv_li_track);
            row.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) row.getTag();
        holder.artistName.setText(mTracks.get(position).artistName);
        holder.trackName.setText(mTracks.get(position).trackName);
        holder.albumArt.setImageUrl(mTracks.get(position).artworkUrl100, mImageLoader);

        return row;
    }
}
