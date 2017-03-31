package com.vifu.crms.model;/**
 * Created by mirag on 2017/2/16.
 */

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 测试模板demo
 *
 * @author TangBo
 * @create 2017-02-16 11:27
 **/
@Entity
@Table(name = "t_acctUser",catalog = "crm_ssh")
public class AcctUser {

    private String id;
    private String nickName;
    private String telephone;
    private Date registerTime;
//    private Set<AcctRole> acctRoles = new HashSet<AcctRole>(0);

    public AcctUser() {

    }

    public AcctUser(String id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public AcctUser(String id, String nickName, String telephone,
                    Date registerTime) {
        this.id = id;
        this.nickName = nickName;
        this.telephone = telephone;
        this.registerTime = registerTime;
//        this.acctRoles = acctRoles;
    }

    @Id
    @GeneratedValue(generator = "_uuid")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GenericGenerator(name = "_uuid",strategy = "org.hibernate.id.UUIDGenerator")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "nick_name", nullable = false)
    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_time", length = 19)
    public Date getRegisterTime() {
        return this.registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

  /*  @JsonIgnoreProperties(value={"acctUsers", "acctAuthorities"})
    @ManyToMany(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "acct_user_role", catalog = "work", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
    public Set<AcctRole> getAcctRoles() {
        return this.acctRoles;
    }

    public void setAcctRoles(Set<AcctRole> acctRoles) {
        this.acctRoles = acctRoles;
    }*/
}
