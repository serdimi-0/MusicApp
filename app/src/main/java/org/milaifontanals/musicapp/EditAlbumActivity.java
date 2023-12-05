package org.milaifontanals.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.milaifontanals.musicapp.model.Album;

public class EditAlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_edit);

        Intent i = getIntent();
        int albumId = (int)i.getLongExtra("albumId",-1);
        Album currentAlbum = Album.getAlbumList().stream().filter(obj -> obj.getId() == albumId).findFirst().orElse(null);

        ((ImageView)findViewById(R.id.albumEditImg)).setImageBitmap(currentAlbum.getImgBitmap());
        ((EditText)findViewById(R.id.albumEditName)).setText(currentAlbum.getTitle());
        ((EditText)findViewById(R.id.albumEditArtist)).setText(currentAlbum.getArtist());


        setSupportActionBar(findViewById(R.id.albumEditToolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
