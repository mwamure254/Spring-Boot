package com.example.DakachaPry.parameters.repositories;

import com.example.DakachaPry.parameters.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
}
