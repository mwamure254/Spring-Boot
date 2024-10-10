package com.example.DakachaPry.parameters.repositories;

import com.example.DakachaPry.parameters.models.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

}
