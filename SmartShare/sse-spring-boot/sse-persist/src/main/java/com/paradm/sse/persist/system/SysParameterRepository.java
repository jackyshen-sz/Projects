package com.paradm.sse.persist.system;

import com.paradm.sse.common.enums.RecordStatus;
import com.paradm.sse.domain.system.entity.SysParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Repository
public interface SysParameterRepository extends JpaRepository<SysParameter, Integer> {

  List<SysParameter> findByRecordStatus(RecordStatus recordStatus);

}
