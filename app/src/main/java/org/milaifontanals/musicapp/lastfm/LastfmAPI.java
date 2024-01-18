package org.milaifontanals.musicapp.lastfm;

import android.util.Log;

import org.json.*;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.model.Artist;
import org.milaifontanals.musicapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class LastfmAPI {

    public static List<Artist> getArtists(String name) {
        List<Artist> artists = new ArrayList<>();
        String URL = "https://ws.audioscrobbler.com/2.0/?method=artist.search&artist=" + name + "&api_key=778d0f1ed924dbc91244e1b8545b7a5c&format=json";
        String json = NetworkUtils.getJson(URL);

        JSONObject root = null;
        try {
            root = new JSONObject(json);
            JSONArray artistsArray = root.getJSONObject("results").getJSONObject("artistmatches").getJSONArray("artist");
            for (int i = 0; i < artistsArray.length(); i++) {
                artists.add(new Artist(artistsArray.getJSONObject(i).getString("name"),
                        artistsArray.getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text")));
                Log.d("TAG",artists.get(i).toString());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        return artists;
    }

    public static List<Album> getAlbums(String artistId) {
        String URL = "https://ws.audioscrobbler.com/2.0/?method=artist.getTopAlbums&mbid=" + artistId + "&api_key=778d0f1ed924dbc91244e1b8545b7a5c&format=json";
        return null;
    }
}
