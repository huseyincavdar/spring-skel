package com.cepheid.cloud.skel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
public class Description extends AbstractEntity {

  @Column(name = "item_id")
  private Long itemId;
  @Column(name = "description", nullable = false)
  private String description;

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
