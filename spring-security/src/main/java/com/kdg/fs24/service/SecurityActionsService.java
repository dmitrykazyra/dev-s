/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdg.fs24.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import com.kdg.fs24.entity.core.api.EntityClassesPackages;

/**
 *
 * @author N76VB
 */
@Data
@Service
@EntityClassesPackages(pkgList = {"com.kdg.fs24.spring.security"})
public class SecurityActionsService extends ActionExecutionService {

}
