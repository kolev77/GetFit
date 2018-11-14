package org.getfit.models.viewModels;

public class ExerciseViewModel {
    private String name;

    private String creatorName;

    private String description;

    private String levelOfDifficulty;

    private String muscleGroup;

    private String photosInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevelOfDifficulty() {
        return levelOfDifficulty;
    }

    public void setLevelOfDifficulty(String levelOfDifficulty) {
        this.levelOfDifficulty = levelOfDifficulty;
    }

    public String getPhotosInfo() {
        return photosInfo;
    }

    public void setPhotosInfo(String photosInfo) {
        this.photosInfo = photosInfo;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
}
