package com.cepheid.cloud.skel.model.http;

import com.cepheid.cloud.skel.model.State;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

  private State state;
  private String name;
  private List<DescriptionDto> descriptions;
}
