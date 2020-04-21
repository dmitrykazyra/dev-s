/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.kind;

import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Entity
@Table(name = "core_EntityKindsRef")
@Data
public class EntityKind extends AbstractRefRecord implements ReferenceRec {

    @Id
    @Column(name = "entity_kind_id")
    private Integer entityKindId;
    @Column(name = "entity_type_id")
    private Integer entityTypeId;
    @Column(name = "entity_kind_name")
    private String entityKindName;

    //==========================================================================
    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(this.toString(), this.getEntityKindId());
    }
//    //==========================================================================
}
