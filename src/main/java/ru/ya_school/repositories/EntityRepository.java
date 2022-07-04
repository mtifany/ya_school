package ru.ya_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ya_school.models.Entity;
import ru.ya_school.models.EntityType;

import java.util.List;
import java.util.UUID;

@Repository
public interface EntityRepository extends JpaRepository<Entity, UUID> {

    List<Entity> getAllByType(EntityType type);
    Entity getById(UUID id);
}
