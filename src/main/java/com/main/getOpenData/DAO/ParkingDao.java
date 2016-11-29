package com.main.getOpenData.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ParkingDao extends CrudRepository<Parking,Long> {

    @Query(value ="SELECT k FROM Parking k WHERE id_bilding = ?1")
    List<Parking> findByIdBilding(long id_bilding);
}
