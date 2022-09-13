package om.self.structure.bidirectional;

import om.self.structure.child.KeyedChildStructure;
import om.self.structure.parent.KeyedParentStructure;

import java.util.Hashtable;
import java.util.Map;

public class KeyedBidirectionalStructure<K, PARENT, CHILD> implements KeyedChildStructure<K, CHILD>, KeyedParentStructure<K, PARENT> {
    private K parentKey;
    private PARENT parent;
    private final Hashtable<K, CHILD> children = new Hashtable<>();

    @Override
    public void attachChild(K key, CHILD child) {
        if(children.put(key, child) == child) return;

        if(child instanceof KeyedParentStructure<?,?>)((KeyedParentStructure)child).attachParent(key, this);
        onChildAttach(key, child);
    }

    @Override
    public void detachChild(K key) {
        if(!isChildKeyAttached(key)) return;

        CHILD child = children.remove(key);
        if(child instanceof KeyedParentStructure<?,?>)((KeyedParentStructure)child).detachParent();
        onChildDetach(key, child);
    }

    @Override
    public void detachChildren() {
        for (K key: children.keySet())
            detachChild(key);
    }

    @Override
    public Map<K, CHILD> getChildrenAndKeys() {
        return children;
    }

    @Override
    public void attachParent(K key, PARENT parent) {
        if(isParentAttached()) detachParent();

        if(parent instanceof KeyedChildStructure<?,?>)((KeyedChildStructure)parent).attachChild(key,this);
        onParentAttach(key, parent);
        this.parentKey = key;
        this.parent = parent;
    }

    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        if(parent instanceof KeyedChildStructure<?,?>)((KeyedChildStructure)parent).detachChild(parentKey);
        onParentDetach(parentKey, parent);
        parentKey = null;
        parent = null;
    }

    @Override
    public PARENT getParent() {
        return parent;
    }

    @Override
    public K getParentKey() {
        return parentKey;
    }
}
