package om.self.structure;

/**
 * A more advanced structure class that keeps all the functionality of {@link Structure} but requires a key for identification purposes like HashTable lookups
 * @param <K> The type of the key used to identify itself to its parent
 * @param <V> The type of the parent object(passed into Structure).
 */
public abstract class KeyedStructure <K, V> extends Structure<V>{
    /**
     * The key used to identify itself to its parent
     */
    private K parentKey;

    /**
     * 1
     */
    public KeyedStructure(){}

    /**
     * DO NOT USE. A parent key is REQUIRED when using the KeyedStructure class
     * @implNote use {@link KeyedStructure#attachParent(K, V)} instead
     * @param parent the parent of itself
     */
    @Override
    public void attachParent(V parent) {
        throw new UnsupportedOperationException("Keyed Structures require both the parent and parent key to be set! Use attachParent(K parentKey, V parent) instead");
    }

    /**
     * Detaches current {@link Structure#parent} and {@link KeyedStructure#parentKey} if attached then attaches the new parentKey and calls {@link Structure#attachParent(Object)} to attach parent
     * @param parentKey the key used to identify itself to its parent
     * @param parent the parent of itself
     */
    public void attachParent(K parentKey, V parent){
        if(isParentAttached())detachParent();
        this.parentKey = parentKey;
        super.attachParent(parent);
    }

    /**
     * Detaches current parent and parent key by calling {@link Structure#detachParent()} then setting {@link  KeyedStructure#parentKey} to null
     */
    @Override
    public void detachParent(){
        super.detachParent();
        this.parentKey = null;
    }

    /**
     * Gets the current attached parent key. If no parent is attached then it will return null
     * @return {@link KeyedStructure#parentKey}
     */
    public K getParentKey() {
        return parentKey;
    }
}
