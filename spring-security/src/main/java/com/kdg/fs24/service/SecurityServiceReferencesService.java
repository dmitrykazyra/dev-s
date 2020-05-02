/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import com.kdg.fs24.entity.core.api.ReferencesClassesPackages;
import com.kdg.fs24.spring.core.service.AbstractReferencesService;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 *
 * @author N76VB
 */

@Data
@Service
@ReferencesClassesPackages(pkgList = {"com.kdg.fs24.entity.security"})
public class SecurityServiceReferencesService extends AbstractReferencesService {

}
