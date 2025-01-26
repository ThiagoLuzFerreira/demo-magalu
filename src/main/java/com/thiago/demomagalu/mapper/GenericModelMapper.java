package com.thiago.demomagalu.mapper;

import org.modelmapper.ModelMapper;

public class GenericModelMapper {

    public static ModelMapper mapper = new ModelMapper();

    public static <O,D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }
}
