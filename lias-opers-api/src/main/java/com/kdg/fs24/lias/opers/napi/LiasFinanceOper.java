/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.lias.opers.napi;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.log.LogService;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
public class LiasFinanceOper {

    final private Collection<OperAttr> operAttrsCollection
            = ServiceFuncs.<OperAttr>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);

    public <LD extends OperAttr> LiasFinanceOper add(final LD attrValue) {

//        LogService.LogInfo(attrValue.getClass(), () -> String.format("attrValue: %s = (%s) %s",
//                attrValue.getClass().getInterfaces()[0].getSimpleName(),
//                attrValue.value().getClass().getSimpleName(),
//                attrValue.value()));
        this.getOperAttrsCollection().add(attrValue);
        return this;
    }

    //==========================================================================
    public <V> V attr(final Class<? extends OperAttr> clazz) {

        final Optional<OperAttr> operAttr = ServiceFuncs.<OperAttr>getCollectionElement(
                this.getOperAttrsCollection(),
                attr -> attr.getClass().getInterfaces()[0].getName().equals(clazz.getName()));

        return (V) (operAttr.isPresent() ? operAttr.get() : null);
    }

    //==========================================================================
    public void printOperAttrsCollection() {

        String operAttrs = String.format("LiasOpers attributes (%d): \n",
                LiasFinanceOper.this.operAttrsCollection.size());

        final String printAttrs = this.operAttrsCollection
                .stream()
                .sorted((r1, r2) -> r1.getClass()
                .getInterfaces()[0]
                .getSimpleName()
                .compareTo(r2.getClass()
                        .getInterfaces()[0]
                        .getSimpleName()))
                .map(operAttr -> (Function<String, String>) s -> String.format("%30s: %s\n", operAttr.getClass().getInterfaces()[0].getSimpleName(), ServiceFuncs.getStringObjValue(operAttr.value())))
                .reduce(Function.identity(), Function::andThen)
                .apply(operAttrs);

//        newBody = RED_WORDS.stream()
//                .map(toReplace -> (Function<String, String>) s -> s.replaceAll(toReplace, fillInRed(toReplace)))
//                .reduce(Function.identity(), Function::andThen)
//                .apply(newBody);        
        LogService.LogInfo(this.getClass(), () -> printAttrs);
    }
}
