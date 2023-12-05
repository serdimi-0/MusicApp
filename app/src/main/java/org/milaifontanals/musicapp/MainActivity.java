package org.milaifontanals.musicapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.milaifontanals.musicapp.adapter.AlbumAdapter;
import org.milaifontanals.musicapp.model.Album;

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
        rcyAlbums.addItemDecoration(new GridSpacingItemDecoration(2, 50, true));
        rcyAlbums.setHasFixedSize(true);
        rcyAlbums.setAdapter(albumAdapter);

        findViewById(R.id.btnEditAlbum).setOnClickListener(e -> {

            Handler handler = new Handler(Looper.getMainLooper());

            Album selectedAlbum = albumAdapter.getList().get(albumAdapter.getSelectedIndex());
            Intent i = new Intent(this, EditAlbumActivity.class);
            i.putExtra("albumId", selectedAlbum.getId());
            this.startActivity(i);

            // Timeout para que no desaparezca la toolbar antes de llegar al Intent
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    albumAdapter.notifyItemChanged(albumAdapter.getSelectedIndex());
                    albumAdapter.setSelectedIndex(-1);
                    findViewById(R.id.albumToolbar).setVisibility(View.INVISIBLE);
                }
            }, 1000);

        });

        findViewById(R.id.btnRmAlbum).setOnClickListener(e -> {

            Album selectedAlbum = albumAdapter.getList().get(albumAdapter.getSelectedIndex());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Delete album");
            builder.setMessage("Are you sure you want to delete " + selectedAlbum.getTitle() + "?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Album.getAlbumList().remove(selectedAlbum);

                            albumAdapter.notifyItemRemoved(albumAdapter.getSelectedIndex());
                            albumAdapter.setSelectedIndex(-1);

                            findViewById(R.id.albumToolbar).animate().alpha(0f).withEndAction(() -> {
                                if (findViewById(R.id.albumToolbar) != null)
                                    findViewById(R.id.albumToolbar).setVisibility(View.INVISIBLE);
                            });
                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


        });
    }
}