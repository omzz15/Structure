package om.self.structure.parent;

//v1 complete

/**
 * An implementation of {@link ParentStructure}
 * @param <T> the type of the parent
 * @see ParentStructure
 */
public class ParentStructureImpl<T> implements ParentStructure<T> {
    /**
     * The parent that the object extending this structure is attached to.
     */
    private T parent;

    /**
     * Default constructor
     */
    public ParentStructureImpl(){}

    /**
     * Constructor that automatically attaches a parent
     * @param parent the parent being attached
     */
    public ParentStructureImpl(T parent){
        attachParent(parent);
    }

    /**
     * Gets the currently attached parent
     * @return the attached parent, null if no parent is attached
     */
    @Override
    public T getParent() {
        return parent;
    }

    /**
     * Detaches the current parent if one is attached then attaches a parent.
     * @param parent the parent being attached
     */
    @Override
    public void attachParent(T parent) {
        if(this.parent == parent) return;

        detachParent();

        onParentAttach(parent);
        this.parent = parent;
    }

    /**
     * Detached the parent if one is attached.
     */
    @Override
    public void detachParent(){
        if(!isParentAttached()) return;

        onParentDetach(parent);
        parent = null;
    }
}
