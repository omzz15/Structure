import om.self.structure.bidirectional.BidirectionalStructure;

public class Example {
    public static void main(String[] args) {
        Test t1 = new Test("1");
        Test t2 = new Test("2");
        Test t3 = new Test("3");

        System.out.println("1 to 2");
        t2.attachChild(t1);
        System.out.println("2 to 3");
        t2.attachParent(t3);
        t2.detachChild(t1);
        t2.detachParent();
    }
}

class Test extends BidirectionalStructure<Test, Test>{
    private String name;

    public Test(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onChildAttach(Test child) {
        System.out.println(child.getName() + " child attached to " + name);
    }

    @Override
    public void onChildDetach(Test child) {
        System.out.println(child.getName() + " child detached from " + name);
    }

    @Override
    public void onParentAttach(Test parent) {
        System.out.println(parent.getName() + " parent attached to " + name);
    }

    @Override
    public void onParentDetach(Test parent) {
        System.out.println(parent.getName() + " parent detached from " + name);
    }
}
