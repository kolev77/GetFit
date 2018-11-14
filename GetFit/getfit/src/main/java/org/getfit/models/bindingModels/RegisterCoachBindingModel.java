package org.getfit.models.bindingModels;

import org.getfit.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class RegisterCoachBindingModel {
    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String description;

    private String certificates;

    private String profilePictureFileName;

    private String profilePictureFileId;

    private List<Client> clients;


    public RegisterCoachBindingModel() {
        this.clients = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
