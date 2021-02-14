package com.dimotim.lensjava;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;


public interface Lens<A,B> extends Traversal<A,B>{
    B view(A a);
    A set(A a, B b);

    static <A,B> Lens<A,B> of(Function<A, B> viewer, BiFunction<A,B,A> setter){
        return new Lens<A, B>() {
            @Override
            public B view(A a) {
                return viewer.apply(a);
            }

            @Override
            public A set(A a, B b) {
                return setter.apply(a, b);
            }
        };
    }

    default <C>Lens<A,C> compose(Lens<B,C> inner){
        return new Lens<A, C>() {
            @Override
            public C view(A a) {
                return inner.view(Lens.this.view(a));
            }

            @Override
            public A set(A a, C c) {
                return Lens.this.set(a, inner.set(Lens.this.view(a),c));
            }
        };
    }

    @Override
    default List<B> list(A a){
        return Collections.singletonList(view(a));
    }

    @Override
    default A map(A a, Function<B, B> mapper){
        return set(a, mapper.apply(view(a)));
    }
}


