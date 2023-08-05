package om.self.structure.bidirectional;

import om.self.structure.child.ChildStructure;
import om.self.structure.parent.ParentContainer;
import om.self.structure.parent.ParentStructure;
import static om.self.structure.Utils.tryFunction;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An advanced implementation of both {@link ChildStructure} and {@link ParentStructure} that allows for bidirectional relationships where children and parents can be automatically attached and detached when the structure changes.
 * @param <PARENT> The type of the parent
 * @param <CHILD> The type of the child
 */
public class BidirectionalStructure<PARENT, CHILD> implements ChildStructure<CHILD>, ParentStructure<PARENT> {
    
    private PARENT parent;
    private final Set<CHILD> children = new HashSet<>();

    /**
     * Default constructor that creates a bidirectional node without children or a parent
     */
    public BidirectionalStructure() {
    }

    /**
     * Creates a bidirectional node with the specified parent attached
     * @param parent the parent of this node
     */
    public BidirectionalStructure(PARENT parent) {
        attachParent(parent);
    }

    /**
     * Creates a bidirectional node with the specified children attached
     * @param children the children to attach to this node
     */
    public BidirectionalStructure(Iterable<CHILD> children) {
        attachChildren(children);
    }

    /**
     * If the child is not already attached, it attaches the child then attaches itself as a parent to the child if it's the right type.
     * @param child The child being attached
     */
    @Override
    public void attachChild(CHILD child) {
        if(!children.add(child)) return;

        if(child instanceof ParentStructure)
            tryFunction(() -> ((ParentStructure) child).attachParent(this));

        onChildAttach(child);
    }

    /**
     * If the child is already attached, it detached the child then detaches itself as a parent from the child if it's the right type.
     * @param child The child being detached
     */
    @Override
    public void detachChild(CHILD child) {
        if(!children.remove(child)) return;

        if(child instanceof ParentContainer<?>) {
            ParentContainer<?> container = (ParentContainer<?>) child;
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

        detachParent();
        if(isParentAttached()) return; //in case the parent didn't detach itself

        this.parent = parent;

        if(parent instanceof ChildStructure)
            tryFunction(() -> ((ChildStructure)parent).attachChild(this));

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
        if(parent instanceof ChildStructure)
            tryFunction(() -> ((ChildStructure)parent).detachChild(this));

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
}