/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.liases.api;

import com.kdg.fs24.references.liases.actiontype.LiasActionType;
import com.kdg.fs24.references.liases.finopercode.LiasFinOperCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.kdg.fs24.lias.opers.attrs.*;
//import com.kdg.fs24.log.mgmt.LogService;
import com.kdg.fs24.application.core.api.ObjectRoot;
import com.kdg.fs24.application.core.sysconst.SysConst;
import com.kdg.fs24.lias.opers.napi.SaveAccretionHist;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "liasActions")
public class LiasAction extends ObjectRoot implements PersistenceEntity {

    // код финансовой операции
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lias_action_id")
    @SequenceGenerator(name = "seq_lias_action_id", sequenceName = "seq_lias_action_id", allocationSize = 1)
    @Column(name = "lias_action_id")
    private Long liasActionId;

    @ManyToOne
    @JoinColumn(name = "fin_oper_code", referencedColumnName = "fin_oper_code")
    private LiasFinOperCode liasFinOperCode;
    // тип финансовой операции
    @JoinColumn(name = "action_type_id", referencedColumnName = "action_type_id")
    private LiasActionType liasActionType;
    // сумма финансовой операции
    @Column(name = "lias_sum")
    private BigDecimal liasSum;
    // дата финансовой операции
    @Column(name = "lias_date")
    private LocalDate liasDate;

    @Column(name = "doc_id")
    private Long docId;
    @Column(name = "lias_id")
    private Integer liasId;
    @Column(name = "server_date")
    private LocalDateTime serverDate;
    @Column(name = "status")
    private Integer status;
    private int liasOperHC;

    private SaveAccretionHist sah;
}
