package apiKoel.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PreferencesResponse {
    @JsonProperty("lastfm_session_key")
    private String lastfm_session_key;

    // Getters and Setters
    public String getLastfm_session_key() {
        return lastfm_session_key; }
    public void setLastfm_session_key(String lastfm_session_key) {
        this.lastfm_session_key = lastfm_session_key; }
}


