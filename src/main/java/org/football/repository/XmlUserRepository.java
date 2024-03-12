package org.football.repository;

import org.football.model.ERole;
import org.football.model.User;
import org.football.model.XmlUser;
import org.football.model.XmlUserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class XmlUserRepository {
    private static List<XmlUser> userList = new ArrayList<>();
    @Autowired
    private UserRepository userRepository;
    @Value("${xml_path}")
    private String xmlFilePath;

    public XmlUserRepository() {
    }

    public static XmlUser findById(Long id) {
        for (XmlUser user : userList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null; // Return null if the entity with the given ID is not found
    }

    public static XmlUser findByUsernameAndPassword(String username, String password) {
        for (XmlUser user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @PostConstruct
    private synchronized void loadAll() throws JAXBException {
        File file = new File(xmlFilePath);
        if (file.exists()) {
            JAXBContext context = JAXBContext.newInstance(XmlUserWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XmlUserWrapper wrapper = (XmlUserWrapper) unmarshaller.unmarshal(file);
            userList = wrapper.getUserList();
            for (XmlUser xmlUser : userList) {
                User user = new User(xmlUser.getId());
                userRepository.save(user);
            }
        }
    }

    @PreDestroy
    private synchronized void saveAll() throws JAXBException {
        File file = new File(xmlFilePath);
        JAXBContext context = JAXBContext.newInstance(XmlUserWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        XmlUserWrapper wrapper = new XmlUserWrapper();
        wrapper.setUserList(userList);
        marshaller.marshal(wrapper, file);
    }

    public boolean existsByUsername(String username) {
        for (XmlUser user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public XmlUser findByUsername(String username) {
        for (XmlUser user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public List<XmlUser> findAll() {
        return userList;
    }

    public void delete(Long id) {
        XmlUser userToRemove = null;
        for (XmlUser user : userList) {
            if (user.getId().equals(id)) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            userList.remove(userToRemove);
        }
    }

    public XmlUser save(XmlUser user) {
        try {
            userList.add(user);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public XmlUser create(String username, String password) {
        try {
            XmlUser user = new XmlUser(userList.size() + 1L, username, password, Set.of(ERole.USER));
            userList.add(user);
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}

