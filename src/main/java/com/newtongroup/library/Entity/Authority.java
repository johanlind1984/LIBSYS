package com.newtongroup.library.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="authority_id")
    private long autorityId;

    @Column(name="authority")
    private String authorityName;

    @OneToMany(mappedBy = "authority")
    private Set<User> authorityUserId;

    public Authority() {
    }

    public long getAutorityId() {
        return autorityId;
    }

    public void setAutorityId(long autorityId) {
        this.autorityId = autorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Set<User> getAuthorityUserId() {
        return authorityUserId;
    }

    public void setAuthorityUserId(Set<User> authorityUserId) {
        this.authorityUserId = authorityUserId;
    }
}
