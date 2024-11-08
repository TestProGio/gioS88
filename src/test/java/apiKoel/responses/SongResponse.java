package apiKoel.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SongResponse {
    @JsonProperty("id")
    private String id;  // Added this field

    @JsonProperty("liked")
    private boolean liked;

    @JsonProperty("song_id")
    private String songId;

    @JsonProperty("play_count")
    private int playCount;

    @JsonProperty("song")
    private SongDetailsResponse songDetails;

    @JsonProperty("user")
    private UserDetailsResponse user;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public SongDetailsResponse getSongDetails() {
        return songDetails;
    }

    public void setSongDetails(SongDetailsResponse songDetails) {
        this.songDetails = songDetails;
    }

    public UserDetailsResponse getUser() {
        return user;
    }

    public void setUser(UserDetailsResponse user) {
        this.user = user;
    }
}

/*
//ORIGINAL
public class SongResponse {
    @JsonProperty("liked")
    private boolean liked;

    @JsonProperty("song_id")
    private String songId;

    @JsonProperty("play_count")
    private int playCount;

    @JsonProperty("song")
    private SongDetails song; // Nested SongDetails object

    @JsonProperty("user")
    private UserDetailsResponse user; // Nested UserDetails object

    // Getters and setters
    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public SongDetails getSong() {
        return song;
    }

    public void setSong(SongDetails song) {
        this.song = song;
    }

    public UserDetailsResponse getUser() {
        return user;
    }

    public void setUser(UserDetailsResponse user) {
        this.user = user;
    }

    // Nested class for Song Details
    public static class SongDetails {
        @JsonProperty("id")
        private String id;

        @JsonProperty("album_id")
        private int albumId;

        @JsonProperty("artist_id")
        private int artistId;

        @JsonProperty("title")
        private String title;

        @JsonProperty("length")
        private double length;

        @JsonProperty("track")
        private int track;

        @JsonProperty("disc")
        private int disc;

        @JsonProperty("created_at")
        private String createdAt;

        // Getters and setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getAlbumId() {
            return albumId;
        }

        public void setAlbumId(int albumId) {
            this.albumId = albumId;
        }

        public int getArtistId() {
            return artistId;
        }

        public void setArtistId(int artistId) {
            this.artistId = artistId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public int getTrack() {
            return track;
        }

        public void setTrack(int track) {
            this.track = track;
        }

        public int getDisc() {
            return disc;
        }

        public void setDisc(int disc) {
            this.disc = disc;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}

 */
