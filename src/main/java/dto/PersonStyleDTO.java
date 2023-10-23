/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import dat3.jpademo.entities.Person;
import dat3.jpademo.entities.SwimStyle;
import java.util.List;

/**
 *
 * @author jobe
 */
public class PersonStyleDTO {

    private String name;
    private int year;
    private List<String> swimStyles;

    public PersonStyleDTO(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public PersonStyleDTO(Person p) {
        this.name = p.getName();
        this.year = p.getYear();
        for (SwimStyle ss : p.getStyles()) {
            this.swimStyles.add(ss.getStyleName());
        }
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

    public List<String> getSwimStyles() {
        return swimStyles;
    }

    public void setSwimStyle(List<String> swimStyle) {
        this.swimStyles = swimStyle;
    }

    public void addSwimStyle(String swimStyle) {
        this.swimStyles.add(swimStyle);
    }

}
