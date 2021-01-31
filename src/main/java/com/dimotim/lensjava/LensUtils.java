package com.dimotim.lensjava;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
}
