package com.main.getOpenData.DAO;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SchoolDao extends CrudRepository<School,Long> {

    @Query(value ="SELECT s FROM School s WHERE id_bilding = ?1")
    List<School> findByIdBilding(long id_bilding);

}
