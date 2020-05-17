/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import java.util.Collection;
import com.kdg.fs24.entity.bondschedule.PmtScheduleBuilder;
import com.kdg.fs24.spring.core.bean.AbstractApplicationBean;
import lombok.Data;

/**
 *
 * @author N76VB
 *
 * коллекция построителей графиков
 *
 */
@Data
public abstract class AbstractSchedulesBuilders extends AbstractApplicationBean {
    
    // коллекция алгоритмов-построителей графиков
    private Collection<PmtScheduleBuilder> scheduleBuilders;

}
