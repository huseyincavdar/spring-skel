package com.cepheid.cloud.skel.model;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.ToString;

@Entity
@ToString
public class Item extends AbstractEntity {

  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "state", nullable = false)
  @Enumerated(EnumType.STRING)
  private State state;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "item_id")
  private List<Description> descriptions = new ArrayList<>();

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public List<Description> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(List<Description> descriptions) {
    this.descriptions = descriptions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
