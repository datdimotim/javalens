package com.dimotim.lensjava.codegen.test.model;

import com.dimotim.lensjava.codegen.MakeLens;
import lombok.Value;
import lombok.With;

@MakeLens
@With
@Value
public class PrimitiveTestEntity {
    boolean aBoolean;
    Boolean aBooleanBoxed;
    byte aByte;
    short aShort;
    char aChar;
    int anInt;
    long aLong;
    float aFloat;
    double aDouble;
}
