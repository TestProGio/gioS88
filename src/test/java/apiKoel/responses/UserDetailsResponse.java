package apiKoel.responses;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetailsResponse {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("is_admin")
    private boolean isAdmin;

    @JsonProperty("preferences")
    private PreferencesResponse preferences;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public PreferencesResponse getPreferences() {
        return preferences;
    }

    public void setPreferences(PreferencesResponse preferences) {
        this.preferences = preferences;
    }


}

