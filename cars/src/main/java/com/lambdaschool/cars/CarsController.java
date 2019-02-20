package com.lambdaschool.cars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.ResourceAssembler;
import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Slf4j
@RestController
public class CarsController
{
    private final CarsRepository carsrepo;
    private final CarsResourceAssembler assembler;
    private final RabbitTemplate rt;

    public CarsController(CarsRepository carsrepo, CarsResourceAssembler assembler,  RabbitTemplate rt)
    {
        this.carsrepo = carsrepo;
        this.assembler = assembler;
        this.rt = rt;
    }

    @GetMapping("/cars/upload")
    public List<Cars>all()
    {
        return carsrepo.findAll();
    }

    @PostMapping("/cars/upload")
    public List<Cars> addCars(@RequestBody List<Cars> newCars)
    {
        log.info("Data Loaded");
        return carsrepo.saveAll(newCars);
    }

    @GetMapping("/cars/id/{id}")
    public Cars findId(@PathVariable Long id)
    {
        return carsrepo.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @GetMapping("/cars/year/{year}")
    public Resources<Resource<Cars>> findYear(@PathVariable int year)
    {
        List<Resource<Cars>> allCars = carsrepo.findAll().stream().map(assembler::toResource).collect(Collectors.toList());
        List<Resource<Cars>> wantedCars = new ArrayList<>();
        for(Resource<Cars> aCar : allCars)
        {
            if (aCar.getContent().getYear() == year)
            {
                wantedCars.add(aCar);
            }
        }
        return new Resources<>(wantedCars, linkTo(methodOn(CarsController.class).all()).withSelfRel());
    }
    @GetMapping("/cars/brand/{brand}")
    public Resources<Resource<Cars>> findBrand(@PathVariable String brand)
    {
        List<Resource<Cars>> allCars = carsrepo.findAll().stream().map(assembler::toResource).collect(Collectors.toList());
        List<Resource<Cars>> wantedCars = new ArrayList<>();
        for(Resource<Cars> aCar : allCars)
        {
            if (aCar.getContent().getBrand().equalsIgnoreCase(brand))
            {
                wantedCars.add(aCar);
            }
        }

//        CarsLog message = new CarsLog("Searched for ", brand );
//        rt.convertAndSend(CarsApplication.QUEUE_NAME, message.toString());
        log.info("Search for " + brand);

        return new Resources<>(wantedCars, linkTo(methodOn(CarsController.class).all()).withSelfRel());
    }

    @DeleteMapping("/cars/delete/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id)
    {
        carsrepo.deleteById(id);
        log.info(id + " Data deleted" );

        return ResponseEntity.noContent().build();
    }


}
