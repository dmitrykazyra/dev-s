/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.status;

import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Entity
@Table(name = "core_EntityStatusesRef")
@Data
@IdClass(EntityStatusPK.class)
public class EntityStatus extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "entity_status_id")
    private Integer entityStatusId;
    @Id
    @Column(name = "entity_type_id")
    private Integer entityTypeId;
    @Column(name = "entity_status_name")
    private String entityStatusName;

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(this.toString(), this.getEntityStatusId());
    }
    //==========================================================================

    public static EntityStatus getExistEntityStatus(final Integer entityType, final Integer entityStatusId) {

        return ServiceFuncs.getMapValue(REF_CACHE, mapEntry -> mapEntry.getKey().equals(EntityStatus.class))
                .get()
                .stream()
                .map(x -> (EntityStatus) x)
                .collect(Collectors.toList())
                .stream()
                .filter(status -> status.getEntityTypeId().equals(entityType))
                .filter(status -> status.getEntityStatusId().equals(entityStatusId))
                .findFirst()
                .get();

    }

}
