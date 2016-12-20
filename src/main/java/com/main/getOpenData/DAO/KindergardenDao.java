package com.main.getOpenData.DAO;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface KindergardenDao extends CrudRepository<Kindergarden,Long> {

    @Query(value ="SELECT k FROM Kindergarden k WHERE id_bilding=?1")
    List<Kindergarden> findByIdBilding(long id);

    @Query(value ="SELECT date FROM Kindergarden  ORDER BY date ASC LIMIT 1", nativeQuery = true)
    Date getDate();

}
