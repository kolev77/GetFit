package org.getfit.services;

import org.getfit.entities.Coach;
import org.getfit.models.bindingModels.RegisterCoachBindingModel;
import org.getfit.models.viewModels.CoachViewModel;
import org.getfit.repositories.CoachRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoachServiceImpl implements CoachService {
    private final CoachRepository coachRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public CoachServiceImpl(CoachRepository coachRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.coachRepository = coachRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean coachExists(String username) {
        return this.coachRepository.findByUsername(username) != null;
    }

    @Override
    public boolean save(RegisterCoachBindingModel coachBindingModel) {
        String userId = this.coachRepository.findByUsername(coachBindingModel.getUsername()).getId();
        if (this.coachRepository.existsById(userId)) {
            Coach userToUpdate = this.coachRepository.getOne(userId);
            userToUpdate.setClients(coachBindingModel.getClients());

            Coach save = this.coachRepository.saveAndFlush(userToUpdate);
        } else {
            Coach coach = this.modelMapper.map(coachBindingModel, Coach.class);

            coach.setPassword(this.bCryptPasswordEncoder.encode(coachBindingModel.getPassword()));

            this.coachRepository.save(coach);
        }
        return true;
    }

    @Override
    public CoachViewModel getCoachViewModelByUsername(String username) {
        Coach coach = this.coachRepository.findByUsername(username);

        return this.modelMapper.map(coach, CoachViewModel.class);
    }

    @Override
    public Coach getCoachByUsername(String username) {
        if (coachExists(username)) {
            return coachRepository.findByUsername(username);
        }
        return null;
    }

    @Override
    public List<CoachViewModel> findAll() {
        List<Coach> all = this.coachRepository.findAll();
        List<CoachViewModel> result = new ArrayList<>();

        for (Coach coach : all) {
            CoachViewModel viewModel = modelMapper.map(coach, CoachViewModel.class);
            coach.getClients().forEach(c -> viewModel.getClientsNames().add(c.getUsername()));
            result.add(viewModel);
        }

        return result;
    }

    @Override
    public CoachViewModel findById(String id) {
        Coach coach = this.coachRepository.findById(id).get();

        return this.modelMapper.map(coach, CoachViewModel.class);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Coach coach = this.coachRepository.findByUsername(username);
        if (coach == null) {
            throw new UsernameNotFoundException("Username was not found.");
        }
        return coach;
    }
}
