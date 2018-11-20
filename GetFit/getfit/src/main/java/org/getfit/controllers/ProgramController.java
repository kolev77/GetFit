package org.getfit.controllers;

import com.google.gson.Gson;
import org.getfit.cloud.CloudImageExtractor;
import org.getfit.services.ExerciseService;
import org.getfit.services.ProgramService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/program")
public class ProgramController {
    private final ExerciseService exerciseService;

    private final ProgramService programService;

    private final Gson gson;

    private final CloudImageExtractor cloudImageExtractor;

    public ProgramController(ExerciseService exerciseService, ProgramService programService, Gson gson, CloudImageExtractor cloudImageExtractor) {
        this.exerciseService = exerciseService;
        this.programService = programService;
        this.gson = gson;
        this.cloudImageExtractor = cloudImageExtractor;
    }



//    @RequestMapping(value = "/create-exercise", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
//    public ResponseEntity<?> createExercise(MultipartHttpServletRequest request) throws IOException {
//        String exerciseInfo = request.getParameter("exerciseInfo");
//        ObjectMapper objectMapper = new ObjectMapper();
//        RegisterExerciseBindingModel exerciseBindingModel = objectMapper.readValue(exerciseInfo, RegisterExerciseBindingModel.class);
//
//        List<MultipartFile> images = new ArrayList<>() {{
//            add(request.getFile("image1"));
//            add(request.getFile("image2"));
//            add(request.getFile("image3"));
//        }};
//
//        List<Response> responses = this.cloudImageUploader.uploadFiles(images);
//        Map<String, String> imagesInfo = new HashMap<>();
//        for (Response response : responses) {
//            String pictureFileName = response.request().url().queryParameterValues("filename").get(0);
//            String pictureId = response.request().url().queryParameterValues("folderid").get(0);
//            imagesInfo.put(pictureFileName, pictureId);
//        }
//
//        exerciseBindingModel.setPhotosInfo(this.gson.toJson(imagesInfo));
//
//        if (this.exerciseService.exerciseExists(exerciseBindingModel.getExerciseName())) {
//            return new ResponseEntity<>("Exercise already exists.", HttpStatus.BAD_REQUEST);
//        }
//
//        if (  this.exerciseService.save(exerciseBindingModel)) {
//            return new ResponseEntity<>("Successfully created exercise.", HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("Something went wrong while processing your request.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
