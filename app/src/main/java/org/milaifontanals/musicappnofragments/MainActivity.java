package org.milaifontanals.musicappnofragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

        List<Album> albumList = new ArrayList<>();

        Album a1 = new Album(1L, "BBO", "Hoke", (short) 2022, "https://lastfm.freetls.fastly.net/i/u/770x0/1e9fb81bd814ed33dff0aeef21f296bf.jpg");
        Album a2 = new Album(2L, "Aladdin Sane", "David Bowie", (short) 1973, "https://lastfm.freetls.fastly.net/i/u/770x0/f861d629bcf17cde36d82b264486b34a.jpg");
        Album a3 = new Album(3L, "Random Access Memories", "Daft Punk", (short) 2013, "https://lastfm.freetls.fastly.net/i/u/770x0/11dd7e48a1f042c688bf54985f01d088.jpg");
        Album a4 = new Album(4L, "IGOR", "Tyler, The Creator", (short) 2019, "https://lastfm.freetls.fastly.net/i/u/770x0/e150fa362c89b8f1d92d883ae828b7ef.jpg");
        Album a5 = new Album(5L, "Drunk", "Thundercat", (short) 2017, "https://lastfm.freetls.fastly.net/i/u/770x0/17311ac4702bbc6245e9ee2958630c8f.jpg");
        Album a6 = new Album(6L, "The Slow Rush", "Tame Impala", (short) 2020, "https://lastfm.freetls.fastly.net/i/u/770x0/832ade6a35ec2a224ea9a5be326b5de4.jpg");

        ArrayList<Track> tracks = new ArrayList<Track>();
        tracks.add(new Track(1, "Ojo de Halcón", 110000));
        tracks.add(new Track(2, "Five O", 140000));
        tracks.add(new Track(3, "Chorbo Real", 182000));
        tracks.add(new Track(4, "Jjjj", 207000));
        tracks.add(new Track(5, "Speedrun Skit", 66000));
        tracks.add(new Track(6, "Desamparados", 197000));
        tracks.add(new Track(7, "Medallones", 150000));
        tracks.add(new Track(8, "TT", 121000));
        tracks.add(new Track(9, "Automático", 154000));
        tracks.add(new Track(10, "Olympique", 152000));
        tracks.add(new Track(11, "Santo", 92000));
        a1.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(1, "Watch That Man", 270000));
        tracks.add(new Track(2, "Aladdin Sane", 315000));
        tracks.add(new Track(3, "Drive-In Saturday", 278000));
        tracks.add(new Track(4, "Panic in Detroit", 270000));
        tracks.add(new Track(5, "Cracked Actor", 181000));
        tracks.add(new Track(6, "Time", 310000));
        tracks.add(new Track(7, "The Prettiest Star", 208000));
        tracks.add(new Track(8, "Let's Spend the Night Together", 190000));
        tracks.add(new Track(9, "The Jean Genie", 246000));
        tracks.add(new Track(10, "Lady Grinning Soul", 233000));
        a2.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(1, "Give Life Back to Music", 274000));
        tracks.add(new Track(2, "The Game of Love", 321000));
        tracks.add(new Track(3, "Giorgio by Moroder", 544000));
        tracks.add(new Track(4, "Within", 228000));
        tracks.add(new Track(5, "Instant Crush", 337000));
        tracks.add(new Track(6, "Lose Yourself to Dance", 353000));
        tracks.add(new Track(7, "Touch", 498000));
        tracks.add(new Track(8, "Get Lucky", 367000));
        tracks.add(new Track(9, "Beyond", 290000));
        tracks.add(new Track(10, "Motherboard", 341000));
        tracks.add(new Track(11, "Fragments of Time", 279000));
        tracks.add(new Track(12, "Doin' It Right", 251000));
        tracks.add(new Track(13, "Contact", 381000));
        a3.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(1, "Igor's Theme", 200000));
        tracks.add(new Track(2, "Earfquake", 190000));
        tracks.add(new Track(3, "I Think", 212000));
        tracks.add(new Track(4, "Exactly What You Run From You End Up Chasing", 14000));
        tracks.add(new Track(5, "Running Out of Time", 177000));
        tracks.add(new Track(6, "New Magic Wand", 195000));
        tracks.add(new Track(7, "A Boy Is a Gun", 210000));
        tracks.add(new Track(8, "Puppet", 179000));
        tracks.add(new Track(9, "What's Good", 205000));
        tracks.add(new Track(10, "Gone, Gone / Thank You", 375000));
        tracks.add(new Track(11, "I Don't Love You Anymore", 161000));
        tracks.add(new Track(12, "Are We Still Friends?", 265000));
        a4.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(1, "Rabbot Ho", 38000));
        tracks.add(new Track(2, "Captain Stupido", 101000));
        tracks.add(new Track(3, "Uh Uh", 136000));
        tracks.add(new Track(4, "Bus in These Streets", 144000));
        tracks.add(new Track(5, "A Fan's Mail (Tron Song II)", 158000));
        tracks.add(new Track(6, "Lava Lamp", 178000));
        tracks.add(new Track(7, "Jethro", 94000));
        tracks.add(new Track(8, "Day & Night", 37000));
        tracks.add(new Track(9, "Show You the Way", 214000));
        tracks.add(new Track(10, "Walk On By", 199000));
        tracks.add(new Track(11, "Blackkk", 119000));
        tracks.add(new Track(12, "Tokyo", 144000));
        tracks.add(new Track(13, "Jameel's Space Ride", 69000));
        tracks.add(new Track(14, "Friend Zone", 192000));
        tracks.add(new Track(15, "Them Changes", 188000));
        tracks.add(new Track(16, "Where I'm Going", 129000));
        tracks.add(new Track(17, "Drink Dat", 215000));
        tracks.add(new Track(18, "Inferno", 240000));
        tracks.add(new Track(19, "I Am Crazy", 25000));
        tracks.add(new Track(20, "3AM", 75000));
        tracks.add(new Track(21, "Drunk", 102000));
        tracks.add(new Track(22, "The Turn Down", 149000));
        tracks.add(new Track(23, "DUI", 138000));
        a5.setTrackList(tracks);

        tracks = new ArrayList<Track>();
        tracks.add(new Track(1, "One More Year", 322000));
        tracks.add(new Track(2, "Instant Destiny", 193000));
        tracks.add(new Track(3, "Borderline", 238000));
        tracks.add(new Track(4, "Posthumous Forgiveness", 366000));
        tracks.add(new Track(5, "Breathe Deeper", 372000));
        tracks.add(new Track(6, "Tomorrow’s Dust", 326000));
        tracks.add(new Track(7, "On Track", 301000));
        tracks.add(new Track(8, "Lost in Yesterday", 249000));
        tracks.add(new Track(9, "Is It True", 238000));
        tracks.add(new Track(10, "It Might Be Time", 273000));
        tracks.add(new Track(11, "Glimmer", 128000));
        tracks.add(new Track(12, "One More Hour", 433000));
        a6.setTrackList(tracks);

        albumList.add(a1);
        albumList.add(a2);
        albumList.add(a3);
        albumList.add(a4);
        albumList.add(a5);
        albumList.add(a6);

        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(2048)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        ImageLoader.getInstance().init(conf);

        albumAdapter = new AlbumAdapter(albumList, this);
        RecyclerView rcyAlbums = findViewById(R.id.rcyAlbums);
        rcyAlbums.setLayoutManager(new GridLayoutManager(this, 2));
        rcyAlbums.addItemDecoration(new GridSpacingItemDecoration(2,50,true));
        rcyAlbums.setHasFixedSize(true);
        rcyAlbums.setAdapter(albumAdapter);

    }
}