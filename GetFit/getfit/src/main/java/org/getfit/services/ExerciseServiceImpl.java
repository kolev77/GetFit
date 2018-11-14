package org.getfit.services;

import org.getfit.entities.Exercise;
import org.getfit.models.bindingModels.RegisterExerciseBindingModel;
import org.getfit.models.viewModels.ExerciseViewModel;
import org.getfit.repositories.ExerciseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;

    private final ModelMapper modelMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ModelMapper modelMapper) {
        this.exerciseRepository = exerciseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean exerciseExists(String name) {
        return this.exerciseRepository.findByExerciseName(name) != null;
    }

    @Override
    public boolean save(RegisterExerciseBindingModel exerciseBindingModel) {
        Exercise exercise = this.modelMapper.map(exerciseBindingModel,Exercise.class);
        System.out.println(exercise);
        System.out.println("test");

        this.exerciseRepository.save(exercise);
        return true;
    }

    @Override
    public ExerciseViewModel getExerciseById(String id) {
        return null;
    }

    @Override
    public ExerciseViewModel getExerciseByName(String name) {
        return null;
    }

    @Override
    public List<ExerciseViewModel> getAllExercises() {
        List<Exercise> all = this.exerciseRepository.findAll();
        List<ExerciseViewModel> exerciseViewModels = new ArrayList<>();

        for (Exercise exercise : all) {
            ExerciseViewModel viewModel = modelMapper.map(exercise,ExerciseViewModel.class);
            exerciseViewModels.add(viewModel);
        }
        return exerciseViewModels;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
