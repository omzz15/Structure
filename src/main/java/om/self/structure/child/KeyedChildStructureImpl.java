package om.self.structure.child;

import java.util.Hashtable;
import java.util.Map;

public class KeyedChildStructureImpl<K, V> implements KeyedChildStructure<K, V> {
    private final Hashtable<K, V> children = new Hashtable<>();

    @Override
    public void attachChild(K key, V child) {
        if(children.put(key, child) == child) return;

        onChildAttach(key, child);
    }

    @Override
    public void detachChild(K key) {
        if(!isChildKeyAttached(key)) return;

        onChildDetach(key, children.remove(key));
    }

    @Override
    public void detachChildren() {
        children.clear();
    }

    @Override
    public Map<K, V> getChildrenAndKeys() {
        return children;
    }
}
