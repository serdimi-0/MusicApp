package org.milaifontanals.musicapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.milaifontanals.musicapp.model.Track;

import java.util.List;

@Dao
public interface TrackDao {

    @Query("SELECT * FROM track WHERE album_id = :albumId")
    LiveData<List<Track>> getTracksFromAlbum(String albumId);

    @Insert
    void insertAll(Track... tracks);

    @Delete
    void delete(Track track);

    @Update
    void update(Track track);
}
