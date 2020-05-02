/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import com.kdg.fs24.entity.liases.api.LiasDebtRest;
import com.kdg.fs24.application.core.sysconst.SysConst;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author kazyra_d
 */
public final class TariffRestBox extends TariffBoxAbstract {


    public TariffRestBox() {
        super();
    }

    //==========================================================================
    public void createCalculations(final LocalDate D1,
            final LocalDate D2,
            final Collection<LiasDebtRest> liasDebtRest,
            final TariffRate tariffRate,
            final TariffRowCalculator tariffRowCalculator) {

        Collections.sort((List<LiasDebtRest>) liasDebtRest, this.RDC);
        Collections.sort((List<TariffRateRecord>) tariffRate.getRateRecords(), this.TRRC);

        LocalDate ld1 = D1;
        LocalDate ld2 = D2.plusDays(1);
        BigDecimal dbIteratorBasis;
        BigDecimal percRate;

        final Iterator<LiasDebtRest> restsIterator = liasDebtRest.iterator();
        final Iterator<TariffRateRecord> rateIterator = tariffRate.getRateRecords().iterator();

        // начальный базис
        LiasDebtRest v_liasDebtRest = restsIterator.next();
        dbIteratorBasis = v_liasDebtRest.getRest();

        if (restsIterator.hasNext()) {
            while (v_liasDebtRest.getRestDate().isBefore(ld1)) {
                dbIteratorBasis = v_liasDebtRest.getRest();
                if (restsIterator.hasNext()) {
                    v_liasDebtRest = restsIterator.next();
                }
            }
        }

        // начальная ставка     
        TariffRateRecord v_tariffRateRecord = rateIterator.next();
        percRate = v_tariffRateRecord.getRate_value();

        if (rateIterator.hasNext()) {
            while (v_tariffRateRecord.getRate_date().isBefore(ld1)) {
                percRate = v_tariffRateRecord.getRate_value();
                if (rateIterator.hasNext()) {
                    v_tariffRateRecord = rateIterator.next();
                }
            }
        }
        while (ld1.isBefore(ld2)) {

            if (ld1.equals(v_liasDebtRest.getRestDate())) {
                dbIteratorBasis = v_liasDebtRest.getRest();
                if (restsIterator.hasNext()) {
                    v_liasDebtRest = restsIterator.next();
                }
            }

            if (ld1.equals(v_tariffRateRecord.getRate_date())) {
                percRate = v_tariffRateRecord.getRate_value();
                if (rateIterator.hasNext()) {
                    v_tariffRateRecord = rateIterator.next();
                }
            }

            final LocalDate ld4add = ld1;
            final BigDecimal accrualbasis = dbIteratorBasis;
            final BigDecimal accrualRate = percRate;
            final BigDecimal accrualSum;

            if (null != tariffRowCalculator) {
                accrualSum = tariffRowCalculator.calculate(ld4add, accrualbasis, percRate);
            } else {
                accrualSum = null;
            }

            this.addTariffSum(ld4add, accrualbasis, accrualRate, accrualSum, SysConst.BIGDECIMAL_NULL);
            ld1 = ld1.plusDays(1);
        }
    }

}
