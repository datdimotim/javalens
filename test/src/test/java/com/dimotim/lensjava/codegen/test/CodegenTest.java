package com.dimotim.lensjava.codegen.test;


import com.dimotim.lensjava.codegen.test.model.City;
import com.dimotim.lensjava.codegen.test.model.CityL;
import com.dimotim.lensjava.codegen.test.model.Person;
import com.dimotim.lensjava.codegen.test.model.PersonL;
import com.dimotim.lensjava.codegen.test.model.inner_package.Address;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;


public class CodegenTest {
    @Test
    public void makeLensTest(){
        String name = "Kate";
        Person p = new Person(name, 22);

        String r = PersonL.name.view(p);

        Assert.assertEquals(name, r);
    }

    @Test
    public void makeLensPackageTest(){
        Function<City, Address> f = CityL.address::view;
        Function<City, com.dimotim.lensjava.codegen.test.model.Object> ff = CityL.object::view;
    }
}

