/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.collection;

import com.kdg.fs24.entity.core.api.EntityLock;
import com.kdg.fs24.references.list.ObjectList;
//import com.kdg.fs24.services.api.Service;
import com.kdg.fs24.application.core.sysconst.SysConst;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.kdg.fs24.application.core.nullsafe.NullSafe;

/**
 *
 * @author N76VB
 */
public class EntityLocksListService extends ObjectList { // implements Service {

    //==========================================================================
    public List<EntityLock> getLocksList() {
        return (List<EntityLock>) super.getObjectList();
    }

    //==========================================================================
//    public void addEntityLock(EntityLock entity) {
//        this.getLocksList().add(entity);
//    }
    //==========================================================================
    // возвращаем инфомацию о блокировке, если сущность кем-то уже заблокирована
    public void addEntityLock(final Long entity_id, final String locked_by) {

        EntityLock entityLock = findEntityLock(entity_id);

        if (NullSafe.isNull(entityLock)) {
            // добавляем блокировку - определяем класс на лету
            this.getLocksList().add(new EntityLock() {

                @Override
                public Long entity_id() {
                    return entity_id;
                }

                @Override
                public String locked_by() {
                    return locked_by;
                }

                @Override
                public String lock_date() {
                    return String.format("%s", LocalDateTime.now());
                }

                @Override
                public String lock_address() {
                    return SysConst.APPLICATION_ADDRESS;
                }
            });
        }
    }
    //==========================================================================

    public void releaseEntityLock(final Long entity_id) {

//        LogService.writeLog(LogService.getCurrentObjProcName(this), String.format("entity_id=%d", entity_id));
        EntityLock entityLock = findEntityLock(entity_id);

        if (NullSafe.notNull(entityLock)) {
            this.getLocksList().remove(entityLock);
        }
    }

    //==========================================================================
    public EntityLock findEntityLock(final Long entity_id) {
        EntityLock entityLock = null;

        List<EntityLock> el = ((List<EntityLock>) this.getLocksList()).stream()
                .filter(p -> p.entity_id().equals(entity_id)).collect(Collectors.toList());

        if (!el.isEmpty()) {
            entityLock = el.get(0);
        }
        return entityLock;
    }
}
