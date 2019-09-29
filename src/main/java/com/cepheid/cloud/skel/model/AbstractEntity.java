package com.cepheid.cloud.skel.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long mId;

  public Long getId() {
    return mId;
  }
  
  public void setId(Long id) {
    mId = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mId == null) ? 0 : mId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractEntity other = (AbstractEntity) obj;
    if (mId == null) {
      if (other.mId != null)
        return false;
    }
    else if (!mId.equals(other.mId))
      return false;
    return true;
  }
  
  
}
