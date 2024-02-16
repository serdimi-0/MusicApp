package org.milaifontanals.musicapp.viewmodel;

import android.app.Application;
import android.media.metrics.Event;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import org.milaifontanals.musicapp.dao.AlbumDao;
import org.milaifontanals.musicapp.dao.TrackDao;
import org.milaifontanals.musicapp.db.AppDatabase;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.model.Track;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlbumsViewModel extends AndroidViewModel {

    private AppDatabase db;
    private List<Album> albumList = new ArrayList<>();
    private List<Album> albumDownloadList = new ArrayList<>();
    private Long id;
    private Album currentAlbum;
    private String currentArtist;
    private Track currentTrack;
    public MutableLiveData<Boolean> insertDone = new MutableLiveData<>();

    public AlbumsViewModel(@NonNull Application application) {
        super(application);

        db = Room.databaseBuilder(application, AppDatabase.class, "db_musicapp.db").build();
        insertDone.setValue(false);
        /*initialInserts();*/
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
    }

    public String getCurrentArtist() {
        return currentArtist;
    }

    public void setCurrentArtist(String currentArtist) {
        this.currentArtist = currentArtist;
    }

    public List<Album> getAlbumDownloadList() {
        return albumDownloadList;
    }

    public void setAlbumDownloadList(List<Album> albumDownloadList) {
        this.albumDownloadList = albumDownloadList;
    }

    public LiveData<List<Album>> getSavedAlbums() {
        AlbumDao albumDao = db.albumDao();
        return albumDao.getAll();
    }

    public Album getAlbum(String id) {
        AlbumDao albumDao = db.albumDao();
        return albumDao.getAlbum(id);
    }

    public LiveData<List<Track>> getTracks(Album a) {
        TrackDao trackDao = db.trackDao();
        return trackDao.getTracksFromAlbum(a.getId());
    }


    public void insert(Album album) {

        Observable.fromCallable(() -> {
            AlbumDao albumDao = db.albumDao();
            albumDao.insertAll(album);
            for (Track track : album.getTrackList()) {
                db.trackDao().insertAll(track);
            }
            insertDone.postValue(true);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void deleteAlbum(Album album) {
        Observable.fromCallable(() -> {
            AlbumDao albumDao = db.albumDao();
            TrackDao trackDao = db.trackDao();
            trackDao.deleteAllFromAlbum(album.getId());
            albumDao.delete(album);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Album getCurrentAlbum() {
        return currentAlbum;
    }

    public void setCurrentAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }

    public void initialInserts() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Album a1;
        Album a2;
        Album a3;
        Album a4;
        Album a5;
        Album a6;
        try {
            a1 = new Album("1","BBO", "Hoke", sdf.parse("2022-01-01"), "https://lastfm.freetls.fastly.net/i/u/770x0/1e9fb81bd814ed33dff0aeef21f296bf.jpg");
            a2 = new Album("2","Aladdin Sane", "David Bowie", sdf.parse("1973-01-01"), "https://lastfm.freetls.fastly.net/i/u/770x0/f861d629bcf17cde36d82b264486b34a.jpg");
            a3 = new Album("3","Random Access Memories", "Daft Punk", sdf.parse("2013-01-01"), "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.jpg");
            a4 = new Album("4","IGOR", "Tyler, The Creator", sdf.parse("2019-01-01"), "https://lastfm.freetls.fastly.net/i/u/770x0/e150fa362c89b8f1d92d883ae828b7ef.jpg");
            a5 = new Album("5","Drunk", "Thundercat", sdf.parse("2017-01-01"), "https://lastfm.freetls.fastly.net/i/u/770x0/17311ac4702bbc6245e9ee2958630c8f.jpg");
            a6 = new Album("6","The Slow Rush", "Tame Impala", sdf.parse("2020-01-01"), "https://lastfm.freetls.fastly.net/i/u/770x0/832ade6a35ec2a224ea9a5be326b5de4.jpg");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track("1", 1, "Ojo de Halcón", 110000, false));
        tracks.add(new Track("1", 2, "Five O", 140000, false));
        tracks.add(new Track("1", 3, "Chorbo Real", 182000, false));
        tracks.add(new Track("1", 4, "Jjjj", 207000, false));
        tracks.add(new Track("1", 5, "Speedrun Skit", 66000, false));
        tracks.add(new Track("1", 6, "Desamparados", 197000, false));
        tracks.add(new Track("1", 7, "Medallones", 150000, false));
        tracks.add(new Track("1", 8, "TT", 121000, false));
        tracks.add(new Track("1", 9, "Automático", 154000, false));
        tracks.add(new Track("1", 10, "Olympique", 152000, false));
        tracks.add(new Track("1", 11, "Santo", 92000, false));
        a1.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track("2", 1, "Watch That Man", 270000, false));
        tracks.add(new Track("2", 2, "Aladdin Sane", 315000, false));
        tracks.add(new Track("2", 3, "Drive-In Saturday", 278000, false));
        tracks.add(new Track("2", 4, "Panic in Detroit", 270000, false));
        tracks.add(new Track("2", 5, "Cracked Actor", 181000, false));
        tracks.add(new Track("2", 6, "Time", 310000, false));
        tracks.add(new Track("2", 7, "The Prettiest Star", 208000, false));
        tracks.add(new Track("2", 8, "Let's Spend the Night Together", 190000, false));
        tracks.add(new Track("2", 9, "The Jean Genie", 246000, false));
        tracks.add(new Track("2", 10, "Lady Grinning Soul", 233000, false));
        a2.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track("3", 1, "Give Life Back to Music", 274000, false));
        tracks.add(new Track("3", 2, "The Game of Love", 321000, false));
        tracks.add(new Track("3", 3, "Giorgio by Moroder", 544000, false));
        tracks.add(new Track("3", 4, "Within", 228000, false));
        tracks.add(new Track("3", 5, "Instant Crush", 337000, false));
        tracks.add(new Track("3", 6, "Lose Yourself to Dance", 353000, false));
        tracks.add(new Track("3", 7, "Touch", 498000, false));
        tracks.add(new Track("3", 8, "Get Lucky", 367000, false));
        tracks.add(new Track("3", 9, "Beyond", 290000, false));
        tracks.add(new Track("3", 10, "Motherboard", 341000, false));
        tracks.add(new Track("3", 11, "Fragments of Time", 279000, false));
        tracks.add(new Track("3", 12, "Doin' It Right", 251000, false));
        tracks.add(new Track("3", 13, "Contact", 381000, false));
        a3.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track("4", 1, "Igor's Theme", 200000, false));
        tracks.add(new Track("4", 2, "Earfquake", 190000, false));
        tracks.add(new Track("4", 3, "I Think", 212000, false));
        tracks.add(new Track("4", 4, "Exactly What You Run From You End Up Chasing", 14000, false));
        tracks.add(new Track("4", 5, "Running Out of Time", 177000, false));
        tracks.add(new Track("4", 6, "New Magic Wand", 195000, false));
        tracks.add(new Track("4", 7, "A Boy Is a Gun", 210000, false));
        tracks.add(new Track("4", 8, "Puppet", 179000, false));
        tracks.add(new Track("4", 9, "What's Good", 205000, false));
        tracks.add(new Track("4", 10, "Gone, Gone / Thank You", 375000, false));
        tracks.add(new Track("4", 11, "I Don't Love You Anymore", 161000, false));
        tracks.add(new Track("4", 12, "Are We Still Friends?", 265000, false));
        a4.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track("5", 1, "Rabbot Ho", 38000, false));
        tracks.add(new Track("5", 2, "Captain Stupido", 101000, false));
        tracks.add(new Track("5", 3, "Uh Uh", 136000, false));
        tracks.add(new Track("5", 4, "Bus in These Streets", 144000, false));
        tracks.add(new Track("5", 5, "A Fan's Mail (Tron Song II)", 158000, false));
        tracks.add(new Track("5", 6, "Lava Lamp", 178000, false));
        tracks.add(new Track("5", 7, "Jethro", 94000, false));
        tracks.add(new Track("5", 8, "Day & Night", 37000, false));
        tracks.add(new Track("5", 9, "Show You the Way", 214000, false));
        tracks.add(new Track("5", 10, "Walk On By", 199000, false));
        tracks.add(new Track("5", 11, "Blackkk", 119000, false));
        tracks.add(new Track("5", 12, "Tokyo", 144000, false));
        tracks.add(new Track("5", 13, "Jameel's Space Ride", 69000, false));
        tracks.add(new Track("5", 14, "Friend Zone", 192000, false));
        tracks.add(new Track("5", 15, "Them Changes", 188000, false));
        tracks.add(new Track("5", 16, "Where I'm Going", 129000, false));
        tracks.add(new Track("5", 17, "Drink Dat", 215000, false));
        tracks.add(new Track("5", 18, "Inferno", 240000, false));
        tracks.add(new Track("5", 19, "I Am Crazy", 25000, false));
        tracks.add(new Track("5", 20, "3AM", 75000, false));
        tracks.add(new Track("5", 21, "Drunk", 102000, false));
        tracks.add(new Track("5", 22, "The Turn Down", 149000, false));
        tracks.add(new Track("5", 23, "DUI", 138000, false));
        a5.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track("6", 1, "One More Year", 322000, false));
        tracks.add(new Track("6", 2, "Instant Destiny", 193000, false));
        tracks.add(new Track("6", 3, "Borderline", 238000, false));
        tracks.add(new Track("6", 4, "Posthumous Forgiveness", 366000, false));
        tracks.add(new Track("6", 5, "Breathe Deeper", 372000, false));
        tracks.add(new Track("6", 6, "Tomorrow’s Dust", 326000, false));
        tracks.add(new Track("6", 7, "On Track", 301000, false));
        tracks.add(new Track("6", 8, "Lost in Yesterday", 249000, false));
        tracks.add(new Track("6", 9, "Is It True", 238000, false));
        tracks.add(new Track("6", 10, "It Might Be Time", 273000, false));
        tracks.add(new Track("6", 11, "Glimmer", 128000, false));
        tracks.add(new Track("6", 12, "One More Hour", 433000, false));
        a6.setTrackList(tracks);

        AlbumDao albumDao = db.albumDao();

        this.insert(a1);
        this.insert(a2);
        this.insert(a3);
        this.insert(a4);
        this.insert(a5);
        this.insert(a6);

    }

    public void updateAlbum(Album currentAlbum) {
        Observable.fromCallable(() -> {
            AlbumDao albumDao = db.albumDao();
            albumDao.update(currentAlbum);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void updateTrack(Track currentTrack) {
        Observable.fromCallable(() -> {
            TrackDao trackDao = db.trackDao();
            trackDao.update(currentTrack);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void insertAlbum(Album album) {
        Observable.fromCallable(() -> {
            AlbumDao albumDao = db.albumDao();
            albumDao.insertAll(album);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void insertTrack(Track track) {
        Observable.fromCallable(() -> {
            TrackDao trackDao = db.trackDao();
            trackDao.insertAll(track);
            return true;
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
