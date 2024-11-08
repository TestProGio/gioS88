package apiKoel.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SongDetailsResponse {

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
