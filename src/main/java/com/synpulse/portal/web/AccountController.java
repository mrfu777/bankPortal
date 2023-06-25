package com.synpulse.portal.web;

import com.synpulse.portal.dto.AccountQuery;
import com.synpulse.portal.dto.Result;
import com.synpulse.portal.exception.BusinessException;
import com.synpulse.portal.jwt.Security;
import com.synpulse.portal.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  restful controller about account
 */
@RestController
@ResponseBody
@RequestMapping("/account")
@Api("account api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     *  query Transaction record page list
     * @return
     */
    @GetMapping("/{userName}/{pageSize}/{pageNum}")
    @ApiOperation("query account page list")
    @Security
    public Result accountPageList(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @PathVariable String userName){
        return accountService.accountPageList(pageNum,pageSize,userName);
    }

    /**
     *  query Transaction record page list
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("login")
    public Result login(@RequestBody AccountQuery accountQuery) throws BusinessException {
        return accountService.login(accountQuery);
    }

    /**
     *  query account detail
     * @return
     */
    @GetMapping
    @ApiOperation("get account record by id")
    @Security
    public Result getById(){
        return accountService.getById();
    }

    /**
     * create account
     * @param accountQuery
     * @return
     */
    @PostMapping
    @ApiOperation("create account info")
    public Result createAccount(@RequestBody AccountQuery accountQuery){
        return accountService.createAccount(accountQuery);
    }

    /**
     * update Transaction record
     * @param accountQuery
     * @return
     */
    @PutMapping
    @ApiOperation("update account info")
    @Security
    public Result updateTAccount(@RequestBody AccountQuery accountQuery){
        return accountService.updateTAccount(accountQuery);
    }
    /**
     * delete account info
     * @return
     */
    @ApiOperation("delete account info")
    @DeleteMapping
    @Security
    public Result deleteAccount(){
        return accountService.deleteAccount();
    }
}
