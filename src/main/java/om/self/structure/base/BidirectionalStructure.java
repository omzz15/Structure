package om.self.structure.base;

import om.self.structure.base.parent.ParentStructure;
import om.self.structure.base.child.ChildStructure;

import java.util.*;

public abstract class BidirectionalStructure<PARENT extends ChildStructure, CHILD extends ParentStructure> implements ChildStructure<CHILD>, ParentStructure<PARENT> {
    private PARENT parent;
    private final Set<CHILD> children = new HashSet<>();

    @Override
    public void attachChild(CHILD child) {
        if(!children.add(child)) return;

        child.attachParent(this);
        onChildAttach(child);
    }

    @Override
    public void detachChild(CHILD child) {
        if(!children.remove(child)) return;

        child.detachParent();
        onChildDetach(child);
    }

    @Override
    public void detachChildren() {
        for (CHILD c: children)
            detachChild(c);
    }

    @Override
    public Collection<CHILD> getChildren() {
        return children;
    }

    @Override
    public void attachParent(PARENT parent) {
        if(this.parent == parent) return;

        if(isParentAttached()) detachParent();
        this.parent = parent;
        parent.attachChild(this);
        onParentAttach(parent);
    }

    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        PARENT parent = this.parent;
        this.parent = null;
        parent.detachChild(this);
        onParentDetach(parent);
    }

    @Override
    public PARENT getParent() {
        return parent;
    }
}
