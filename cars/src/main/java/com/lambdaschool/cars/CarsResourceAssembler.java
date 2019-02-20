package com.lambdaschool.cars;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CarsResourceAssembler implements ResourceAssembler <Cars, Resource<Cars>>
{
    @Override
    public Resource<Cars> toResource(Cars cars)
    {
        return new Resource<Cars>(cars,
                    linkTo(methodOn(CarsController.class).findId(cars.getId())).withSelfRel(),
                    linkTo(methodOn(CarsController.class).all()).withRel("cars"));
    }
}
