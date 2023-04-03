package com.graphy.backend.domain.project.util;

import com.graphy.backend.domain.project.domain.Project;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification {

    public static Specification<Project> searchWith(final String content, final String projectName) {

        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(content)) {
                predicates.add(builder.like(root.get("content"), "%" + content + "%"));
            }
            if (StringUtils.hasText(projectName)) {
                predicates.add(builder.like(root.get("projectName"), "%" + projectName + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
