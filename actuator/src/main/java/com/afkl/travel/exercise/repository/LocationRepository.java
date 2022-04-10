package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.entity.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findAll();

    Optional<Location> findByTypeAndCode(final String type, final String code);

    List<Location> findByLocationByParentId(final Location location);
}
