package om.self.structure.bidirectional;

import om.self.structure.child.ChildStructure;
import om.self.structure.child.KeyedChildStructure;
import om.self.structure.parent.KeyedParentStructure;
import om.self.structure.parent.ParentContainer;
import om.self.structure.parent.ParentStructure;

import java.util.Hashtable;
import java.util.Map;

//v1 complete

/**
 * An advanced implementation of both {@link KeyedChildStructure} and {@link KeyedParentStructure} that allows for bidirectional relationships where children and parents can be automatically attached and detached when the structure changes.
 * @param <K> the type of the key (unique identifier)
 * @param <PARENT> The type of the parent
 * @param <CHILD> The type of the child
 */
public class KeyedBidirectionalStructure<K, PARENT, CHILD> implements KeyedChildStructure<K, CHILD>, KeyedParentStructure<K, PARENT> {
    private K parentKey;
    private PARENT parent;
    private final Hashtable<K, CHILD> children = new Hashtable<>();

    /**
     * If the child is not already attached, it attaches the child then attaches itself as a parent to the child if it's the right type.
     * @param key the key associated with the child
     * @param child the child being attached
     */
    @Override
    public void attachChild(K key, CHILD child) {
        if(key == null) throw new IllegalArgumentException("the key argument can not be null!");
        if(children.put(key, child) == child) return;

        if(child instanceof KeyedParentStructure) ((KeyedParentStructure) child).attachParent(key, this);
        if(child instanceof ParentStructure) ((ParentStructure) child).attachParent(this);

        onChildAttach(key, child);
    }

    /**
     * If the child is already attached, it detached the child then detaches itself as a parent from the child if it's the right type.
     * @param key the key of the child being detached
     */
    @Override
    public void detachChild(K key) {
        if(!isChildKeyAttached(key)) return;

        CHILD child = children.remove(key);
        if(child instanceof ParentContainer) {
            ParentContainer<?> container = (ParentContainer<?>) child;
            if (container.getParent() == this)
                container.detachParent();
        }

        onChildDetach(key, child);
    }

    /**
     * Gets the attached children along with their keys.
     * @return The children and their keys
     */
    @Override
    public Map<K, CHILD> getChildrenAndKeys() {
        return children;
    }

    /**
     * Detaches the previous parent, then attaches the new parent and itself as a child to the parent if it is the right type.
     * @param key the key associated with the parent
     * @param parent the parent being attached
     */
    @Override
    public void attachParent(K key, PARENT parent) {
        if(key == null) throw new IllegalArgumentException("the key argument can not be null!");
        if(key == parentKey && parent == this.parent) return;

        if(isParentAttached()) detachParent();

        this.parentKey = key;
        this.parent = parent;

        if(parent instanceof KeyedChildStructure) ((KeyedChildStructure) parent).attachChild(key,this);
        if(parent instanceof ChildStructure) ((ChildStructure) parent).attachChild(this);

        onParentAttach(key, parent);
    }

    /**
     * Detaches the parent then detaches itself from the parents children if it is the right type.
     */
    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        if(parent instanceof KeyedChildStructure) ((KeyedChildStructure) parent).detachChild(parentKey);
        if(parent instanceof ChildStructure) ((ChildStructure) parent).detachChild(this);

        onParentDetach(parentKey, parent);
        parentKey = null;
        parent = null;
    }

    /**
     * Gets the attached parent.
     * @return the parent
     */
    @Override
    public PARENT getParent() {
        return parent;
    }

    /**
     * Gets the key of the attached parent.
     * @return key of the parent
     */
    @Override
    public K getParentKey() {
        return parentKey;
    }
}
