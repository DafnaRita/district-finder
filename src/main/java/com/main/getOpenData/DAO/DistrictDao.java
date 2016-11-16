package com.main.getOpenData.DAO;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DistrictDao extends CrudRepository<District,Long> {
    List<District> findByName(String name);
}
