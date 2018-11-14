package org.getfit.services;

import org.getfit.models.bindingModels.RegisterExerciseBindingModel;
import org.getfit.models.viewModels.ExerciseViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ExerciseService extends UserDetailsService {
    boolean exerciseExists(String name);

    boolean save(RegisterExerciseBindingModel exerciseBindingModel);

    ExerciseViewModel getExerciseById(String id);

    ExerciseViewModel getExerciseByName(String name);
}
