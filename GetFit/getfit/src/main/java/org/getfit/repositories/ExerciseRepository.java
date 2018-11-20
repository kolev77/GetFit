package org.getfit.repositories;

import org.getfit.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {
    Exercise findByExerciseName(String name);

    List<Exercise> getAllByMuscleGroup(String muscleGroup);
}
