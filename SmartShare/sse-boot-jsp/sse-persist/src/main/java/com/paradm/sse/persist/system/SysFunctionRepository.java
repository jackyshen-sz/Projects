package com.paradm.sse.persist.system;

import com.paradm.sse.common.enums.FunctionStatus;
import com.paradm.sse.domain.system.entity.SysFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Repository
public interface SysFunctionRepository extends JpaRepository<SysFunction, Integer> {

  List<SysFunction> findByStatusOrderByDisplaySeqAsc(FunctionStatus status);
}
