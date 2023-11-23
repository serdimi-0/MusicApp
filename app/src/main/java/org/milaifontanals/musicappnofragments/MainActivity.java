package org.milaifontanals.musicappnofragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicappnofragments.adapter.AlbumAdapter;
import org.milaifontanals.musicappnofragments.model.Album;
import org.milaifontanals.musicappnofragments.model.Track;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AlbumAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(2048)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(conf);

        Album.generateAlbumList();

        albumAdapter = new AlbumAdapter(Album.getAlbumList(), this);
        RecyclerView rcyAlbums = findViewById(R.id.rcyAlbums);
        rcyAlbums.setLayoutManager(new GridLayoutManager(this, 2));
        rcyAlbums.addItemDecoration(new GridSpacingItemDecoration(2,50,true));
        rcyAlbums.setHasFixedSize(true);
        rcyAlbums.setAdapter(albumAdapter);


    }
}