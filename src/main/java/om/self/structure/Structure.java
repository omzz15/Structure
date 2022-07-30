package om.self.structure;

/**
 * basic structure class that stores a parent of type T and has abstract methods for after a parent is attached and before a parent is detached
 * @param <T> the type of the parent object
 */
public abstract class Structure<T> {
    /**
     * The parent that the object extending this structure is attached to.
     */
    private T parent;

    /**
     * 1
     */
    public Structure(){}

    /**
     * gets the currently attached parent
     * @return the {@link Structure#parent} variable
     */
    public T getParent() {
        return parent;
    }

    /**
     * checks whether there is a parent attached(basically if {@link Structure#parent} is null)
     * @return if a parent is attached
     */
    public boolean isParentAttached(){
        return parent != null;
    }

    /**
     * detaches the current parent if one is attached then attaches a parent by setting {@link Structure#parent} and calling {@link Structure#onAttached()}
     * @param parent the parent of this structure
     */
    public void attachParent(T parent) {
        if(isParentAttached())detachParent();
        this.parent = parent;
        onAttached();
    }

    /**
     * checks if a parent is attached then detaches it by calling {@link Structure#onDetach()} then setting {@link Structure#parent} to null
     */
    public void detachParent(){
        if(!isParentAttached()) return;
        onDetach();
        parent = null;
    }

    /**
     * Abstract method that is called after a parent is attached or changed.
     * @implNote this method is called every time {@link Structure#attachParent(T)} is called even if the parent does not change.
     */
    protected abstract void onAttached();

    /**
     * Abstract method that is called before a parent is detached.
     * @implNote this method is called when {@link Structure#detachParent()} is called, but only if parent is not already null so {@link Structure#getParent()} will never be null inside this method.
     */
    protected abstract void onDetach();
}
