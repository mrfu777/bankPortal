package com.synpulse.portal.repository;


import com.synpulse.portal.po.TransactionRecords;
import com.synpulse.portal.repository.condition.TransactionRecordCondition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>

 */
public interface TransactionRecordsRepository extends IService<TransactionRecords> {

    IPage<TransactionRecords> pageList(TransactionRecordCondition TransactionRecordCondition);

    Object getByGuid(String guid);

    void deleteByGuid(String guid);
}
