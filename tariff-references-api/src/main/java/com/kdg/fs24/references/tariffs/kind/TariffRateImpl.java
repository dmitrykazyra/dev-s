/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.tariffs.kind;

/**
 *
 * @author N76VB
 */
import com.kdg.fs24.application.core.service.funcs.CustomCollectionImpl;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.service.funcs.GenericFuncs;
import com.kdg.fs24.application.core.locale.NLS;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.api.ObjectRoot;
import java.util.Collection;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import java.lang.reflect.Method;

public class TariffRateImpl<T extends TariffRateRecord>
        extends ObjectRoot implements TariffRate<T> {

    private final Collection<T> rateRecords = ServiceFuncs.<T>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

    private Integer serv_id;
    private Integer kind_id;
    private Integer rate_id;
    private String rate_name;
    private Integer tariff_scheme_id;
    final private String methodName = "store";

    public TariffRateImpl() {
        super();
    }

    public TariffRateImpl(final Integer serv_id) {
        this();
        this.setServ_id(serv_id);
    }

    //==========================================================================
    @Override
    public void store() {

        if (!getRateRecords().isEmpty()) {

            NullSafe.create(GenericFuncs.getElementClass(this.getRateRecords()))
                    .safeExecute((clazz) -> {

                        NullSafe.create(((Class) clazz).getMethod(methodName, Collection.class, Integer.class))
                                .whenIsNull(() -> LogService.LogErr(this.getClass(), () -> String.format("Method is not found in records collection (%s)", this.methodName)))
                                .safeExecute(method -> {
                                    ((Method) method).invoke(null, this.getRateRecords(), this.getRate_id());
                                });
                    });
        }
    }

    @Override
    public Integer getServ_id() {
        return serv_id;
    }

    @Override
    public void setServ_id(final Integer serv_id) {
        this.serv_id = serv_id;
    }

    @Override
    public String getRate_name() {
        return rate_name;
    }

    @Override
    public Integer getRate_id() {
        return rate_id;
    }

    @Override
    public void setRate_id(final Integer rate_id) {
        this.rate_id = rate_id;
    }

    @Override
    public Collection<T> getRateRecords() {
        return rateRecords;
    }

    @Override
    public <TR extends TariffRateRecord> TariffRate<TR> addRateRecord(final TR rateRecord) {
        rateRecords.add((T) rateRecord);
        return (TariffRate<TR>) this;
    }

    @Override
    public Integer getTariff_scheme_id() {
        return tariff_scheme_id;
    }

    @Override
    public void printRates() {
        //анонимный класс для принтования списка остатков
        final CustomCollectionImpl customCollection = NullSafe.createObject(CustomCollectionImpl.class,String.format("RateRecords (rate_id=%d, %d records, rate_name={%s}, scheme_id = {%d})\n",
                TariffRateImpl.this.getRate_id(),
                TariffRateImpl.this.<T>getRateRecords().size(),
                TariffRateImpl.this.getRate_name(),
                TariffRateImpl.this.getTariff_scheme_id()));

        (this.<T>getRateRecords())
                .stream()
                .sorted((r1, r2) -> {
                    // сортировка по дате в списке
                    return r1.getRate_date().compareTo(r2.getRate_date());
                })
                .forEach(rest -> {
                    customCollection.addCustomRecord(() -> String.format("rateDate: %s; rate value: %f\n",
                            NLS.getStringDate(rest.getRate_date()),
                            rest.getRate_value()));
                });

        LogService.LogInfo(this.getClass(), () -> customCollection.getRecord());
    }

    @Override
    public Integer getKind_id() {
        return kind_id;
    }

    @Override
    public void setKind_id(final Integer kind_id) {
        this.kind_id = kind_id;
    }
}
