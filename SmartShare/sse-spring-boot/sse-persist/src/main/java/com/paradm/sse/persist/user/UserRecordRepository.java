package com.paradm.sse.persist.user;

import com.paradm.sse.domain.user.entity.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Repository
public interface UserRecordRepository extends JpaRepository<UserRecord, Integer>, JpaSpecificationExecutor<UserRecord> {

}
