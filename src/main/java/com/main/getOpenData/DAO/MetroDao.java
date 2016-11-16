package com.main.getOpenData.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MetroDao extends CrudRepository<Metro,Long> {

//    @Query(value = "select m from MetroJSON m")
//    List<MetroJSON> ();
}
