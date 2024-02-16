package org.milaifontanals.musicapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.milaifontanals.musicapp.model.Album;

import java.util.List;

@Dao
public interface AlbumDao {

    @Query("SELECT * FROM album")
    LiveData<List<Album>> getAll();

    @Query("SELECT * FROM album WHERE id=:albumId")
    Album getAlbum(String albumId);

    @Insert
    void insertAll(Album... albums);

    @Update
    void update(Album a);

    @Delete
    void delete(Album album);
}
