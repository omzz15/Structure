package om.self.structure.bidirectional;

import om.self.structure.child.ChildStructure;
import om.self.structure.child.KeyedChildStructure;
import om.self.structure.parent.KeyedParentStructure;
import om.self.structure.parent.ParentContainer;
import om.self.structure.parent.ParentStructure;

import java.util.Hashtable;
import java.util.Map;

import static om.self.structure.Utils.tryFunction;

/**
 * An advanced implementation of both {@link KeyedChildStructure} and {@link KeyedParentStructure} that allows for bidirectional relationships where children and parents can be automatically attached and detached when the structure changes.
 * @param <K> the type of the key (unique identifier)
 * @param <PARENT> The type of the parent
 * @param <CHILD> The type of the child
 */
public class KeyedBidirectionalStructure<K, PARENT, CHILD> implements KeyedChildStructure<K, CHILD>, KeyedParentStructure<K, PARENT> {
    /**
     * Thy key associated with the attached parent
     */
    private K parentKey;
    private PARENT parent;
    private final Hashtable<K, CHILD> children = new Hashtable<>();

    /**
     * Creates a keyed bidirectional node without a parent or children
     */
    public KeyedBidirectionalStructure() {
    }

    /**
     * If the child is not already attached, it attaches the child then attaches itself as a parent to the child using the key parameter as the key if it's the right type.
     * @param key the key associated with the child
     * @param child the child being attached
     *
     * @see #attachChild(Object, Object, Object)
     */
    @Override
    public void attachChild(K key, CHILD child){
        attachChild(key, child, null);
    }

    /**
     * If the child is not already attached, it attaches the child then attaches itself as a parent to the child if it's the right type.
     * @param key the key associated with the child (used to identify the child)
     * @param child the child being attached
     * @param customParentKey if not null, it will try to use this parameter as the key when this object attaches itself as the parent to the child else it will just use the key parameter
     */
    public void attachChild(K key, CHILD child, K customParentKey) {
        if(key == null) throw new IllegalArgumentException("the key argument can not be null!");
        if(children.put(key, child) == child) return;

        if(child instanceof KeyedBidirectionalStructure) {
            KeyedBidirectionalStructure structure = (KeyedBidirectionalStructure) child;
            tryFunction(
                    () -> structure.attachParent(
                            customParentKey == null ? key : customParentKey,
                            this,
                            key
                    )
            );
        }
        else if(child instanceof KeyedParentStructure) {
            KeyedParentStructure structure = (KeyedParentStructure) child;
            tryFunction(
                    () -> structure.attachParent(
                            customParentKey == null ? key : customParentKey,
                            this
                    )
            );
        }
        else if(child instanceof ParentStructure) {
            ParentStructure structure = (ParentStructure) child;
            tryFunction(() -> structure.attachParent(this));
        }
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
        if(child instanceof ParentContainer<?>) {
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
     * Detaches the previous parent, then attaches the new parent and itself as a child to the parent using the key if it is the right type.
     * @param key the key associated with the parent
     * @param parent the parent being attached
     *
     * @see #attachParent(Object, Object, Object)
     */
    @Override
    public void attachParent(K key, PARENT parent) {
        attachParent(key, parent, null);
    }

    /**
     * Detaches the previous parent, then attaches the new parent and itself as a child to the parent if it is the right type.
     * @param key the key associated with the parent
     * @param parent the parent being attached
     * @param customChildName if not null, it will try to use this parameter to attach this object as a child to the parent else it will just use the key parameter
     *
     * @implNote if you use a customChildKey, you will need to pass in the same key when detaching the parent in order to detach this object from the parents children
     */
    public void attachParent(K key, PARENT parent, K customChildName) {
        if(key == null) throw new IllegalArgumentException("the key argument can not be null!");
        if(key == parentKey && parent == this.parent) return;

        detachParent();
        if(isParentAttached()) return; //in case a check prevents parent from being detached

        this.parentKey = key;
        this.parent = parent;

        if(parent instanceof KeyedBidirectionalStructure) {
            KeyedBidirectionalStructure structure = (KeyedBidirectionalStructure) parent;
            tryFunction(
                    () -> structure.attachChild(
                            customChildName == null ? key : customChildName,
                            this,
                            key
                    )
            );
        }
        else if(parent instanceof KeyedChildStructure) {
            KeyedChildStructure structure = (KeyedChildStructure) parent;
            tryFunction(
                    () -> structure.attachChild(
                            customChildName == null ? key : customChildName,
                            this
                    )
            );
        }
        else if(parent instanceof ChildStructure) {
            ChildStructure structure = (ChildStructure) parent;
            tryFunction(() -> structure.attachChild(this));
        }
        onParentAttach(key, parent);
    }

    /**
     * Detaches the parent then detaches itself from the parents children if it is the right type.
     * @param customChildName if not null, it will try to use this parameter to detach this object as a child from the parent else it will just use the {@link #parentKey} variable
     */
    public void detachParent(K customChildName) {
        if(!isParentAttached()) return;

        if(parent instanceof KeyedChildStructure) {
            KeyedChildStructure structure = (KeyedChildStructure) parent;
            tryFunction(() -> structure.detachChild(
                    customChildName == null ? parentKey : customChildName
            ));
        }else if(parent instanceof ChildStructure) {
            ChildStructure structure = (ChildStructure) parent;
            tryFunction(() -> structure.detachChild(this));
        }
        onParentDetach(parentKey, parent);
        parentKey = null;
        parent = null;
    }

    /**
     * Detaches the parent then detaches itself from the parents children if it is the right type using the {@link #parentKey}.
     *
     * @see #detachParent(Object)
     */
    @Override
    public void detachParent() {
        detachParent(null);
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
