package com.main.getOpenData.DAO;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface KindergardenDao extends CrudRepository<Kindergarden,Long> {

    @Query(value = "SELECT k FROM kindergarden k WHERE id_bilding = " +
            "(SELECT id FROM bilding WHERE lat=?1 AND lng=?2)",nativeQuery = true)
    Kindergarden findByLatAndlng(double lat, double lng);
}
