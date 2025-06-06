package org.o2s.orm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.o2s.orm.enums.EnvironmentType;
import org.o2s.orm.enums.Status;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Entity
@Table(name="Environment")
@NamedQuery(name = "Environment.findAll", query = "SELECT e FROM Environment e ORDER BY e.name", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Environment {

    @Id
    @SequenceGenerator(name = "envSequence", sequenceName = "env_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "envSequence")
    private Integer id;
    private String name;
    private EnvironmentType type;
    private String description;
    private String country;
    private String state;
    private String city;
    private Status status;

//    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "env_id")
    private List<Device> devices = new ArrayList<>();

}
