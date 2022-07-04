package ru.ya_school.dto;

import ru.ya_school.models.EntityType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class EntityDto {


    @NotNull
    UUID id;
    @NotNull
    String name;
    @NotNull
    EntityType type;
    Integer price;
    UUID parentId;

    public EntityDto(UUID id, String name, EntityType type, Integer price, UUID parentId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.parentId = parentId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EntityType getType() {
        return type;
    }

    public Integer getPrice() {
        return price;
    }

    public UUID getParentId() {
        return parentId;
    }




    @Override
    public String toString() {
        return "EntityDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
