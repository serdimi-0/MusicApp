package org.milaifontanals.musicapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import org.milaifontanals.musicapp.dao.AlbumDao;
import org.milaifontanals.musicapp.dao.TrackDao;
import org.milaifontanals.musicapp.model.*;
import org.milaifontanals.musicapp.utils.Converters;

@Database(entities = {Album.class, Track.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();
    public abstract TrackDao trackDao();
}
