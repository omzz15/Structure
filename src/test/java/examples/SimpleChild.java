package examples;

import om.self.structure.child.ChildStructureImpl;

public class SimpleChild {

    public static void main(String[] args) {
        Parent parent = new Parent();

        String child1 = "hello";
        String child2 = "world";
        String child3 = "!!!";

        //will return an empty hashset
        System.out.println(parent.getChildren());
        //attaches child 1
        parent.attachChild(child1);
        //attaches child 2
        parent.attachChild(child2);
        //this will not do anything because child3 is not attached
        parent.detachChild(child3);
        //this will attach child3
        parent.attachChild(child3);
        //this will return the 'hello', 'world', and '!!!' children
        System.out.println(parent.getChildren());
        //this check uses the contains() method which relies on the equal() method
        //this will return true
        System.out.println(parent.isChildAttached("world"));
        //this will remove all children
        parent.detachChildren();

        //the output will be:
        //[]
        //Attaching child: hello
        //Attaching child: world
        //Attaching child: !!!
        //[!!!, world, hello]
        //true
        //Detaching child: !!!
        //Detaching child: world
        //Detaching child: hello
    }
}

class Parent extends ChildStructureImpl<String> {

    @Override
    public void onChildAttach(String child) {
        System.out.println("Attaching child: " + child);
    }

    @Override
    public void onChildDetach(String child) {
        System.out.println("Detaching child: " + child);
    }
}