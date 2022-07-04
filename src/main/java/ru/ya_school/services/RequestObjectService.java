package ru.ya_school.services;

import org.modelmapper.ModelMapper;
import ru.ya_school.dto.EntityDto;
import ru.ya_school.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ya_school.util.InvalidDataException;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RequestObjectService {

    private final EntityService entityService;
    private final ModelMapper modelMapper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Autowired
    public RequestObjectService(EntityService entityService, ModelMapper modelMapper) {
        this.entityService = entityService;
        this.modelMapper = modelMapper;

    }

    public void create(RequestObject requestObject) throws ParseException {
        if (requestObject.getItems() == null)
            return;

        Date date = formatter.parse(requestObject.getUpdateDate());
        if (date == null)
            return;

        for (EntityDto  entityDto : requestObject.getItems()) {
            if (requestObject.getItems().stream().filter((i -> i.getId().equals(entityDto.getId()))).count() > 1){
                throw new InvalidDataException("");
            }
            Entity item = validityCheckAndConvert(entityDto);
            item.setDate(date);
          UUID parentId = item.getParentId();
            if (parentId != null) {
               item.setParent((Category) entityService.read(parentId));
            }
            if (entityService.read(item.getId()) != null) {
               entityService.update(item, item.getId());
            } else {
                entityService.create(item);
           }
        }
    }

    private Entity validityCheckAndConvert(@Valid EntityDto entityDto){

        if(entityDto.getId() == null || entityDto.getType() == null || entityDto.getName() == null
        || entityService.read(entityDto.getParentId()).getType().equals(EntityType.OFFER))
            {
                throw new InvalidDataException("");
            }

        Optional<Integer> price = Optional.ofNullable(entityDto.getPrice());
        switch (entityDto.getType()){
            case CATEGORY -> {
                if (price.isPresent()){
                    throw new InvalidDataException("");
                }
                return modelMapper.map(entityDto, Category.class);
            }

            case OFFER -> {
                if (price.isEmpty() || price.get() < 0){
                    throw new InvalidDataException("");
                }
                return modelMapper.map(entityDto, Product.class);
            }
        }
        throw new InvalidDataException("");
    }

}
