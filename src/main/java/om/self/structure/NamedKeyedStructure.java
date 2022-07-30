package om.self.structure;

import org.apache.commons.lang3.NotImplementedException;

/**
 * Similar to {@link KeyedStructure} but storing a name value that could be used as {@link KeyedStructure#parentKey} if the types {@link NAME} and {@link K} match.
 * @param <NAME> The type of the name used to identify itself
 * @param <K> The type of the key used to identify itself to its parent
 * @param <V> The type of the parent object
 */
public abstract class NamedKeyedStructure<NAME, K, V> extends KeyedStructure<K, V>{
    /**
     * The name of itself(may also be used as the parent key).
     */
    NAME name;

    /**
     * 1
     */
    public NamedKeyedStructure(){}

    /**
     * Sets the name of itself
     * @param name the name you want to give to this
     */
    public void setName(NAME name) {
        this.name = name;
    }

    /**
     * gets the current name
     * @return {@link NamedKeyedStructure#name}
     */
    public NAME getName() {
        return name;
    }

    /**
     * Attaches a parent using {@link KeyedStructure#attachParent(Object, Object)} if {@link NamedKeyedStructure#name} is not null and type {@link NAME} is castable to type {@link K}
     * @param parent the parent of this structure
     */
    @Override
    public void attachParent(V parent) {
        if(name == null) return;
        try {
            super.attachParent((K)name, parent);
        }catch (Exception e){
            throw new NotImplementedException();
        }
    }
}
