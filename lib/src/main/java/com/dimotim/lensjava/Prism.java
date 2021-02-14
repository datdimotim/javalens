package com.dimotim.lensjava;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Prism<A, B> extends Traversal<A, B> {
    Optional<B> cast(A a);

    A review(B b);

    static <A, B> Prism<A, B> of(Function<A, Optional<B>> caster, Function<B, A> reviewer) {
        return new Prism<A, B>() {
            @Override
            public Optional<B> cast(A a) {
                return caster.apply(a);
            }

            @Override
            public A review(B b) {
                return reviewer.apply(b);
            }
        };
    }

    default <C> Prism<A, C> compose(Prism<B, C> inner) {
        return new Prism<A, C>() {
            @Override
            public Optional<C> cast(A a) {
                return Prism.this.cast(a).flatMap(inner::cast);
            }

            @Override
            public A review(C c) {
                return Prism.this.review(inner.review(c));
            }
        };
    }

    @Override
    default List<B> list(A a) {
        return cast(a).map(Arrays::asList).orElse(Collections.emptyList());
    }

    @Override
    default A map(A a, Function<B, B> mapper) {
        return cast(a).map(mapper).map(this::review).orElse(a);
    }
}
