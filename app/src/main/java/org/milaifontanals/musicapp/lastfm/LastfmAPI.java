package org.milaifontanals.musicapp.lastfm;

import android.util.Log;

import org.json.*;
import org.milaifontanals.musicapp.model.Album;
import org.milaifontanals.musicapp.model.Artist;
import org.milaifontanals.musicapp.model.Track;
import org.milaifontanals.musicapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Date;
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
                String id;
                try {
                    id = artistsArray.getJSONObject(i).getString("mbid");
                    id = !id.equals("") ? id : null;
                } catch (JSONException e) {
                    id = null;
                }
                artists.add(new Artist(id, artistsArray.getJSONObject(i).getString("name"),
                        artistsArray.getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text")));
                Log.d("TAG", artists.get(i).toString());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        return artists;
    }

    public static List<Album> getAlbumsFromArtist(String artistId) {

        if (artistId == null) return null;

        List<Album> albums = new ArrayList<>();
        String URL = "https://ws.audioscrobbler.com/2.0/?method=artist.getTopAlbums&mbid=" + artistId + "&api_key=778d0f1ed924dbc91244e1b8545b7a5c&format=json";
        String json = NetworkUtils.getJson(URL);

        JSONObject root = null;
        try {
            root = new JSONObject(json);
            JSONArray albumsArray = root.getJSONObject("topalbums").getJSONArray("album");
            for (int i = 0; i < albumsArray.length(); i++) {
                String id;
                try {
                    id = albumsArray.getJSONObject(i).getString("mbid");
                    id = !id.equals("") ? id : null;
                } catch (JSONException e) {
                    id = null;
                }
                albums.add(new Album(id, albumsArray.getJSONObject(i).getString("name"),
                        albumsArray.getJSONObject(i).getJSONObject("artist").getString("name"), null,
                        albumsArray.getJSONObject(i).getJSONArray("image").getJSONObject(3).getString("#text")));
                Log.d("TAG", albums.get(i).toString());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return albums;
    }

    public static List<Album> getAlbumsFromName(String name) {
        List<Album> albums = new ArrayList<>();
        String URL = "https://ws.audioscrobbler.com/2.0/?method=album.search&album=" + name + "&api_key=778d0f1ed924dbc91244e1b8545b7a5c&format=json";
        String json = NetworkUtils.getJson(URL);

        JSONObject root = null;
        try {
            root = new JSONObject(json);
            JSONArray albumsArray = root.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album");
            for (int i = 0; i < albumsArray.length(); i++) {
                albums.add(new Album(albumsArray.getJSONObject(i).getString("mbid"), albumsArray.getJSONObject(i).getString("name"),
                        albumsArray.getJSONObject(i).getString("artist"), null,
                        albumsArray.getJSONObject(i).getJSONArray("image").getJSONObject(3).getString("#text")));
                Log.d("TAG", albums.get(i).toString());
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return albums;
    }

    public static Album getAlbumInfo(String artist, String album) {
        List<Album> albums = new ArrayList<>();
        String URL = "https://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=778d0f1ed924dbc91244e1b8545b7a5c&artist=" + artist + "&album=" + album + "&format=json";
        String json = NetworkUtils.getJson(URL);

        JSONObject root = null;
        try {
            root = new JSONObject(json);
            JSONObject albumObject = root.getJSONObject("album");
            String id;
            try {
                id = albumObject.getString("mbid");
                id = !id.equals("") ? id : String.valueOf((int) (Math.random() * 1000000));
            } catch (JSONException e) {
                id = String.valueOf((int) (Math.random() * 1000000));
            }
            List<Track> tracks = new ArrayList<>();
            try {
                JSONArray tracksArray = albumObject.getJSONObject("tracks").getJSONArray("track");
                for (int i = 0; i < tracksArray.length(); i++) {
                    JSONObject track = tracksArray.getJSONObject(i);
                    Track t = new Track(id, track.getJSONObject("@attr").getInt("rank"),
                            track.getString("name"), track.getInt("duration") * 1000, false);
                    tracks.add(t);
                    Log.d("TAG", t.toString());
                }
            } catch (JSONException e) {
                tracks = null;
            }
            Album a = new Album(id, albumObject.getString("name"),
                    albumObject.getString("artist"), new Date(),
                    albumObject.getJSONArray("image").getJSONObject(3).getString("#text"));
            a.setTrackList(tracks);

            return a;
        } catch (JSONException e) {
            return null;
        }
    }
}
