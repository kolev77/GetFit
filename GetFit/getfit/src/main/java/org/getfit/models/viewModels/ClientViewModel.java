package org.getfit.models.viewModels;

import java.util.ArrayList;
import java.util.List;

public class ClientViewModel extends UserViewModel {

    private Double height;

    private Double weight;

    private String profilePictureFileName;

    private String profilePictureFileId;

    private List<String> coachesNames;

    public ClientViewModel() {
        this.coachesNames = new ArrayList<>();
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
