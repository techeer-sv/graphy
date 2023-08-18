package com.graphy.backend.domain.project.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tech;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private Set<ProjectTag> projects;

}