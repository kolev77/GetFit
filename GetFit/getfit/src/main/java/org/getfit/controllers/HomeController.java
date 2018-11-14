package org.getfit.controllers;

import com.google.gson.Gson;
import org.getfit.cloud.CloudImageExtractor;
import org.getfit.models.viewModels.ExerciseViewModel;
import org.getfit.services.ClientService;
import org.getfit.services.CoachService;
import org.getfit.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class HomeController {
    private final String ROLE_COACH = "ROLE_COACH";
    private final String ROLE_CLIENT = "ROLE_CLIENT";
    private final ClientService clientService;
    private final CoachService coachService;
    private final ExerciseService exerciseService;
    private final Gson gson;
    private final CloudImageExtractor cloudImageExtractor;

    @Autowired
    public HomeController(ClientService clientService, CoachService coachService, ExerciseService exerciseService, Gson gson, CloudImageExtractor cloudImageExtractor) {
        this.clientService = clientService;
        this.coachService = coachService;
        this.exerciseService = exerciseService;
        this.gson = gson;
        this.cloudImageExtractor = cloudImageExtractor;
    }


    @GetMapping(value = "/home", produces = "application/json")
    @CachePut("result")
    public @ResponseBody
    String[] home(Principal principal) throws IOException {
        String[] result = new String[5];

        List<?> all = null;
        String role = ((Authentication) principal).getAuthorities().toArray()[0].toString();

        if (role.equals(ROLE_COACH)) {
            all = this.clientService.findAll();
            result[3] = this.coachService.getCoachByUsername(principal.getName().toString()).getProfilePictureFileName();
        } else if (role.equals(ROLE_CLIENT)) {
            all = this.coachService.findAll();
            result[3] = this.clientService.getClientByUsername(principal.getName().toString()).getProfilePictureFileName();
        }

        result[0] = this.gson.toJson(all);
        result[1] = "[{\"role\":\"" + role + "\"}]";
        result[2] = this.gson.toJson(this.cloudImageExtractor.getAllImages(principal.getName()));
        return result;
    }

    @GetMapping(value = "/exercises", produces = "application/json")
    @CachePut("result")
    public @ResponseBody
    String[] allExercises(Principal principal) throws IOException {
        String[] result = new String[2];
        List<ExerciseViewModel> allExercises = this.exerciseService.getAllExercises();
        result[0] = this.gson.toJson(allExercises);
        result[1] = this.gson.toJson(this.cloudImageExtractor.getAllImages(principal.getName()));

        return result;
    }

}
