package org.o2s.orm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.o2s.orm.enums.DeviceType;
import org.o2s.orm.enums.Status;

import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name="Device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String host;
    private Integer port;
    private String alias;
    private Status status;

    @Column(name="uname")
    private String user;
    private String password;
    private String os;
    private String protocol;
    private DeviceType type;
    private String basePath;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="env_id", nullable=false)
    private Environment environment;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> props = new HashMap<>();
}
