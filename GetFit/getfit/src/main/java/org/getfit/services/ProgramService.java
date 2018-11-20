package org.getfit.services;

import org.getfit.models.bindingModels.ProgramBindingModel;
import org.getfit.models.bindingModels.TrainingBindingModel;

public interface ProgramService {
    boolean programExists(String name);

    boolean save(ProgramBindingModel programBindingModel, TrainingBindingModel trainingBindingModel);


//    ExerciseViewModel getExerciseById(String id);
//
//    ExerciseViewModel getExerciseByName(String name);
//
//    List<ExerciseViewModel> getAllExercises();
}
