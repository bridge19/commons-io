package io.bridge.common.biz.response;

import java.util.List;
import lombok.Data;

@Data
public class PageableEntity <T> {

  private List<T> result;
  private Long total;
}
