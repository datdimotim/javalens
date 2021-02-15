package com.dimotim.lensjava.codegen.test;

import com.dimotim.lensjava.LensUtils;
import com.dimotim.lensjava.Traversal;
import com.dimotim.lensjava.codegen.test.model.House;
import com.dimotim.lensjava.codegen.test.model.HouseL;
import com.dimotim.lensjava.codegen.test.model.Person;
import com.dimotim.lensjava.codegen.test.model.PersonL;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NullPTest {
    @Test
    public void test(){
        Traversal<House, Integer> ageTraversal = HouseL.person
                .compose(LensUtils.nullP())
                .compose(PersonL.age);

        House emptyHouse = new House("desert", null);
        House house = new House("desert", new Person("c", 18));

        List<Integer> ages = ageTraversal.list(emptyHouse);
        Assert.assertEquals(0, ages.size());

        List<Integer> ages2 = ageTraversal.list(house);
        Assert.assertEquals(18, (int)ages2.get(0));

        House newEmpty = ageTraversal.map(emptyHouse, i->i+1);
        Assert.assertNull(newEmpty.getPerson());

        House newHouse = ageTraversal.map(house, i->i+1);
        Assert.assertEquals(19, (int)newHouse.getPerson().getAge());
    }
}

