package com.intabia.wikitabia.wrappers;

import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RequestInfoSaver {
  private int page;
  private int size = 10;
  private String sort;
  private String filterByName;
  private List<String> filterByTag;
  private List<String> chosenTags;
}
