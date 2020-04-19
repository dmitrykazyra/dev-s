/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.references.liases.finopercode;

import java.util.Map;
import com.kdg.fs24.references.api.ReferenceRec;
import com.kdg.fs24.references.api.AbstractRefRecord;

/**
 *
 * @author kazyra_d
 */
public class LiasFinOperCode extends AbstractRefRecord implements ReferenceRec {

    private Integer fin_oper_code;
    private String fin_oper_name;

    public LiasFinOperCode() {
        super();
    }

//    public LiasFinOperCode(final Integer fin_oper_code, String fin_oper_name) {
//        super();
//        this.fin_oper_code = fin_oper_code;
//        this.fin_oper_name = fin_oper_name;
//    }
    public Integer getFin_oper_code() {
        return fin_oper_code;
    }

    public LiasFinOperCode setFin_oper_code(final Integer fin_oper_code) {
        this.fin_oper_code = fin_oper_code;
        return this;
    }

    public String getFin_oper_name() {
        return fin_oper_name;
    }

    public LiasFinOperCode setFin_oper_name(final String fin_oper_name) {
        this.fin_oper_name = fin_oper_name;
        return this;
    }

    @Override
    public void record2Map(final Map<String, Integer> map) {
        map.put(String.format("%d - %s", this.getFin_oper_code(), this.getFin_oper_name()), this.getFin_oper_code());
    }
}
