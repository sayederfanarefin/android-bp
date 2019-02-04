package info.sayederfanarefin.meisterkoch.model;

/**
 * Created by erfan on 30/06/17.
 */
public class users {

    private String coverPicLocation;

    private String uid;
    private String username;
    private String email;
    private String profileImageUrl;

    public String getCoverPicLocation() {
        return coverPicLocation;
    }

    public void setCoverPicLocation(String coverPicLocation) {
        this.coverPicLocation = coverPicLocation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
