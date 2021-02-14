package com.dimotim.lensjava;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Iso<A, B> extends Prism<A, B>, Lens<A, B>, Traversal<A, B> {
    B view(A a);

    A review(B b);

    static <A, B> Iso<A, B> of(Function<A, B> viewer, Function<B, A> reviewer) {
        return new Iso<A, B>() {
            @Override
            public B view(A a) {
                return viewer.apply(a);
            }

            @Override
            public A review(B b) {
                return reviewer.apply(b);
            }
        };
    }

    @Override
    default List<B> list(A a) {
        return ((Traversal<A, B>) this).list(a);
    }

    @Override
    default A map(A a, Function<B, B> mapper) {
        return ((Traversal<A, B>) this).map(a, mapper);
    }

    default <C> Iso<A, C> compose(Iso<B, C> inner) {
        return new Iso<A, C>() {

            @Override
            public C view(A a) {
                return inner.view(Iso.this.view(a));
            }

            @Override
            public A review(C c) {
                return Iso.this.review(inner.review(c));
            }
        };
    }

    @Override
    default A set(A a, B b) {
        return review(b);
    }

    @Override
    default Optional<B> cast(A a) {
        return Optional.of(view(a));
    }
}
