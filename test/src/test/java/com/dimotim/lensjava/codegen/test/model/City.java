package com.dimotim.lensjava.codegen.test.model;

import com.dimotim.lensjava.codegen.MakeLens;
import com.dimotim.lensjava.codegen.test.model.inner_package.Address;
import lombok.Value;
import lombok.With;

@MakeLens
@With
@Value
public class City {
    Address address;
    Object object;
}
