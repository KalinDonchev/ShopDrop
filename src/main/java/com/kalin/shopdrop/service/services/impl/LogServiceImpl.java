package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.Log;
import com.kalin.shopdrop.data.repositories.LogRepository;
import com.kalin.shopdrop.service.models.LogServiceModel;
import com.kalin.shopdrop.service.services.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LogServiceModel seedLogInDB(LogServiceModel logServiceModel) {
        Log log = this.modelMapper.map(logServiceModel, Log.class);
        this.logRepository.saveAndFlush(log);
        return this.modelMapper.map(log, LogServiceModel.class);
    }
}
