/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.lias.opers.napi;

import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.lias.opers.api.DocAttrLinkProperty;
import java.util.Collection;
import java.util.Optional;
import java.util.Map;
import lombok.Data;

/**
 *
 * @author N76VB
 */
@Data
public class LiasFinanceOper {

    final private Collection<OperAttr> operAttrsCollection
            = ServiceFuncs.<OperAttr>createCollection();

    final public static Map<Integer, Class<? extends OperAttr>> LINKED_FIELDS
            = ServiceFuncs.<Integer, Class<? extends OperAttr>>getOrCreateMap(ServiceFuncs.MAP_NULL);

    //==========================================================================
    public <LD extends OperAttr> LiasFinanceOper addAttr(final LD attrValue) {

//        final Object value = attrValue.value();
//        final String logRecord = String.format("attrValue: %s = (%s) %s",
//                attrValue.getClass().getInterfaces()[0].getSimpleName(),
//                NullSafe.notNull(value) ? value.getClass().getSimpleName() : SysConst.UNKNOWN,
//                value);
//
//        LogService.LogInfo(attrValue.getClass(), () -> logRecord);
        NullSafe.runNewThread(() -> {
            if (attrValue.getClass().getInterfaces()[0].isAnnotationPresent(DocAttrLinkProperty.class)) {
                synchronized (LiasFinanceOper.LINKED_FIELDS) {
                    final DocAttrLinkProperty dal = ((DocAttrLinkProperty) attrValue.getClass().getInterfaces()[0].getAnnotation(DocAttrLinkProperty.class));
                    LiasFinanceOper.LINKED_FIELDS.put(dal.docAttr(), (Class) attrValue.getClass().getInterfaces()[0]);
                }
            }
        });

        this.getOperAttrsCollection().add(attrValue);
        return this;
    }

    //==========================================================================
    public <V> V attr(final Class<? extends OperAttr> clazz) {

        final Optional<OperAttr> operAttr = ServiceFuncs.<OperAttr>getCollectionElement(
                this.getOperAttrsCollection(),
                attr -> attr.getClass().getInterfaces()[0].getName().equals(clazz.getName()));

        return (V) (operAttr.isPresent() ? operAttr.get().value() : null);
    }

    //==========================================================================
    public <V> V attrDef(final Class<? extends OperAttr> clazz, final V defaultValue) {

        return (V) (NullSafe.nvl(attr(clazz), defaultValue));
    }

    //==========================================================================
    public void printOperAttrsCollection() {

//        newBody = RED_WORDS.stream()
//                .map(toReplace -> (Function<String, String>) s -> s.replaceAll(toReplace, fillInRed(toReplace)))
//                .reduce(Function.identity(), Function::andThen)
//                .apply(newBody);        
        LogService.LogInfo(this.getClass(), () -> this.operAttrsCollection
                .stream()
                .sorted((r1, r2) -> r1.getClass()
                .getInterfaces()[0]
                .getSimpleName()
                .compareTo(r2.getClass()
                        .getInterfaces()[0]
                        .getSimpleName()))
                .map(operAttr -> String.format("%30s: %s\n", operAttr.getClass().getInterfaces()[0].getSimpleName(), ServiceFuncs.getStringObjValue(operAttr.value())))
                .reduce(String.format("LiasOpers attributes (%d): \n",
                        LiasFinanceOper.this.operAttrsCollection.size()),
                        (x, y) -> x.concat(y)));
    }
    //==========================================================================
    //@JsonIgnore
//    private static Map<Integer, Class<? extends OperAttr>> linkedFields;
//
//    public Map<Integer, Class<? extends OperAttr>> getLinkedFields() {
//        synchronized (LiasFinanceOper.class) {
//
//            NullSafe.create(LiasFinanceOper.linkedFields)
//                    .whenIsNull(() -> {
//
//                        this.linkedFields = ServiceFuncs.<Integer, Class<? extends OperAttr>>getOrCreateMap(ServiceFuncs.MAP_NULL);
//
//                        this.operAttrsCollection
//                                .stream()
//                                .unordered()
//                                .filter(operAttr -> operAttr.getClass().isAnnotationPresent(DocAttrLinkProperty.class))
//                                .forEach((operAttr) -> {
//                                    final DocAttrLinkProperty dal = ((DocAttrLinkProperty) operAttr.getClass().getAnnotation(DocAttrLinkProperty.class));
//                                    LiasFinanceOper.linkedFields.put(dal.docAttr(), operAttr.getClass());
//                                });
//                        return LiasFinanceOper.linkedFields;
//                    });
//        }
//        return LiasFinanceOper.linkedFields;
//    }
}
