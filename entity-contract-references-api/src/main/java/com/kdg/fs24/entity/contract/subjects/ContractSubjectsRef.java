/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contract.subjects;

import com.kdg.fs24.exception.references.ReferenceNotFound;
import com.kdg.fs24.references.core.AbstractReference;

/**
 *
 * @author kazyra_d
 */
@Deprecated
public class ContractSubjectsRef<T extends ContractSubject> extends AbstractReference<ContractSubject> {

    //==========================================================================
    public T getContractSubjectById(final Integer cs_id) throws ReferenceNotFound {

        return (T) this.<T>findReference(() -> (this.findContractSubjectById(cs_id)),
                String.format("Неизвестный тип контракта (ContractSubjectRef.id=%d)", cs_id));

    }

    //==========================================================================
    private T findContractSubjectById(final Integer cs_id) {

        return (T) this.findCachedRecords((object) -> ((T) object).getContractSubjectId().equals(cs_id));

    }

    //==========================================================================
    public String getContractSubjectNameById(final Integer cs_id) throws ReferenceNotFound {

        return this.getContractSubjectById(cs_id).getContractSubjectName();
    }

}
