package com.paradm.sse.persist.dms;

import com.paradm.sse.domain.dms.entity.SmartshareLocMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
@Repository
public interface SmartshareLocMasterRepository extends JpaRepository<SmartshareLocMaster, Integer>, JpaSpecificationExecutor<SmartshareLocMaster> {
}
