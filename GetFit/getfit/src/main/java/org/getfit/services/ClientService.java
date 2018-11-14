package org.getfit.services;

import org.getfit.entities.Client;
import org.getfit.models.bindingModels.RegisterClientBindingModel;
import org.getfit.models.viewModels.ClientViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ClientService extends UserDetailsService {
    boolean clientExists(String username);

    boolean save(RegisterClientBindingModel client);

    ClientViewModel getClientViewByUsername(String username);

    Client getClientByUsername(String username);

    List<ClientViewModel> findAll();

    ClientViewModel findById(String id);


//    boolean addCoachToClient(String coachName, String clientName);
}
