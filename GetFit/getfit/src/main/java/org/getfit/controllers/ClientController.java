package org.getfit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.Response;
import org.getfit.cloud.CloudImageExtractor;
import org.getfit.cloud.CloudImageUploader;
import org.getfit.entities.Client;
import org.getfit.entities.Coach;
import org.getfit.models.bindingModels.RegisterClientBindingModel;
import org.getfit.models.bindingModels.RegisterCoachBindingModel;
import org.getfit.models.viewModels.ClientViewModel;
import org.getfit.models.viewModels.CoachViewModel;
import org.getfit.services.ClientService;
import org.getfit.services.CoachService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final CloudImageUploader cloudImageUploader;

    private final Gson gson;

    private final ClientService clientService;

    private final CoachService coachService;

    private final CloudImageExtractor cloudImageExtractor;

    private final ModelMapper modelMapper;


    public ClientController(CloudImageUploader cloudImageUploader, Gson gson, ClientService clientService, CoachService coachService, CloudImageExtractor cloudImageExtractor, ModelMapper modelMapper) {
        this.cloudImageUploader = cloudImageUploader;
        this.gson = gson;
        this.clientService = clientService;
        this.coachService = coachService;
        this.cloudImageExtractor = cloudImageExtractor;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public ResponseEntity<?> register(MultipartHttpServletRequest request) throws IOException {
        MultipartFile profilePicture = request.getFile("profilePicture");
        ObjectMapper mapper = new ObjectMapper();
        RegisterClientBindingModel user = mapper.readValue(request.getParameter("userInfo"), RegisterClientBindingModel.class);

        Response response = this.cloudImageUploader.uploadFile(profilePicture);

        String profilePictureFileName = response.request().url().queryParameterValues("filename").get(0);
        String profilePictureId = response.request().url().queryParameterValues("folderid").get(0);

        user.setProfilePictureFileName(profilePictureFileName);
        user.setProfilePictureFileId(profilePictureId);


        if (this.clientService.clientExists(user.getUsername())) {
            return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
        }

        if (this.clientService.save(user)) {
            return new ResponseEntity<>("Successfully registered user.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong while processing your request.", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping(value = "/profile", produces = "application/json")
    public @ResponseBody
    String[] home(@RequestParam String username) throws IOException {
        String[] result = new String[2];
        ClientViewModel client = this.clientService.getClientViewByUsername(username);
        this.clientService.getClientByUsername(client.getUsername()).getCoaches().forEach(c -> client.getCoachesNames().add(c.getUsername()));
        List<CoachViewModel> coaches = new ArrayList<>();

        if (!client.getCoachesNames().isEmpty()) {
            client.getCoachesNames().forEach(coach -> {
                coaches.add(this.coachService.getCoachViewModelByUsername(coach));
            });
        }
        result[0] = this.gson.toJson(client);
        result[1] = this.gson.toJson(coaches);

        return result;
    }

    @PostMapping(value = "/add-coach", consumes = "application/json")
    public ResponseEntity<?> addCoach(@RequestBody Map<String, String> data) {
        String coachName = data.get("coachName");
        String clientName = data.get("clientName");

        Client client = this.clientService.getClientByUsername(clientName);
        Coach coach = this.coachService.getCoachByUsername(coachName);

        client.getCoaches().add(coach);
        coach.getClients().add(client);

        RegisterClientBindingModel clientBindingModel = modelMapper.map(client, RegisterClientBindingModel.class);
        RegisterCoachBindingModel coachBindingModel = modelMapper.map(coach, RegisterCoachBindingModel.class);

        clientService.save(clientBindingModel);

        coachService.save(coachBindingModel);

        return new ResponseEntity<>("Successfully added coach.", HttpStatus.OK);
    }

    @GetMapping(value = "/details", produces = "application/json")
    public @ResponseBody
    String details(@RequestParam String username) {
        ClientViewModel user = this.clientService.getClientViewByUsername(username);
        this.clientService.getClientByUsername(username).getCoaches().forEach(c -> user.getCoachesNames().add(c.getUsername()));

        return this.gson
                .toJson(user);
    }
}
