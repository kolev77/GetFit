package org.getfit.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements UserDetails {
    protected static final String ROLE_PREFIX = "ROLE_";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;


    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Image profilePicture;

    @Column(name = "description")
    private String description;

    // Profile picture data could be in another entity IMAGE
    @Column(name = "profilePictureFileName")
    private String profilePictureFileName;

    @Column(name = "profilePictureFileId")
    private String profilePictureFileId;

    // FACEBOOK, INSTAGRAM PROFILE

    // birthdate

    // profile picture

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
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
}
