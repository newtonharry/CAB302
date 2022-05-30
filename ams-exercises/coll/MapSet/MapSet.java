package coll.MapSet;

import java.util.*;
import java.util.stream.Collectors;

public class MapSet<K,V> extends AbstractMap<K, HashSet<V>> implements Iterable<V> {


    private Map<K,HashSet<V>> map;


    public MapSet(){
        map = new HashMap<K, HashSet<V>>();
    }


    public void addValue(K a, V b){
        if(map.containsKey(a)){
           map.get(a).add(b);
        }else{
            HashSet<V> set = new HashSet<>();
            set.add(b);
            map.put(a,set);
        }
    }


    @Override
    public Set<Entry<K, HashSet<V>>> entrySet(){
        return map.entrySet();
    }


    @Override
    public Iterator<V> iterator(){
        return this.values().stream().sorted((it1,it2) -> Integer.compare(it2.toArray().length,it1.toArray().length)).
                flatMap(Collection::stream).collect(Collectors.toList()).iterator();
    }
}
