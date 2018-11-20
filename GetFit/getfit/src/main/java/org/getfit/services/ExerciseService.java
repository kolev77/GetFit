package org.getfit.services;

import org.getfit.models.bindingModels.RegisterExerciseBindingModel;
import org.getfit.models.viewModels.ExerciseViewModel;

import java.util.List;

public interface ExerciseService {
    boolean exerciseExists(String name);

    boolean save(RegisterExerciseBindingModel exerciseBindingModel);

    ExerciseViewModel getExerciseById(String id);

    ExerciseViewModel getExerciseByName(String name);

    List<ExerciseViewModel> getAllExercises();

    List<ExerciseViewModel> getAllExercisesByMuscleGroup(String muscleGroup);
}
