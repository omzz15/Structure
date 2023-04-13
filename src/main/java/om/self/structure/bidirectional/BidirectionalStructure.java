package om.self.structure.bidirectional;

import om.self.structure.child.ChildStructure;
import om.self.structure.parent.ParentContainer;
import om.self.structure.parent.ParentStructure;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//v1 complete

/**
 * An advanced implementation of both {@link ChildStructure} and {@link ParentStructure} that allows for bidirectional relationships where children and parents can be automatically attached and detached when the structure changes.
 * @param <PARENT> The type of the parent
 * @param <CHILD> The type of the child
 */
public class BidirectionalStructure<PARENT, CHILD> implements ChildStructure<CHILD>, ParentStructure<PARENT> {
//    private FailureAction parentAttachFailureAction = FailureAction.IGNORE;
//    private FailureAction parentDetachFailureAction = FailureAction.IGNORE;
//    private FailureAction childAttachFailureAction = FailureAction.IGNORE;
//    private FailureAction childDetachFailureAction = FailureAction.IGNORE;
    
    private PARENT parent;
    private final Set<CHILD> children = new HashSet<>();

    /**
     * If the child is not already attached, it attaches the child then attaches itself as a parent to the child if it's the right type.
     * @param child The child being attached
     */
    @Override
    public void attachChild(CHILD child) {
        if(!children.add(child)) return;

        try{
            if(child instanceof ParentStructure structure) structure.attachParent(this);
        }
        catch(Exception e){

        }
        onChildAttach(child);
    }

    /**
     * If the child is already attached, it detached the child then detaches itself as a parent from the child if it's the right type.
     * @param child The child being detached
     */
    @Override
    public void detachChild(CHILD child) {
        if(!children.remove(child)) return;

        if(child instanceof ParentContainer<?> container) {
            if (container.getParent() == this)
                container.detachParent();
        }

        onChildDetach(child);
    }

    /**
     * Gets all attached children.
     * @return attached children
     */
    @Override
    public Collection<CHILD> getChildren() {
        return children;
    }

    /**
     * Detaches the previous parent, then attaches the new parent and itself as a child to the parent if it is the right type.
     * @param parent the parent being attached
     */
    @Override
    public void attachParent(PARENT parent) {
        if(this.parent == parent) return;

        if(isParentAttached()) detachParent();
        this.parent = parent;
        if(parent instanceof ChildStructure structure) structure.attachChild(this);
        onParentAttach(parent);
    }

    /**
     * Detaches the parent then detaches itself from the parents children if it is the right type.
     */
    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        PARENT parent = this.parent;
        this.parent = null;
        if(parent instanceof ChildStructure structure) structure.detachChild(this);
        onParentDetach(parent);
    }

    /**
     * Gets the attached parent.
     * @return the parent
     */
    @Override
    public PARENT getParent() {
        return parent;
    }

//    public enum FailureAction{
//        THROW_EXCEPTION,
//        IGNORE
//    }
}