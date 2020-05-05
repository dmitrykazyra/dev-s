/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contracts.actions;

//import com.kdg.fs24.entity.contract.EntityContractAbstract;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.lias.opers.attrs.*;
import com.kdg.fs24.lias.opers.napi.NewLiasOper;
import java.util.Collection;
import java.time.LocalDate;
import com.kdg.fs24.entity.contracts.AbstractEntityContract;
import com.kdg.fs24.entity.core.api.AllowedMethod;
import com.kdg.fs24.entity.core.api.AskDateDialog;
import com.kdg.fs24.entity.core.api.LiasContractAction;

import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.List;

import com.kdg.fs24.references.api.DocumentsConst;
import com.kdg.fs24.references.documents.docstatus.DocStatus;
import com.kdg.fs24.references.api.DocAttrValue;
import com.kdg.fs24.references.documents.doctemplate.DocTemplate;
//import com.kdg.fs24.entity.contract.api.EntityContract;
import com.kdg.fs24.lias.opers.api.LiasOpersConst;
import com.kdg.fs24.application.core.locale.NLS;
import com.kdg.fs24.references.api.DocTemplateId;
import com.kdg.fs24.application.core.sysconst.SysConst;
import java.util.Arrays;
//import com.kdg.fs24.liases.templates.AbstractLiasOpersTemplate;
import com.kdg.fs24.references.api.LiasesConst;
import com.kdg.fs24.lias.opers.attrs.ROW_NUM;
import com.kdg.fs24.lias.opers.attrs.DOC_TEMPLATE_ID;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.test.api.TestConst;
import com.kdg.fs24.application.core.service.funcs.ReflectionFuncs;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
// абстрактное действие создающие финансовые операции над договором
@Data
@LiasContractAction
//@PreViewDialog(dialog_name = "create_contract_lias_opers")
//@AskDateDialog(dialogName = "get_calc_dates")
@AllowedMethod(action = AbstractLiasContractOper.class, entity = AbstractEntityContract.class)
public abstract class AbstractLiasContractOper<T extends AbstractEntityContract>
        extends AbstractContractDocumentAction<T> {

    // коллекция для создания финопераций
    final private Collection<NewLiasOper> newOpers
            = ServiceFuncs.<NewLiasOper>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
    // дата начисления
    private LocalDate accretionDate;
    // дата исполнения
    private LocalDate liasDate;
    // коллекция шаблонов для создания финопераций
//    private final Collection<Class<AbstractLiasOpersTemplate>> liasOpersTemplates
//            = ReflectionFuncs.<Class<AbstractLiasOpersTemplate>>createPkgClassesCollection(LiasesConst.LIASES_TEMLATE_PKG, AbstractLiasOpersTemplate.class);

    //==========================================================================
    //==========================================================================
//    public static Boolean isAllowed(final AbstractEntityContract entity) {
//
//        return entity.getIsAuthorized();
//        //return true;
//    }
    //==========================================================================
    @Override
    public void initialize() {

        super.initialize();
        // запрос даты расчета операции
        accretionDate = LocalDate.now(); // получить дату последнего начисления
        liasDate = LocalDate.now().plus(2, ChronoUnit.DAYS);

        // код для дополниельной инициализации действия при тестировании
//        if (TestConst.TEST_MODE_RUNNING) {
//            this.getEntity().processActionTest_afterInitialize(this);
//        }
    }

    //==========================================================================
    @Override
    protected void doCalculation() {

//        LogService.LogInfo(this.getClass(), LogService.getCurrentObjProcName(this),
//                String.format("LiasDate:%s, AccretionDate:%s", liasDate, accretionDate));
        this.preCalculation();

        // код для тестирования действия
//        if (TestConst.TEST_MODE_RUNNING) {
//            this.getEntity().processActionTest_afterCalculation(this);
//        }
//        if (!(NullSafe.notNull(this.getOpers4creation()) && (!this.getOpers4creation().isEmpty()))) {
//            throw new LiasActionsNotSpecified("Нет операций для выполнения!");
//        }
        if (!(NullSafe.notNull(this.getNewOpers()) && (!this.getNewOpers().isEmpty()))) {

            throw new LiasActionsNotSpecified("Нет операций для выполнения!");
        }
    }

    //==========================================================================

    //==========================================================================
    protected void addNewLiasOper(final NewLiasOper lio) {
        lio.<ROW_NUM>addAttr(() -> Integer.valueOf(this.getNewOpers().size() /*+ 1*/));

        // код шаблона документа не задан
        if ((NullSafe.isNull(lio.<Integer>attrValue(DOC_TEMPLATE_ID.class)))
                && (NullSafe.notNull(this.getDefaultDocTemplateId()))) {
            // берем код документа по умолчанию
//            lio.<DOC_TEMPLATE_ID>addAttr(() -> Integer.valueOf(ServiceLocator
//                    .find(DocumentReferencesService.class)
//                    .getDocTemplateById(this.getDefaultDocTemplateId())
//                    .getDoc_template_id()));
        }
        this.getNewOpers().add(lio);

        if (TestConst.TEST_MODE_RUNNING) {
            lio.printOperAttrsCollection();
        }

//        LogGate.LogInfo(this.getClass(), String.format("create new liasoper (%d)",
//                this.getOpers4creation().size()));
    }

    //--------------------------------------------------------------------------
    // сохранение созданных\измененных обязательств
    //--------------------------------------------------------------------------
//    @Override
//    protected void doUpdate() {
//        super.doUpdate();
//        NullSafe.create()
//                .execute(() -> {
//                    // сохранение обязательств
//                    this.getContractEntity()
//                            .getContractLiasDebts()
//                            .bind(this.getDocument4creation())
//                            .store();
//                }).throwException();
    // создание документов
//    }
    //==========================================================================
    protected abstract void preCalculation();

    //==========================================================================
    @Override
    protected void afterCalculation() {
        super.afterCalculation();
    }

    //==========================================================================    
//    protected Collection<DocAttrValue> createNewDocAttrs(final NewLiasOper lio) {
//        final DocTemplateId dti = ServiceLocator
//                .find(DocumentReferencesService.class)
//                .getDocTemplateClassById(lio.<Integer>attrValue(DOC_TEMPLATE_ID.class));
//
//        return this.processDocAttrs(dti, this.getEntity(), lio);
//
//    }

//    //==========================================================================
//    private <T extends EntityContract> Collection<DocAttrValue> processDocAttrs(
//            final DocTemplateId dti,
//            final T contract,
//            final NewLiasOper liasOperInfo) {
//
//        final Collection<DocAttrValue> dac = ServiceFuncs.<DocAttrValue>getOrCreateCollection(ServiceFuncs.COLLECTION_NULL);
//
//        NullSafe.create(dti)
//                .safeExecute(() -> {
//
//                    NullSafe.create()
//                            .execute(() -> {
//
//                                // добавляем в коллекцию
//                                Arrays.stream(dti.attrsList())
//                                        .unordered()
//                                        .forEach((ai) -> {
//                                            liasOperInfo
//                                                    .getLinkedFields()
//                                                    .entrySet()
//                                                    .stream()
//                                                    .unordered()
//                                                    .filter(s -> s.getKey().equals(ai))
//                                                    .forEach((fields) -> {
//
//                                                        final String attrValue = (String) NLS.getObject2String(
//                                                                NullSafe.create()
//                                                                        .execute2result(() -> {
//                                                                            //return fields.getValue().get(liasOperInfo);
//                                                                            return liasOperInfo.attrValue((Class) fields.getValue().getClass());
//                                                                        })
//                                                                        .whenIsNull(() -> {
//                                                                            return SysConst.NOT_DEFINED;
//                                                                        })
//                                                                        .<T>getObject());
//
//                                                        final DocAttrValue dav = new DocAttrValue() {
//
//                                                            @Override
//                                                            public String getStringDocAttr() {
//                                                                return SysConst.EMPTY_STRING;
//                                                            }
//
//                                                            @Override
//                                                            public int getDocAttrId() {
//                                                                return ai;
//                                                            }
//
//                                                            //==========================
//                                                            @Override
//                                                            public Object getDocAttrValue() {
//                                                                return attrValue;
//                                                            }
//                                                        };
//                                                        dac.add(dav);
//                                                    });
//                                        });
//                            });
//                });
//        return dac;
//    }

    //==========================================================================
    // присвоение финансовой операции кода шаблона
    // может быть перекрыто в наследнике
    protected void assignDocTemplate(final NewLiasOper liasOperInfo) {
        // по умолчанию - 1 операция - 1 документ
        // берем из аннотации на классе
        //liasOperInfo.setDocTemplateId(1);
        liasOperInfo.<DOC_TEMPLATE_ID>addAttr(() -> Integer.valueOf(1));
    }
    //==========================================================================
    // инициализация атрибутов документа

    // инициализация формы предварительного просмотра
    //--------------------------------------------------------------------------
//    @Override
//    protected void initPreviewForm() throws InternalAppException {
//
//    }
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------    
    // для процедуры тестирования (с целью найти операцию и заменить в ней что-нибудь)
    public final NewLiasOper findNewLiasOper(
            final Integer liasFinOperCode,
            final Integer liasActionTypeId) {

        return (NewLiasOper) NullSafe.create()
                .execute2result(() -> {

                    final List<NewLiasOper> list
                            = this.getNewOpers()
                                    .stream()
                                    .unordered()
                                    .filter(oper -> oper.<Integer>attrValue(LiasOpersConst.LIAS_FINOPER_CODE_CLASS).equals(liasFinOperCode))
                                    .filter(oper -> oper.<Integer>attrValue(LIAS_ACTION_TYPE_ID.class).equals(liasActionTypeId))
                                    .collect(Collectors.toList());

//                    if (list.isEmpty()) {
//                        throw new LiasOperNotFoundException(String.format("LiasOper is not found(liasFinOperCode = %d, liasActionTypeId = %d)", liasFinOperCode, liasActionTypeId));
//                    }
//
//                    if (list.size() > 1) {
//                        throw new DuplicatedLiasOperException(String.format("More then opers found(liasFinOperCode = %d, liasActionTypeId = %d)", liasFinOperCode, liasActionTypeId));
//                    }

                    return list.get(0);

                }).<T>getObject();
    }

    //==========================================================================    
    public Collection<NewLiasOper> getNewOpers() {
        return this.newOpers;
    }

    //==========================================================================
    @Override
    protected void finallyExecute() {
        super.finallyExecute();
        this.getNewOpers().clear();
    }

//    protected void setNewOpers(final Collection<NewLiasOper> newOpers) {
//        this.newOpers = newOpers;
//    }
}

class LiasActionsNotSpecified extends RuntimeException {

    public LiasActionsNotSpecified(final String message) {
        super(message);
    }
}
