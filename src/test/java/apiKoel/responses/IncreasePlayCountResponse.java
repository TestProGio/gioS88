package apiKoel.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IncreasePlayCountResponse {
    @JsonProperty("song_id")
    private String songId;

    @JsonProperty("liked")
    private boolean liked;

    @JsonProperty("play_count")
    private int playCount;

    @JsonProperty("song")
    private SongResponse song;

    @JsonProperty("user")
    private UserDetailsResponse user;

    // Getters and Setters
    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public SongResponse getSong() {
        return song;
    }

    public void setSong(SongResponse song) {
        this.song = song;
    }

    public UserDetailsResponse getUser() {
        return user;
    }

    public void setUser(UserDetailsResponse user) {
        this.user = user;
    }
}



