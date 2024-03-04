package com.ust.main.dto;

import org.modelmapper.ModelMapper;

public class LeaveMasterDTO {

    ModelMapper modelMapper = new ModelMapper();

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


}
