package org.milaifontanals.musicapp.viewmodel;

import android.app.Application;
import android.media.metrics.Event;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import org.milaifontanals.musicapp.dao.AlbumDao;
import org.milaifontanals.musicapp.db.AppDatabase;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.model.Track;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlbumsViewModel extends AndroidViewModel {

    private AppDatabase db;
    private List<Album> albumList = new ArrayList<>();
    private Long id;
    private Album currentAlbum;
    public MutableLiveData<Boolean> insertDone = new MutableLiveData<>();

    public AlbumsViewModel(@NonNull Application application) {
        super(application);

        db = Room.databaseBuilder(application, AppDatabase.class, "db_musicapp.db").build();
        insertDone.setValue(false);
        /*initialInserts();*/
    }

    public LiveData<List<Album>> getSavedAlbums() {
        AlbumDao albumDao = db.albumDao();
        return albumDao.getAll();
    }

    public Album getAlbum(long id) {
        AlbumDao albumDao = db.albumDao();
        return albumDao.getAlbum(id);
    }

    public void initialInserts() {
        Album a1 = new Album(1L, "BBO", "Hoke", (short) 2022, "https://lastfm.freetls.fastly.net/i/u/770x0/1e9fb81bd814ed33dff0aeef21f296bf.jpg");
        Album a2 = new Album(2L, "Aladdin Sane", "David Bowie", (short) 1973, "https://lastfm.freetls.fastly.net/i/u/770x0/f861d629bcf17cde36d82b264486b34a.jpg");
        Album a3 = new Album(3L, "Random Access Memories", "Daft Punk", (short) 2013, "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.jpg");
        Album a4 = new Album(4L, "IGOR", "Tyler, The Creator", (short) 2019, "https://lastfm.freetls.fastly.net/i/u/770x0/e150fa362c89b8f1d92d883ae828b7ef.jpg");
        Album a5 = new Album(5L, "Drunk", "Thundercat", (short) 2017, "https://lastfm.freetls.fastly.net/i/u/770x0/17311ac4702bbc6245e9ee2958630c8f.jpg");
        Album a6 = new Album(6L, "The Slow Rush", "Tame Impala", (short) 2020, "https://lastfm.freetls.fastly.net/i/u/770x0/832ade6a35ec2a224ea9a5be326b5de4.jpg");

        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track(1L, 1, "Ojo de Halcón", 110000, false));
        tracks.add(new Track(1L, 2, "Five O", 140000, false));
        tracks.add(new Track(1L, 3, "Chorbo Real", 182000, false));
        tracks.add(new Track(1L, 4, "Jjjj", 207000, false));
        tracks.add(new Track(1L, 5, "Speedrun Skit", 66000, false));
        tracks.add(new Track(1L, 6, "Desamparados", 197000, false));
        tracks.add(new Track(1L, 7, "Medallones", 150000, false));
        tracks.add(new Track(1L, 8, "TT", 121000, false));
        tracks.add(new Track(1L, 9, "Automático", 154000, false));
        tracks.add(new Track(1L, 10, "Olympique", 152000, false));
        tracks.add(new Track(1L, 11, "Santo", 92000, false));
        a1.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(2L, 1, "Watch That Man", 270000, false));
        tracks.add(new Track(2L, 2, "Aladdin Sane", 315000, false));
        tracks.add(new Track(2L, 3, "Drive-In Saturday", 278000, false));
        tracks.add(new Track(2L, 4, "Panic in Detroit", 270000, false));
        tracks.add(new Track(2L, 5, "Cracked Actor", 181000, false));
        tracks.add(new Track(2L, 6, "Time", 310000, false));
        tracks.add(new Track(2L, 7, "The Prettiest Star", 208000, false));
        tracks.add(new Track(2L, 8, "Let's Spend the Night Together", 190000, false));
        tracks.add(new Track(2L, 9, "The Jean Genie", 246000, false));
        tracks.add(new Track(2L, 10, "Lady Grinning Soul", 233000, false));
        a2.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(3L, 1, "Give Life Back to Music", 274000, false));
        tracks.add(new Track(3L, 2, "The Game of Love", 321000, false));
        tracks.add(new Track(3L, 3, "Giorgio by Moroder", 544000, false));
        tracks.add(new Track(3L, 4, "Within", 228000, false));
        tracks.add(new Track(3L, 5, "Instant Crush", 337000, false));
        tracks.add(new Track(3L, 6, "Lose Yourself to Dance", 353000, false));
        tracks.add(new Track(3L, 7, "Touch", 498000, false));
        tracks.add(new Track(3L, 8, "Get Lucky", 367000, false));
        tracks.add(new Track(3L, 9, "Beyond", 290000, false));
        tracks.add(new Track(3L, 10, "Motherboard", 341000, false));
        tracks.add(new Track(3L, 11, "Fragments of Time", 279000, false));
        tracks.add(new Track(3L, 12, "Doin' It Right", 251000, false));
        tracks.add(new Track(3L, 13, "Contact", 381000, false));
        a3.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(4L, 1, "Igor's Theme", 200000, false));
        tracks.add(new Track(4L, 2, "Earfquake", 190000, false));
        tracks.add(new Track(4L, 3, "I Think", 212000, false));
        tracks.add(new Track(4L, 4, "Exactly What You Run From You End Up Chasing", 14000, false));
        tracks.add(new Track(4L, 5, "Running Out of Time", 177000, false));
        tracks.add(new Track(4L, 6, "New Magic Wand", 195000, false));
        tracks.add(new Track(4L, 7, "A Boy Is a Gun", 210000, false));
        tracks.add(new Track(4L, 8, "Puppet", 179000, false));
        tracks.add(new Track(4L, 9, "What's Good", 205000, false));
        tracks.add(new Track(4L, 10, "Gone, Gone / Thank You", 375000, false));
        tracks.add(new Track(4L, 11, "I Don't Love You Anymore", 161000, false));
        tracks.add(new Track(4L, 12, "Are We Still Friends?", 265000, false));
        a4.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(5L, 1, "Rabbot Ho", 38000, false));
        tracks.add(new Track(5L, 2, "Captain Stupido", 101000, false));
        tracks.add(new Track(5L, 3, "Uh Uh", 136000, false));
        tracks.add(new Track(5L, 4, "Bus in These Streets", 144000, false));
        tracks.add(new Track(5L, 5, "A Fan's Mail (Tron Song II)", 158000, false));
        tracks.add(new Track(5L, 6, "Lava Lamp", 178000, false));
        tracks.add(new Track(5L, 7, "Jethro", 94000, false));
        tracks.add(new Track(5L, 8, "Day & Night", 37000, false));
        tracks.add(new Track(5L, 9, "Show You the Way", 214000, false));
        tracks.add(new Track(5L, 10, "Walk On By", 199000, false));
        tracks.add(new Track(5L, 11, "Blackkk", 119000, false));
        tracks.add(new Track(5L, 12, "Tokyo", 144000, false));
        tracks.add(new Track(5L, 13, "Jameel's Space Ride", 69000, false));
        tracks.add(new Track(5L, 14, "Friend Zone", 192000, false));
        tracks.add(new Track(5L, 15, "Them Changes", 188000, false));
        tracks.add(new Track(5L, 16, "Where I'm Going", 129000, false));
        tracks.add(new Track(5L, 17, "Drink Dat", 215000, false));
        tracks.add(new Track(5L, 18, "Inferno", 240000, false));
        tracks.add(new Track(5L, 19, "I Am Crazy", 25000, false));
        tracks.add(new Track(5L, 20, "3AM", 75000, false));
        tracks.add(new Track(5L, 21, "Drunk", 102000, false));
        tracks.add(new Track(5L, 22, "The Turn Down", 149000, false));
        tracks.add(new Track(5L, 23, "DUI", 138000, false));
        a5.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(6L, 1, "One More Year", 322000, false));
        tracks.add(new Track(6L, 2, "Instant Destiny", 193000, false));
        tracks.add(new Track(6L, 3, "Borderline", 238000, false));
        tracks.add(new Track(6L, 4, "Posthumous Forgiveness", 366000, false));
        tracks.add(new Track(6L, 5, "Breathe Deeper", 372000, false));
        tracks.add(new Track(6L, 6, "Tomorrow’s Dust", 326000, false));
        tracks.add(new Track(6L, 7, "On Track", 301000, false));
        tracks.add(new Track(6L, 8, "Lost in Yesterday", 249000, false));
        tracks.add(new Track(6L, 9, "Is It True", 238000, false));
        tracks.add(new Track(6L, 10, "It Might Be Time", 273000, false));
        tracks.add(new Track(6L, 11, "Glimmer", 128000, false));
        tracks.add(new Track(6L, 12, "One More Hour", 433000, false));
        a6.setTrackList(tracks);

        AlbumDao albumDao = db.albumDao();

        this.insert(a1);
        this.insert(a2);
        this.insert(a3);
        this.insert(a4);
        this.insert(a5);
        this.insert(a6);

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
}
