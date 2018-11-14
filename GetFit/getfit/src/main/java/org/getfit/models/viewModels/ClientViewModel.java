package org.getfit.models.viewModels;

import java.util.ArrayList;
import java.util.List;

public class ClientViewModel {
    private String username;

    private String email;

    private String phoneNumber;

    private String description;

    private Double height;

    private Double weight;

    private String profilePictureFileName;

    private String profilePictureFileId;

    private List<String> coachesNames;

    public ClientViewModel() {
        this.coachesNames = new ArrayList<>();
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getProfilePictureFileName() {
        return profilePictureFileName;
    }

    public void setProfilePictureFileName(String profilePictureFileName) {
        this.profilePictureFileName = profilePictureFileName;
    }

    public String getProfilePictureFileId() {
        return profilePictureFileId;
    }

    public void setProfilePictureFileId(String profilePictureFileId) {
        this.profilePictureFileId = profilePictureFileId;
    }

    public List<String> getCoachesNames() {
        return coachesNames;
    }

    public void setCoachesNames(List<String> coachesNames) {
        this.coachesNames = coachesNames;
    }
}
