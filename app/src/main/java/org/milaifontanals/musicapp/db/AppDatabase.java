package org.milaifontanals.musicapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.milaifontanals.musicapp.dao.AlbumDao;
import org.milaifontanals.musicapp.dao.TrackDao;
import org.milaifontanals.musicapp.model.*;

@Database(entities = {Album.class, Track.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();
    public abstract TrackDao trackDao();
}
