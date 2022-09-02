package com.o2s.data.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Device")
public class Device {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    private String host;
    private Integer port;
    private String alias;
    private String userName;
    private String password;
    private String os;
    private String protocol;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> props = new HashMap<>();
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }   
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }
    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public Map<String, String> getProps() {
        return props;
    }
    public void setProps(Map<String, String> props) {
        this.props = props;
    }

}
