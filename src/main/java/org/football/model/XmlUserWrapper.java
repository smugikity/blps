package org.football.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlUserWrapper {
    @XmlElement(name = "user")
    private List<XmlUser> userList;
}
