package com.epam.training;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaly_Krasovsky.
 */
@Entity
@Table(name = "CLIENT")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    //    @JsonBackReference
    @JsonManagedReference
    private List<Account> accounts = new ArrayList<Account>();

    public Client() {
    }

    @Id
    @GeneratedValue
    @Column(name = "CLIENT_ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "AGE")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }


    public void addAccounts(List<Account> accounts) {
        this.accounts = accounts != null ? accounts : new ArrayList<Account>();
        for (Account account : this.accounts) {
            account.setClient(this);
        }
    }

    public void removeAccounts() {
        if (this.accounts != null) {
            for (Account account : this.accounts) {
                account.setClient(null);
            }
        }
        this.accounts = /*null*/new ArrayList<Account>();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
