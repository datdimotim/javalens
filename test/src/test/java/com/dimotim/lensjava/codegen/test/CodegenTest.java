package com.dimotim.lensjava.codegen.test;


import com.dimotim.lensjava.LensUtils;
import com.dimotim.lensjava.codegen.test.model.*;
import com.dimotim.lensjava.codegen.test.model.inner_package.Address;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
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

    @Test
    public void makeLensPrimitiveTest(){
        Function<PrimitiveTestEntity, Boolean> f1 = PrimitiveTestEntityL.aBoolean::view;
        Function<PrimitiveTestEntity, Byte> f2 = PrimitiveTestEntityL.aByte::view;
        Function<PrimitiveTestEntity, Short> f3 = PrimitiveTestEntityL.aShort::view;
        Function<PrimitiveTestEntity, Character> f4 = PrimitiveTestEntityL.aChar::view;
        Function<PrimitiveTestEntity, Integer> f5 = PrimitiveTestEntityL.anInt::view;
        Function<PrimitiveTestEntity, Long> f6 = PrimitiveTestEntityL.aLong::view;
        Function<PrimitiveTestEntity, Float> f7 = PrimitiveTestEntityL.aFloat::view;
        Function<PrimitiveTestEntity, Double> f8 = PrimitiveTestEntityL.aDouble::view;
    }

    @Test
    public void makeLensBoolGetterTest(){
        Function<PrimitiveTestEntity, Boolean> f = PrimitiveTestEntityL.aBoolean::view;
        Function<PrimitiveTestEntity, Boolean> f2 = PrimitiveTestEntityL.aBooleanBoxed::view;
    }

    @Test
    public void makeLensGenericTest(){
        Function<School, List<Integer>> ages = SchoolL.persons
                .compose(LensUtils.listTraversal())
                .compose(PersonL.age)::list;
    }
}

