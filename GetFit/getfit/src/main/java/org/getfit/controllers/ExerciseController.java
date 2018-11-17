package org.getfit.controllers;

import com.google.gson.Gson;
import org.getfit.cloud.CloudImageExtractor;
import org.getfit.models.viewModels.ExerciseViewModel;
import org.getfit.services.ExerciseService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    private final Gson gson;

    private final CloudImageExtractor cloudImageExtractor;

    public ExerciseController(ExerciseService exerciseService, Gson gson, CloudImageExtractor cloudImageExtractor) {
        this.exerciseService = exerciseService;
        this.gson = gson;
        this.cloudImageExtractor = cloudImageExtractor;
    }

    @GetMapping(value = "/all", produces = "application/json")
    @CachePut("result")
    public @ResponseBody
    String[] allExercises(Principal principal) throws IOException {
        String[] result = new String[2];
        List<ExerciseViewModel> allExercises = this.exerciseService.getAllExercises();
        result[0] = this.gson.toJson(allExercises);
        result[1] = this.gson.toJson(this.cloudImageExtractor.getAllImages(principal.getName()));

        return result;
    }


    @GetMapping(value = "/details", produces = "application/json")
    public @ResponseBody
    String details(@RequestParam String exercisename) {
        ExerciseViewModel exerciseByName = this.exerciseService.getExerciseByName(exercisename);

        return this.gson
                .toJson(exerciseByName);
    }


}
