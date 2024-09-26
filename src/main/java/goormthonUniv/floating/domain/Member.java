package goormthonUniv.floating.domain;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class Member {

    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }
}