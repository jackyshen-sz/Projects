package com.paradm.sse.persist.company;

import com.paradm.sse.common.enums.RecordStatus;
import com.paradm.sse.domain.company.entity.ParadmCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jacky.shen
 * @create data 2020/5/29
 */
@Repository
public interface ParadmCompanyRepository extends JpaRepository<ParadmCompany, Integer>, IParadmCompanyDao {

  List<ParadmCompany> findByRecordStatus(RecordStatus recordStatus);
}
