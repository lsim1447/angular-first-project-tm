package com.silerv.tendermaker.lazar.repository;

import com.silerv.tendermaker.lazar.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lazar on 2017. 12. 01..
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

}
