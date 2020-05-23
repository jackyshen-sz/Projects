package com.paradm.sse.domain.framework.model;

import com.paradm.sse.domain.framework.entity.IdEntity;
import lombok.Data;

import java.io.*;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Data
public abstract class BaseModel implements Serializable {

  private static final long serialVersionUID = -6312660614459105867L;

  private Integer page = 0;
  private Integer pageSize = 20;

  private String id = null;
  private String recordStatus = null;
  private String updateCount = null;
  private String updaterId = null;
  private String updateDate = null;
  private String updateName = null;
  private String creatorId = null;
  private String createDate = null;
  private String createName = null;

  private String companyId = null;

  @Override
  public Object clone() {
    Object deepCopy = null;
    try {
      // serialize me into byte array
      ByteArrayOutputStream baos = new ByteArrayOutputStream(100);
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(this);
      byte[] buf = baos.toByteArray();
      oos.close();

      // deserialize byte array into a new object
      ByteArrayInputStream bais = new ByteArrayInputStream(buf);
      ObjectInputStream ois = new ObjectInputStream(bais);
      deepCopy = ois.readObject();
      ois.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return deepCopy;
  }

  public abstract void setModelData(IdEntity entity);

  public abstract IdEntity getEntityData();
}
