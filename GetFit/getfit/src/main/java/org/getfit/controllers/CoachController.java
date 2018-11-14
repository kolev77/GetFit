package org.getfit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.Response;
import org.getfit.cloud.CloudImageExtractor;
import org.getfit.cloud.CloudImageUploader;
import org.getfit.models.bindingModels.RegisterCoachBindingModel;
import org.getfit.models.bindingModels.RegisterExerciseBindingModel;
import org.getfit.models.viewModels.CoachViewModel;
import org.getfit.services.CoachService;
import org.getfit.services.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coach")
public class CoachController {

    private final CoachService coachService;

    private final ExerciseService exerciseService;

    private final Gson gson;

    private final CloudImageExtractor cloudImageExtractor;

    private final CloudImageUploader cloudImageUploader;

    public CoachController(CoachService coachService, ExerciseService exerciseService, Gson gson, CloudImageExtractor cloudImageExtractor, CloudImageUploader cloudImageUploader) {
        this.coachService = coachService;
        this.exerciseService = exerciseService;
        this.gson = gson;
        this.cloudImageExtractor = cloudImageExtractor;
        this.cloudImageUploader = cloudImageUploader;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public ResponseEntity<?> register(MultipartHttpServletRequest request) throws IOException {
        MultipartFile profilePicture = request.getFile("profilePicture");
        ObjectMapper mapper = new ObjectMapper();
        RegisterCoachBindingModel user = mapper.readValue(request.getParameter("userInfo"), RegisterCoachBindingModel.class);

        Response response = this.cloudImageUploader.uploadFile(profilePicture);

        String profilePictureFileName = response.request().url().queryParameterValues("filename").get(0);
        String profilePictureId = response.request().url().queryParameterValues("folderid").get(0);
        user.setProfilePictureFileName(profilePictureFileName);
        user.setProfilePictureFileId(profilePictureId);

        if (this.coachService.coachExists(user.getUsername())) {
            return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
        }

        if (this.coachService.save(user)) {
            return new ResponseEntity<>("Successfully registered user.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Something went wrong while processing your request.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/profile", produces = "application/json")
    public @ResponseBody
    String profile(@RequestParam String username) throws IOException {
        CoachViewModel coach = this.coachService.getCoachViewModelByUsername(username);
        return this.gson.toJson(coach);
    }


    @GetMapping(value = "/details", produces = "application/json")
    public @ResponseBody
    String details(@RequestParam String username) {
        CoachViewModel user = this.coachService.getCoachViewModelByUsername(username);

        this.coachService.getCoachByUsername(user.getUsername()).getClients().forEach(c -> user.getClientsNames().add(c.getUsername()));

        return this.gson
                .toJson(user);
    }

    @RequestMapping(value = "/create-exercise", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public ResponseEntity<?> createExercise(MultipartHttpServletRequest request) throws IOException {
        String exerciseInfo = request.getParameter("exerciseInfo");
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterExerciseBindingModel exerciseBindingModel = objectMapper.readValue(exerciseInfo, RegisterExerciseBindingModel.class);

        List<MultipartFile> images = new ArrayList<>() {{
            add(request.getFile("image1"));
            add(request.getFile("image2"));
            add(request.getFile("image3"));
        }};

        List<Response> responses = this.cloudImageUploader.uploadFiles(images);
        Map<String, String> imagesInfo = new HashMap<>();
        for (Response response : responses) {
            String pictureFileName = response.request().url().queryParameterValues("filename").get(0);
            String pictureId = response.request().url().queryParameterValues("folderid").get(0);
            imagesInfo.put(pictureFileName, pictureId);
        }

        exerciseBindingModel.setPhotosInfo(this.gson.toJson(imagesInfo));

        if (this.exerciseService.exerciseExists(exerciseBindingModel.getExerciseName())) {
            return new ResponseEntity<>("Exercise already exists.", HttpStatus.BAD_REQUEST);
        }

        if (  this.exerciseService.save(exerciseBindingModel)) {
            return new ResponseEntity<>("Successfully created exercise.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Something went wrong while processing your request.", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
