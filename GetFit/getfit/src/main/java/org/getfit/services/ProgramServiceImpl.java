package org.getfit.services;

import org.getfit.models.bindingModels.ProgramBindingModel;
import org.getfit.models.bindingModels.TrainingBindingModel;
import org.getfit.repositories.ProgramRepository;
import org.getfit.repositories.TrainingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProgramServiceImpl implements ProgramService {


    private final TrainingRepository trainingRepository;

    private final ProgramRepository programRepository;

    private final ModelMapper modelMapper;

    public ProgramServiceImpl( TrainingRepository trainingRepository, ProgramRepository programRepository, ModelMapper modelMapper) {
        this.trainingRepository = trainingRepository;
        this.programRepository = programRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean programExists(String name) {
        return this.programRepository.findByName(name) != null;
    }

    @Override
    public boolean save(ProgramBindingModel programBindingModel, TrainingBindingModel trainingBindingModel) {

        return false;
    }

}
