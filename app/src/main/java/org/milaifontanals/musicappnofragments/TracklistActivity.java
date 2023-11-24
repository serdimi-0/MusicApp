package org.milaifontanals.musicappnofragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicappnofragments.adapter.TrackAdapter;
import org.milaifontanals.musicappnofragments.model.Album;


public class TracklistActivity extends AppCompatActivity {

    TrackAdapter trackAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracklist);

        Intent i = getIntent();
        int albumId = (int)i.getLongExtra("albumId",-1);
        Album currentAlbum = Album.getAlbumList().stream().filter(obj -> obj.getId() == albumId).findFirst().orElse(null);

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(2048)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(conf);

        /*
        * Info del album
        * */

        ((TextView)findViewById(R.id.albumArtistTracklist)).setText(currentAlbum.getArtist());
        ((TextView)findViewById(R.id.albumTitleTracklist)).setText(currentAlbum.getTitle());
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(currentAlbum.getImgSrc(),(ImageView) findViewById(R.id.albumImgTracklist));

        /*
        * Tracklist
        * */

        setSupportActionBar(findViewById(R.id.tracklistToolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        trackAdapter = new TrackAdapter(currentAlbum.getTrackList(),this);
        RecyclerView rcyTracks = findViewById(R.id.rcyTracklist);
        rcyTracks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcyTracks.setHasFixedSize(true);
        rcyTracks.setAdapter(trackAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
