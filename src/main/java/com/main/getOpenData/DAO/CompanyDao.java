package com.main.getOpenData.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import org.springframework.data.repository.query.Param;

@Transactional
public interface CompanyDao extends CrudRepository<Company,Long> {
    @Query(value = "select c from Company c " +
            "where (coordinateX BETWEEN ?1 AND ?2) AND (coordinateY BETWEEN  ?3 AND ?4)")
    List<Company> findByRadius(@Param("xLeft") double xLeft,@Param("xRight") double xRight,
                               @Param("yBottom") double yBottom,@Param("yTop") double yTop);
}
