package org.getfit.models.viewModels;


import java.util.ArrayList;
import java.util.List;

public class CoachViewModel extends UserViewModel {

    private String certificates;

    private String profilePictureFileName;

    private String profilePictureFileId;

    private List<String> clientsNames;

    private Integer subscribers;

    public CoachViewModel() {
        this.clientsNames = new ArrayList<>();
    }

    public String getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
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

    public List<String> getClientsNames() {
        return clientsNames;
    }

    public void setClientsNames(List<String> clientsNames) {
        this.clientsNames = clientsNames;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }
}
