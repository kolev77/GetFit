package org.getfit.services;

import org.getfit.entities.Client;
import org.getfit.models.bindingModels.RegisterClientBindingModel;
import org.getfit.models.viewModels.ClientViewModel;
import org.getfit.repositories.ClientRepository;
import org.getfit.repositories.CoachRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    private final CoachRepository coachRepository;

    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, CoachRepository coachRepository) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.coachRepository = coachRepository;
    }

    @Override
    public boolean clientExists(String username) {
        return this.clientRepository.findByUsername(username) != null;
    }

    @Override
    public boolean save(RegisterClientBindingModel clientBindingModel) {
        try {
            Client userToUpdate = this.clientRepository.getOne(this.clientRepository.findByUsername(clientBindingModel.getUsername()).getId());
            userToUpdate.setCoaches(clientBindingModel.getCoaches());
            Client save = this.clientRepository.saveAndFlush(userToUpdate);

        } catch (NullPointerException e) {
            Client client = this.modelMapper.map(clientBindingModel, Client.class);
            client.setPassword(this.bCryptPasswordEncoder.encode(client.getPassword()));

            this.clientRepository.save(client);
        }
        return true;
    }

    @Override
    public ClientViewModel getClientViewByUsername(String username) {
        Client client = this.clientRepository.findByUsername(username);

        return this.modelMapper.map(client, ClientViewModel.class);
    }

    @Override
    public Client getClientByUsername(String username) {
        if (clientExists(username)) {
            return clientRepository.findByUsername(username);
        }
        return null;
    }

    @Override
    public List<ClientViewModel> findAll() {
        List<Client> all = this.clientRepository.findAll();
        List<ClientViewModel> result = new ArrayList<>();

        for (Client client : all) {
            ClientViewModel viewModel = modelMapper.map(client, ClientViewModel.class);
            client.getCoaches().forEach(c -> viewModel.getCoachesNames().add(c.getUsername()));
            result.add(viewModel);
        }

        return result;
    }

    @Override
    public ClientViewModel findById(String id) {
        Client client= this.clientRepository.findById(id).get();

        return this.modelMapper.map(client, ClientViewModel.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = this.clientRepository.findByUsername(username);

        if (client == null) {
            throw new UsernameNotFoundException("Username was not found.");
        }
        return client;
    }
}
