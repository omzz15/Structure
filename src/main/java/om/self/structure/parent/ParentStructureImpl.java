package om.self.structure.parent;

/**
 * basic structure class that stores a parent of type T and has abstract methods for after a parent is attached and before a parent is detached
 * @param <T> the type of the parent object
 */
public class ParentStructureImpl<T> implements ParentStructure<T> {
    /**
     * The parent that the object extending this structure is attached to.
     */
    private T parent;

    /**
     * default constructor
     */
    public ParentStructureImpl(){}

    /**
     * constructor that automatically attaches the passed in parent
     * @param parent the parent you want to attach
     */
    public ParentStructureImpl(T parent){
        attachParent(parent);
    }

    /**
     * gets the currently attached parent
     * @return the {@link ParentStructureImpl#parent} variable
     */
    @Override
    public T getParent() {
        return parent;
    }

    /**
     * detaches the current parent if one is attached then attaches a parent and calls {@link ParentStructure#onParentAttach(Object)}
     * @param parent the parent of this structure
     */
    @Override
    public void attachParent(T parent) {
        if(this.parent == parent) return;

        detachParent();

        onParentAttach(parent);
        this.parent = parent;
    }

    /**
     * checks if a parent is attached then detaches it by calling {@link ParentStructure#onParentDetach(Object)} then setting {@link ParentStructureImpl#parent} to null
     */
    @Override
    public void detachParent(){
        if(!isParentAttached()) return;

        onParentDetach(parent);
        parent = null;
    }
}
