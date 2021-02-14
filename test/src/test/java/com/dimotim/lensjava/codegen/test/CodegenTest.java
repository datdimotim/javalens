package com.dimotim.lensjava.codegen.test;


import com.dimotim.lensjava.codegen.MakeLens;
import lombok.Value;
import lombok.With;
import org.junit.Assert;
import org.junit.Test;


public class CodegenTest {
    @Test
    public void makeLensTest(){
        String name = "Kate";
        Person p = new Person(name, 22);

        String r = PersonL.name.view(p);

        Assert.assertEquals(name, r);
    }
}

@MakeLens
@With
@Value
class Person{
    String name;
    Integer age;
}
