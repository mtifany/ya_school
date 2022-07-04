package ru.ya_school.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundExeption extends  RuntimeException{
    public ItemNotFoundExeption(String message) {
        super(message);
    }
}
