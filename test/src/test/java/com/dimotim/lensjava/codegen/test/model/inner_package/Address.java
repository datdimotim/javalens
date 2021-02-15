package com.dimotim.lensjava.codegen.test.model.inner_package;

import com.dimotim.lensjava.codegen.MakeLens;
import lombok.Value;
import lombok.With;

@With
@MakeLens
@Value
public class Address {
    String location;
}
