package examples;

import om.self.structure.parent.ParentStructureImpl;

public class SimpleParent {
    public static void main(String[] args) {
        Child child = new Child();
        String parent1 = "hello";
        String parent2 = "world";

        //This will return false because no parent is attached
        System.out.println(child.isParentAttached());

        //Attaches parent 'hello' to child
        child.attachParent(parent1);

        //Detaches parent 'hello' from child
        //Then attaches parent 'world' to child
        child.attachParent(parent2);

        //This will return 'world' because that is the current parent
        System.out.println(child.getParent());

        //The final output will be:
        //false
        //Attaching parent: hello
        //Detaching parent: hello
        //Attaching parent: world
        //world
    }
}

class Child extends ParentStructureImpl<String> {
    @Override
    public void onParentAttach(String parent) {
        System.out.println("Attaching parent: " + parent);
    }

    @Override
    public void onParentDetach(String parent) {
        System.out.println("Detaching parent: " + parent);
    }
}
