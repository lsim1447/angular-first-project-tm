package com.silerv.tendermaker.lazar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by lazar on 2017. 12. 01..
 */
@Entity
@Data
@NoArgsConstructor
public class Task {
    @Id
    @SequenceGenerator(name = "seq_gen")
    @GeneratedValue(generator = "seq_gen")
    private long task_id;

    @NotNull
    private String name;

    @NotNull
    private String unit;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private Set<Detail> details;

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
