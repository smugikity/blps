package org.football.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlUser {
    @XmlAttribute
    private Long id;

    @XmlElement
    private String username;

    @XmlElement
    private String password;

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    private Set<ERole> roles;
}
