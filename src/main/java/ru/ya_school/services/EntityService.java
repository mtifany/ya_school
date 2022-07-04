package ru.ya_school.services;

import org.springframework.stereotype.Service;
import ru.ya_school.models.Entity;

import ru.ya_school.repositories.EntityRepository;
import ru.ya_school.util.InvalidDataException;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class EntityService {

    private final EntityRepository entityRepository;

    public EntityService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;

    }

    public void create(@Valid Entity entity){
       if(entityRepository.findById(entity.getId()).isPresent()
       && !entityRepository.getById(entity.getId()).getId().equals(entity.getId()))
       {
           return;
       }
        entityRepository.save(entity);
    }

    public Entity read(UUID id){
        Optional<Entity> entity = entityRepository.findById(id);
        return entity.orElse(null);
    }

    public boolean delete (UUID id){

        Entity entity = this.read(id);
        if (entity == null)
        return false;
        if (entity.getParentId() == null) {
            entityRepository.delete(entity);
        }
        else{
            Entity parent = entity.getParent();
            parent.getChildren().remove(entity);
            entityRepository.save(parent);
            entityRepository.delete(entity);
        }
        return true;
    }

    public void update(@Valid Entity entity, UUID uuid) {

            Entity old = read(uuid);
            if(!old.getType().equals(entity.getType()))
                throw new InvalidDataException("");
            old.update(entity);
            entityRepository.save(old);
            if (entity.getParent() != null)
                updateParent(entity.getParent(), entity.getDate());
         }

    private void updateParent(Entity entity, Date date) {
        if (date != null) {
            if (date.after(entity.getDate()))
                entity.setDate(date);
        }

        entityRepository.save(entity);
        if (entity.getParent() != null) {
            Entity parent = entity.getParent();
           updateParent(parent, date);}

    }



}
