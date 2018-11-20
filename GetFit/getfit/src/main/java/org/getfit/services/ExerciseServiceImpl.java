package org.getfit.services;

import org.getfit.entities.Exercise;
import org.getfit.models.bindingModels.RegisterExerciseBindingModel;
import org.getfit.models.viewModels.ExerciseViewModel;
import org.getfit.repositories.ExerciseRepository;
import org.modelmapper.ModelMapper;
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
        if (this.exerciseRepository.findByExerciseName(exerciseBindingModel.getExerciseName()) != null) {
            return false;
        }

        Exercise exercise = this.modelMapper.map(exerciseBindingModel, Exercise.class);


        this.exerciseRepository.save(exercise);
        return true;
    }

    @Override
    public ExerciseViewModel getExerciseById(String id) {
        return null;
    }

    @Override
    public ExerciseViewModel getExerciseByName(String name) {
        Exercise byExerciseName = this.exerciseRepository.findByExerciseName(name);

        return this.modelMapper.map(byExerciseName, ExerciseViewModel.class);
    }

    @Override
    public List<ExerciseViewModel> getAllExercises() {
        List<Exercise> all = this.exerciseRepository.findAll();
        List<ExerciseViewModel> exerciseViewModels = new ArrayList<>();

        for (Exercise exercise : all) {
            ExerciseViewModel viewModel = modelMapper.map(exercise, ExerciseViewModel.class);
            exerciseViewModels.add(viewModel);
        }
        return exerciseViewModels;
    }

    @Override
    public List<ExerciseViewModel> getAllExercisesByMuscleGroup(String muscleGroup) {
        List<Exercise> entities = this.exerciseRepository.getAllByMuscleGroup(muscleGroup);
        List<ExerciseViewModel> exerciseViewModels = new ArrayList<>();

        for (Exercise exercise : entities) {
            ExerciseViewModel viewModel = modelMapper.map(exercise, ExerciseViewModel.class);
            exerciseViewModels.add(viewModel);
        }
        return exerciseViewModels;
    }
}
