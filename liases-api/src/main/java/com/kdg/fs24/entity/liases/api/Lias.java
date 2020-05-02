/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.entity.liases.api;

import com.kdg.fs24.application.core.service.funcs.CustomCollectionImpl;
import com.kdg.fs24.entity.liases.exception.LiasRestIsNegative;
import com.kdg.fs24.lias.opers.napi.NewLiasOper;
import com.kdg.fs24.lias.opers.attrs.*;
import java.time.LocalDate;
import java.util.Collection;
//import com.kdg.fs24.entity.liases.references.LiasesReferencesService;
import com.kdg.fs24.application.core.locale.NLS;
import com.kdg.fs24.application.core.log.LogService;
import com.kdg.fs24.application.core.api.ObjectRoot;
//import com.kdg.fs24.services.api.ServiceLocator;
import com.kdg.fs24.application.core.sysconst.SysConst;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.application.core.service.funcs.ServiceFuncs;
import com.kdg.fs24.lias.opers.api.LiasOpersConst;
import com.kdg.fs24.lias.opers.napi.SaveAccretionHist;
import com.kdg.fs24.application.core.nullsafe.NullSafe;
import com.kdg.fs24.persistence.api.PersistenceEntity;
import javax.persistence.*;
import lombok.Data;

/**
 *
 * @author kazyra_d
 */
@Data
@Entity
@Table(name = "liases")
public class Lias extends ObjectRoot implements PersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lias_id")
    @SequenceGenerator(name = "seq_lias_id", sequenceName = "seq_lias_id", allocationSize = 1)
    @Column(name = "lias_id")
    private Integer liasId;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "allow_date")
    private LocalDate allowDate;
    @Column(name = "final_date")
    private LocalDate finalDate;
    @Column(name = "legal_date")
    private LocalDate legalDate;
    @Column(name = "server_date")
    private LocalDateTime serverDate;
    @Column(name = "inactive_date")
    private LocalDate inactiveDate;
    @Column(name = "is_Cancelled")
    private Boolean isCancelled;

    @OneToMany
    @JoinColumn(name = "lias_id", referencedColumnName = "lias_id")
    private Collection<LiasAction> liasActions;
    
    @OneToMany
    @JoinColumn(name = "lias_id", referencedColumnName = "lias_id")
    private Collection<LiasRest> liasRests;
}
