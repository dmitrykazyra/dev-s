/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.calculations;

import com.kdg.fs24.entity.debts.LiasAction;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.entity.tariff.TariffRate;
import com.kdg.fs24.entity.tariff.TariffRecordAbstract;
import com.kdg.fs24.references.tariffs.kind.TariffRowCalculator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author N76VB
 */
public class TariffTurnOverBox extends TariffBoxAbstract {

    public TariffTurnOverBox() {
        super();

    }

    //==========================================================================
    public void createCalculations(
            final Collection<LiasAction> liasActions,
            final TariffRate tariffRate,
            final TariffRowCalculator tariffRowCalculator) {

        Collections.sort((List<LiasAction>) liasActions, this.LAC);
        Collections.sort((List<TariffRecordAbstract>) tariffRate.getTariffRates(), this.TRRC);

        final LiasAction firstOper = liasActions.iterator().next();

        if (null == firstOper) {
            return;
        }

        final LocalDate d1 = firstOper.getLiasDate();
        //final Iterator<LiasAction> laIterator = liasActions.iterator();
        final Iterator<TariffRecordAbstract> rateIterator = tariffRate.getTariffRates().iterator();

        BigDecimal percRate;

        // начальная ставка     
        TariffRecordAbstract v_tariffRateRecord = rateIterator.next();
        percRate = v_tariffRateRecord.getRateValue();

        if (rateIterator.hasNext()) {
            while (v_tariffRateRecord.getRateDate().isBefore(d1)) {
                percRate = v_tariffRateRecord.getRateValue();
                if (rateIterator.hasNext()) {
                    v_tariffRateRecord = rateIterator.next();
                }
            }
        }

        //LogService.LogInfo(this.getClass(), String.format(" liasActions size = %d ", liasActions.size()));
        for (final LiasAction liasAction : liasActions) {

            if (liasAction.getLiasDate().equals(v_tariffRateRecord.getRateDate())) {
                percRate = v_tariffRateRecord.getRateValue();
                if (rateIterator.hasNext()) {
                    v_tariffRateRecord = rateIterator.next();
                }
            }

            final BigDecimal accrualRate = percRate;
            final BigDecimal accrualSum;

            if (null != tariffRowCalculator) {
                accrualSum = tariffRowCalculator.calculate(liasAction.getLiasDate(), liasAction.getLiasSum().abs(), percRate);
            } else {
                accrualSum = null;
            }

            this.addOrReplaceTariffSum(liasAction.getLiasDate(), liasAction.getLiasSum().abs(), accrualRate, accrualSum, SysConst.BIGDECIMAL_NULL);

            if (liasAction.getLiasDate().equals(v_tariffRateRecord.getRateDate())) {
                percRate = v_tariffRateRecord.getRateValue();
                if (rateIterator.hasNext()) {
                    v_tariffRateRecord = rateIterator.next();
                }
            }
        }
    }

//==========================================================================
}
