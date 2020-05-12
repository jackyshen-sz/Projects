package com.paradm.sse.framework.dialect;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
public class ParadmSQLServerDialect extends SQLServer2012Dialect {

  public ParadmSQLServerDialect() {
    super();
    registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
  }
}
