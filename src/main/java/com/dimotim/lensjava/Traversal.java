package com.dimotim.lensjava;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Traversal<A, B> {
    List<B> list(A a);

    A map(A a, Function<B, B> mapper);

    static <A, B> Traversal<A, B> of(Function<A, List<B>> lister, BiFunction<A, Function<B, B>, A> mapper) {
        return new Traversal<A, B>() {
            @Override
            public List<B> list(A a) {
                return lister.apply(a);
            }

            @Override
            public A map(A a, Function<B, B> m) {
                return mapper.apply(a, m);
            }
        };
    }

    default <C> Traversal<A, C> compose(Traversal<B, C> inner) {
        return new Traversal<A, C>() {
            @Override
            public List<C> list(A a) {
                return Traversal.this.list(a)
                        .stream()
                        .flatMap(b -> inner.list(b).stream())
                        .collect(Collectors.toList());
            }

            @Override
            public A map(A a, Function<C, C> mapper) {
                return Traversal.this.map(a, b -> inner.map(b, mapper));
            }
        };
    }
}
