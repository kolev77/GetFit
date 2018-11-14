package org.getfit.services;

import org.getfit.entities.Coach;
import org.getfit.models.bindingModels.RegisterCoachBindingModel;
import org.getfit.models.viewModels.CoachViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CoachService extends UserDetailsService {

    boolean coachExists(String username);

    boolean save(RegisterCoachBindingModel coach);

    CoachViewModel getCoachViewModelByUsername(String username);

    Coach getCoachByUsername(String username);

    List<CoachViewModel> findAll();

    CoachViewModel findById(String id);


}
