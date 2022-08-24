package com.intabia.wikitabia.service.specifications;

import com.intabia.wikitabia.model.ResourceEntity;
import com.intabia.wikitabia.model.ResourceEntity_;
import com.intabia.wikitabia.model.TagEntity;
import com.intabia.wikitabia.model.TagEntity_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * спецификация для запросов к ресурсам.
 */
public class ResourcesQuerySpecifications {

  /**
   * фильтрация по названию ресурса и тэгам.
   *
   * @param name     - название ресурса
   * @param tagsName - список тэгов
   * @return возвращает спецификацию, фильтрующую ресурсы по названию и тэгам
   */
  public static Specification<ResourceEntity> filter(String name, List<String> tagsName) {
    return (root, query, criteriaBuilder) -> {
      Predicate filterByName = criteriaBuilder.like(root.get(ResourceEntity_.NAME), name + "%");
      if (tagsName == null || tagsName.size() == 0) {
        return filterByName;
      }
      Join<ResourceEntity, TagEntity> join = root.join(ResourceEntity_.TAGS);
      Predicate filterByTag = getJoinPredicate(join, criteriaBuilder, tagsName);

      return criteriaBuilder.and(filterByName, filterByTag);
    };
  }

  private static Predicate getJoinPredicate(Join<ResourceEntity, TagEntity> join,
                                            CriteriaBuilder criteriaBuilder,
                                            List<String> tagsNames) {
    List<Predicate> predicateList = new ArrayList<>();
    tagsNames.forEach(
        tagName -> predicateList.add(criteriaBuilder.equal(join.get(TagEntity_.NAME), tagName)));
    int size = predicateList.size();
    return criteriaBuilder.and(predicateList.toArray(new Predicate[size]));
  }
}
