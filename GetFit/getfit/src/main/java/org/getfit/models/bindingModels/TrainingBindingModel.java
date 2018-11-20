package org.getfit.models.bindingModels;

import org.getfit.entities.Exercise;

import java.util.List;

public class TrainingBindingModel {
    private String name;

    private List<Exercise> exercises;

    private String additionalInfo;

    public TrainingBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
