package com.dimotim.lensjava.codegen;


import lombok.Value;
import lombok.With;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CodegenTest {
    @Test
    public void makeLensTest(){
        String name = "Kate";
        Person p = new Person(name, 22);

        String r = PersonL.name.view(p);

        Assertions.assertEquals(name, r);
    }
}

@MakeLens
@With
@Value
class Person{
    String name;
    Integer age;
}
