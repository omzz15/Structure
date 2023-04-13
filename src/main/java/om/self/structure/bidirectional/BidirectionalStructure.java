package om.self.structure.bidirectional;

import om.self.structure.child.ChildStructure;
import om.self.structure.parent.ParentStructure;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BidirectionalStructure<PARENT, CHILD> implements ChildStructure<CHILD>, ParentStructure<PARENT> {
//    private FailureAction parentAttachFailureAction = FailureAction.IGNORE;
//    private FailureAction parentDetachFailureAction = FailureAction.IGNORE;
//    private FailureAction childAttachFailureAction = FailureAction.IGNORE;
//    private FailureAction childDetachFailureAction = FailureAction.IGNORE;
    
    private PARENT parent;
    private final Set<CHILD> children = new HashSet<>();

    @Override
    public void attachChild(CHILD child) {
        if(!children.add(child)) return;


        try{
            if(child instanceof ParentStructure<?>)((ParentStructure)child).attachParent(this);
        }
        catch(Exception e){

        }
        onChildAttach(child);
    }

    @Override
    public void detachChild(CHILD child) {
        if(!children.remove(child)) return;

        if(child instanceof ParentStructure<?>)((ParentStructure<?>)child).detachParent();
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
        if(parent instanceof ChildStructure<?>) ((ChildStructure)parent).attachChild(this);
        onParentAttach(parent);
    }

    @Override
    public void detachParent() {
        if(!isParentAttached()) return;

        PARENT parent = this.parent;
        this.parent = null;
        if(parent instanceof ChildStructure<?>) ((ChildStructure)parent).detachChild(this);
        onParentDetach(parent);
    }

    @Override
    public PARENT getParent() {
        return parent;
    }

//    public enum FailureAction{
//        THROW_EXCEPTION,
//        IGNORE
//    }
}