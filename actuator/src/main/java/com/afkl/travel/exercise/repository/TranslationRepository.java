package com.afkl.travel.exercise.repository;

import com.afkl.travel.exercise.entity.Location;
import com.afkl.travel.exercise.entity.Translation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface TranslationRepository extends CrudRepository<Translation, Long> {

    Optional<Translation> findByLanguageAndLocationByLocation(final String language, final Location locationByLocation);

}
