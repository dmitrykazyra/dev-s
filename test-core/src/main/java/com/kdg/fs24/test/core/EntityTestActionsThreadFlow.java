/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.test.core;

import com.kdg.fs24.entity.core.api.ActionEntity;
import com.kdg.fs24.application.core.thread.AbstractThreadFlow;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author kazyra_d
 */
public class EntityTestActionsThreadFlow extends AbstractThreadFlow {

    private ActionEntity entity;
    private String actionLog;

    public EntityTestActionsThreadFlow() {
        super();
    }

    public EntityTestActionsThreadFlow(ActionEntity entity, final String actionLog) {
        this();
        this.entity = entity;
        this.actionLog = actionLog;
    }

    //==========================================================================
//    @Deprecated
    public EntityTestActionsThreadFlow(
            final ActionEntity entity,
            final String actionLog,
            final Integer[] actionsList) throws Throwable {
        this();
        this.entity = entity;
        this.actionLog = actionLog;

        for (final Integer action_code_id : actionsList) {
            this.addThread(() -> {
                EntityTestActionThread actionThread = new EntityTestActionThread(action_code_id, this.entity);
                actionThread.setActionLog(this.actionLog);
                return actionThread;
            });
        }
        this.waitForLastThread();
    }

    //==========================================================================
//    public EntityTestActionsThreadFlow(
//            final Entity entity,
//            final String actionLog,
//            final Stream<Integer> actionsList) throws Throwable {
//        this();
//        this.entity = entity;
//        this.actionLog = actionLog;
//
//        actionsList
//                .collect(Collectors.toList())
//                .forEach(action_code_id -> {;
//                    this.addThread(() -> {
//                        EntityTestActionThread actionThread = new EntityTestActionThread(action_code_id, this.entity);
//                        actionThread.setActionLog(this.actionLog);
//                        return actionThread;
//                    });
//                });
//        this.waitForLastThread();
//    }

    public EntityTestActionsThreadFlow addTestedAction(final Integer action_code_id) {

        this.addThread(() -> {
            EntityTestActionThread actionThread = new EntityTestActionThread(action_code_id, this.entity);
            actionThread.setActionLog(this.actionLog);
            return actionThread;
        });

        return this;
    }

}
