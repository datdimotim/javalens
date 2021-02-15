package com.dimotim.lensjava.codegen.test;

import com.dimotim.lensjava.codegen.MakeLens;
import com.dimotim.lensjava.codegen.test.model.Person;
import lombok.Value;
import lombok.With;

import java.util.List;

@Value
@MakeLens
@With
public class School {
    String name;
    List<Person> persons;
}
