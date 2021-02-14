package com.dimotim.lensjava;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class LensUtils{
    public static <K,V> Lens<Map<K,V>,V> mapL(K key){
        return Lens.of(
                m->m.get(key),
                (m,v)->{
                    Map<K,V> copy=new HashMap<>(m);
                    if(v!=null)copy.put(key,v);
                    else copy.remove(key);
                    return copy;
                }
        );
    }

    public static <K> Lens<Set<K>,Boolean> setL(K key){
        return Lens.of(
                s->s.contains(key),
                (s,b)->{
                    Set<K> copy=new HashSet<>(s);
                    if(b){
                        copy.add(key);
                    }else {
                        copy.remove(key);
                    }
                    return copy;
                }
        );
    }

    public static <T> Traversal<List<T>, T> listTraversal(){
        return Traversal.of(
                Function.identity(),
                (l, f) -> l.stream().map(f).collect(Collectors.toList())
        );
    }
}

