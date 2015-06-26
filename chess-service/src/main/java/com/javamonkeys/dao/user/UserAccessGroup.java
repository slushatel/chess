package com.javamonkeys.dao.user;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "UserAccessGroups")
public class UserAccessGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "isadmin")
    private boolean isAdmin;

    @OneToMany(mappedBy="userAccessGroup")
    private Set<User> users;

    protected UserAccessGroup(){}

    public UserAccessGroup(String name, boolean isAdmin){
        setName(name);
        setIsAdmin(isAdmin);
    }

    /**
     * Get user access group ID.
     * @return current user access group ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set user access group ID
     * @param id new user access group ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get user access group name.
     * @return current user access group name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user access group name.
     * @param name new user access group name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get user access group "is admin" attribute.
     * @return current user access group "is admin" attribute
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Set user access group "is admin" attribute
     * @param isAdmin new user access group "is admin" attribute
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
