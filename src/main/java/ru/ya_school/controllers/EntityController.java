package ru.ya_school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.ya_school.models.Entity;
import ru.ya_school.models.RequestObject;
import ru.ya_school.services.EntityService;
import ru.ya_school.services.RequestObjectService;
import ru.ya_school.util.InvalidDataException;
import ru.ya_school.util.ItemNotFoundExeption;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class EntityController {

    private final EntityService entityService;
    private final RequestObjectService requestObjectService;

    @Autowired
    public EntityController(EntityService entityService, RequestObjectService requestObjectService) {
        this.entityService = entityService;
        this.requestObjectService = requestObjectService;
    }

    @GetMapping(value = "/helloworld")
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello, World!", HttpStatus.OK);
    }


    @GetMapping("/nodes/{id}")
    public ResponseEntity<Object> getItems(@PathVariable(name = "id") String id) {
        try {
            Entity entity = entityService.read(UUID.fromString(id));
            if (entity == null)
                return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @PostMapping(value = "/imports")
    public ResponseEntity<?> create (@RequestBody @Valid RequestObject requestObject, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidDataException("");
        }
        try{
         requestObjectService.create(requestObject);
        }
     catch (Exception e) {
            throw new InvalidDataException("");

        }
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") String id) {
        try {
            if (entityService.delete(UUID.fromString(id)))
                return ResponseEntity.ok(HttpStatus.OK);
            else
                return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
    catch (IllegalArgumentException e){
            throw new InvalidDataException("2");
    }
    }
    @ExceptionHandler ({ItemNotFoundExeption.class})
    private ResponseEntity<Object> handleItemNotFoundExeption (ItemNotFoundExeption e,
                                                               WebRequest request){
        return new ResponseEntity<>("Item with this id was not found!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleInvalidDataExeption(InvalidDataException e, WebRequest request){
        return new ResponseEntity<>("Validation Failed" , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleHttpMessageNotReadableException(Exception ex, WebRequest request) {
      return new ResponseEntity<>("Validation Failed" , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

   }
