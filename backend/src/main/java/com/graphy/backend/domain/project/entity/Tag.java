package com.graphy.backend.domain.project.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "Tag")
@Entity
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tech;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<ProjectTag> projects = new ArrayList<>();

}
