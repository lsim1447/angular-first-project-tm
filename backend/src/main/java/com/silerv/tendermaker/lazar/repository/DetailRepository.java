package com.silerv.tendermaker.lazar.repository;

import com.silerv.tendermaker.lazar.model.Detail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lazar on 2017. 12. 01..
 */
@Repository
public interface DetailRepository extends CrudRepository<Detail, Long> {

}
