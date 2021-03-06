/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.contracts.actions;

//import com.kdg.fs24.entity.contract.EntityContractAbstract;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.lias.opers.attrs.*;
import java.util.Collection;
import java.time.LocalDate;
import com.kdg.fs24.entity.contracts.AbstractEntityServiceContract;
import com.kdg.fs24.entity.core.api.AllowedMethod;
import com.kdg.fs24.entity.core.api.LiasContractAction;

import java.time.temporal.ChronoUnit;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.lias.opers.api.LiasOpersConst;
import lombok.Data;
import com.kdg.fs24.lias.opers.napi.LiasFinanceOper;

/**
 *
 * @author kazyra_d
 */
// абстрактное действие создающие финансовые операции над договором
@Data
@LiasContractAction
//@PreViewDialog(dialog_name = "create_contract_lias_opers")
//@AskDateDialog(dialogName = "get_calc_dates")
@AllowedMethod(action = AbstractLiasContractOper.class, entity = AbstractEntityServiceContract.class)
public abstract class AbstractLiasContractOper<T extends AbstractEntityServiceContract>
        extends AbstractContractDocumentAction<T> {

    // коллекция для создания финопераций
    final private Collection<LiasFinanceOper> newOpers
            = ServiceFuncs.<LiasFinanceOper>createCollection();
    // дата начисления
    private LocalDate accretionDate = LocalDate.now();
    // дата исполнения
    private LocalDate liasDate = LocalDate.now();
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

        if (!(NullSafe.notNull(this.getNewOpers()) && (!this.getNewOpers().isEmpty()))) {

            throw new LiasActionsNotSpecified("Нет операций для выполнения!");
        }
        // обработка новых операций
        this.getContractEntity()
                .applyNewLiasOpers(this.getNewOpers());
//        this
//                .getNewOpers()
//                .stream()
//                .unordered()
//                .sorted((op1, op2) -> (op1.<Integer>attr(ROW_NUM.class)).compareTo(op2.<Integer>attr(ROW_NUM.class)))
//                .forEach(oper -> this.applyNewOper(oper));

    }
    //==========================================================================

    protected void applyNewOper(final LiasFinanceOper liasFinanceOper) {

        // знак операции (увеличение\уменьшение обязательства)
    }

    //==========================================================================
    protected void addNewLiasOper(final LiasFinanceOper lio) {
        lio.<ROW_NUM>addAttr(() -> Integer.valueOf(this.getNewOpers().size() /*+ 1*/));

        // код шаблона документа не задан
        if ((NullSafe.isNull(lio.<Integer>attr(LiasOpersConst.DOC_TEMPLATE_ID_CLASS)))
                && (NullSafe.notNull(this.getDefaultDocTemplate()))) {
            // берем код документа по умолчанию
//            lio.<DOC_TEMPLATE_ID>addAttr(() -> Integer.valueOf(ServiceLocator
//                    .find(DocumentReferencesService.class)
//                    .getDocTemplateById(this.getDefaultDocTemplateId())
//                    .getDoc_template_id()));
            lio.<DOC_TEMPLATE_ID>addAttr(() -> this.getDefaultDocTemplate());

        }
        this.getNewOpers().add(lio);

        if (SysConst.DEBUG_MODE.get()) {
            lio.printOperAttrsCollection();
        }
        //      }

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
    // присвоение финансовой операции кода шаблона
    // может быть перекрыто в наследнике
//    protected void assignDocTemplate(final OldLiasOper liasFinanceOper) {
//        // по умолчанию - 1 операция - 1 документ
//        // берем из аннотации на классе
//        //liasFinanceOper.setDocTemplateId(1);
//        liasFinanceOper.<DOC_TEMPLATE_ID>addAttr(() -> Integer.valueOf(1));
//    }
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
//    public final OldLiasOper findNewLiasOper(
//            final Integer liasFinOperCode,
//            final Integer liasActionTypeId) {
//
//        return (OldLiasOper) NullSafe.create()
//                .execute2result(() -> {
//
//                    final List<OldLiasOper> list
//                            = this.getNewOpers()
//                                    .stream()
//                                    .unordered()
//                                    .filter(oper -> oper.<Integer>attr(LiasOpersConst.LIAS_FINOPER_CODE_CLASS).equals(liasFinOperCode))
//                                    .filter(oper -> oper.<Integer>attr(LIAS_ACTION_TYPE_ID.class).equals(liasActionTypeId))
//                                    .collect(Collectors.toList());
//
////                    if (list.isEmpty()) {
////                        throw new LiasOperNotFoundException(String.format("LiasOper is not found(liasFinOperCode = %d, liasActionTypeId = %d)", liasFinOperCode, liasActionTypeId));
////                    }
////
////                    if (list.size() > 1) {
////                        throw new DuplicatedLiasOperException(String.format("More then opers found(liasFinOperCode = %d, liasActionTypeId = %d)", liasFinOperCode, liasActionTypeId));
////                    }
//
//                    return list.get(0);
//
//                }).<T>getObject();
//    }
    //==========================================================================    
    //==========================================================================
    @Override
    protected void finallyExecute() {
        super.finallyExecute();
        this.getNewOpers().clear();
    }

//    protected void setNewOpers(final Collection<NewLiasOper> newOpers) {
//        this.newOpers = newOpers;
//    }
    // присвоение финансовой операции кода шаблона
    // может быть перекрыто в наследнике
    protected Integer getDefaultDocTemplate() {
        // по умолчанию - 1 операция - 1 документ
        // берем из аннотации на классе
        //liasOperInfo.setDocTemplateId(1);
        return Integer.valueOf(1);
    }
}

class LiasActionsNotSpecified extends RuntimeException {

    public LiasActionsNotSpecified(final String message) {
        super(message);
    }
}
