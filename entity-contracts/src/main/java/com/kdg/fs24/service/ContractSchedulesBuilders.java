/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.application.core.exception.api.InternalAppException;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.AnnotationFuncs;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import java.util.Collection;
import com.kdg.fs24.entity.bondschedule.PmtScheduleBuilder;
import com.kdg.fs24.entity.core.AbstractActionEntity;
import com.kdg.fs24.entity.core.api.ActionClassesPackages;
import com.kdg.fs24.entity.core.api.ActionCodeId;
import com.kdg.fs24.entity.core.api.EntityStatusesRef;
import com.kdg.fs24.entity.core.api.EntityTypeId;
import com.kdg.fs24.entity.status.EntityStatusId;
//import com.kdg.fs24.spring.core.api.ApplicationService;
import com.kdg.fs24.spring.core.bean.AbstractApplicationBean;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import com.kdg.fs24.bond.schedule.api.PmtScheduleCalcAlgId;
import com.kdg.fs24.entity.bondschedule.PmtSchedule;
import com.kdg.fs24.references.bond.schedule.api.PmtScheduleAlg;
import com.kdg.fs24.references.bond.schedule.api.PmtScheduleTerm;
import java.util.Optional;

/**
 *
 * @author N76VB
 *
 * коллекция классов построителей графиков
 *
 */
//@Data
@Service
public class ContractSchedulesBuilders extends AbstractApplicationBean {

    // коллекция алгоритмов-построителей графиков
    private final Collection<Class<PmtScheduleBuilder>> scheduleBuilders = ServiceFuncs.getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

    @Override
    public void initialize() {
        ReflectionFuncs.createPkgClassesCollection("com.kdg.fs24.entity", PmtScheduleBuilder.class)
                .stream()
                .filter(p -> !p.isInterface())
                .filter(p -> !Modifier.isAbstract(p.getModifiers()))
                .filter(p -> AnnotationFuncs.isAnnotated(p, PmtScheduleCalcAlgId.class))
                .forEach((entClazz) -> {

                    scheduleBuilders.add(entClazz);

                });

        if (scheduleBuilders.isEmpty()) {
            class NoPmtScheduleBuilderAvailAble extends InternalAppException {

                public NoPmtScheduleBuilderAvailAble(final String message) {
                    super(message);
                }
            }
            throw new NoPmtScheduleBuilderAvailAble(String.format("No schedule builders defined for '%s' ",
                    ContractSchedulesBuilders.class.getCanonicalName()));
        }
    }

    //==========================================================================
    public PmtSchedule buildSchedule(final Integer algId,
            final PmtScheduleAlg pmtScheduleAlg,
            final PmtScheduleTerm pmtScheduleTerm,
            final Integer scheduleKind,
            final LocalDate D1,
            final LocalDate D2
    ) {

        final Optional<Class<PmtScheduleBuilder>> builder = ServiceFuncs.<Class<PmtScheduleBuilder>>getCollectionElement(
                scheduleBuilders,
                srv -> algId.equals(AnnotationFuncs.getAnnotation(srv, PmtScheduleCalcAlgId.class).calcAlgId()));

        if (!builder.isPresent()) {
            class UnkonwnBuilder extends InternalAppException {

                public UnkonwnBuilder(final String message) {
                    super(message);
                }
            }
            throw new UnkonwnBuilder(String.format("%s: Can't find schedule builder 'algId=%d' ",
                    this.getClass().getSimpleName(),
                    algId));
        }

        final PmtScheduleBuilder pmtScheduleBuilder = NullSafe.createObject(builder.get());

        pmtScheduleBuilder.setPmtScheduleAlg(pmtScheduleAlg);
        pmtScheduleBuilder.setPmtScheduleTerm(pmtScheduleTerm);
        pmtScheduleBuilder.setEntityKind(scheduleKind);
        pmtScheduleBuilder.setFrom_date(D1);
        pmtScheduleBuilder.setLast_date(D2);

        final PmtSchedule pmtSchedule = pmtScheduleBuilder.createSchedule();

        pmtSchedule
                .getPmtScheduleLines()
                .stream()
                .forEach((line) -> {
                    line.setPmtSchedule(pmtSchedule);
                });
        return pmtSchedule;
    }
}
