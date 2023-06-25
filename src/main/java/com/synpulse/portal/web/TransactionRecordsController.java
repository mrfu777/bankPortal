package com.synpulse.portal.web;

import com.synpulse.portal.dto.Result;
import com.synpulse.portal.dto.TransactionQuery;
import com.synpulse.portal.exception.BusinessException;
import com.synpulse.portal.jwt.Security;
import com.synpulse.portal.service.TransactionRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * restful controller about Transaction record
 */
@RestController
@ResponseBody
@RequestMapping("/transactionRecords")
@Api("Transaction record")
public class TransactionRecordsController {

    @Autowired
    private TransactionRecordsService transactionRecordsService;

    /**
     *  query Transaction record page list
     * @param TransactionQuery
     * @return
     */
    @GetMapping
    @ApiOperation("query Transaction page list")
    @Security
    public Result Transactions(@RequestBody TransactionQuery TransactionQuery){
        return transactionRecordsService.transactions(TransactionQuery);
    }

    /**
     *  query Transaction record detail
     * @return
     */
    @GetMapping("/{guid}")
    @ApiOperation("get Transaction record by guid")
    @Security
    public Result Transactions(@PathVariable String guid){
        return transactionRecordsService.getById(guid);
    }

    /**
     * create Transaction record
     * @param transactionQuery
     * @return
     */
    @PostMapping
    @ApiOperation("create Transaction record")
    @Security
    public Result createTransactions(@Validated @RequestBody TransactionQuery transactionQuery) throws BusinessException {
        return transactionRecordsService.createTransactions(transactionQuery);
    }

//    /**
//     * update Transaction record
//     * @param TransactionQuery
//     * @return
//     */
//    @PutMapping
//    @ApiOperation("update Transaction record")
//    public Result updateTransactions(@RequestBody TransactionQuery TransactionQuery){
//
//        return null;
//    }
    /**
     * delete Transaction record
     * @return
     */
    @ApiOperation("delete Transaction record")
    @DeleteMapping("/{guid}")
    @Security
    public Result deleteTransactions(@PathVariable String guid){
        return transactionRecordsService.deleteTransactions(guid);
    }
}
