/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpademo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.eclipse.persistence.jpa.config.Cascade;

@Entity
@Table(name = "person")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE p.id = :id"),
    @NamedQuery(name = "Person.findByYear", query = "SELECT p FROM Person p WHERE p.year = :year"),
    @NamedQuery(name = "Person.deleteAllRows", query = "DELETE FROM Person p"),
    @NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE p.name = :name")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    private String name;
    private int year;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;
    
    @OneToMany(mappedBy = "person",  cascade = {CascadeType.PERSIST,  CascadeType.REMOVE})
    private List<Fee> fees;
    
    @ManyToMany(mappedBy = "persons",  cascade = {CascadeType.PERSIST,  CascadeType.REMOVE})
    private List<SwimStyle> styles;

    public Person() {
    }
    
    public Person(String name, int year, Address address, List<Fee> fees, List<SwimStyle> styles) {
        this.name = name;
        this.year = year;
        this.address = address;
        this.fees = fees;
        this.styles = styles;
    }
    
    public List<SwimStyle> getStyles() {
        return styles;
    }
    
    public List<Fee> getFees() {
        return fees;
    }

    public void addFee(Fee fee) {
        this.fees.add(fee);
        if (fee != null){
            fee.setPerson(this);
        }
    }
    
    public void addSwimStyle(SwimStyle style){
        if (style != null){
            this.styles.add(style);
            style.getPersons().add(this);
        }
    }
    
    public void removeSwimStyle(SwimStyle swimStyle){
        if (swimStyle != null){
            styles.remove(swimStyle);
            swimStyle.getPersons().remove(this);    
        }
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    public void setStyles(List<SwimStyle> styles) {
        this.styles = styles;
    }
   
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        if(address != null){
            address.setPerson(this);
            
        }
    }
    

    public Long getId() {
        return id;
    }

    public Person(String name, int year) {
        this.name = name;
        this.year = year;
        this.fees = new ArrayList<>();
        this.styles = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    
}
