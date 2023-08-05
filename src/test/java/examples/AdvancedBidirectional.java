package examples;

//This is a more advanced example of a bidirectional structure that include some extra functionality

import om.self.structure.NamedStructure;
import om.self.structure.Utils;
import om.self.structure.bidirectional.KeyedBidirectionalStructureWithChecks;
import om.self.structure.parent.KeyedParentStructureImpl;

public class AdvancedBidirectional {
    public static void main(String[] args) {

        Folder root = new Folder("root");
        Folder usr = new Folder("usr", root);
        Folder bin = new Folder("bin", root);
        Folder home = new Folder("home", root);
        Folder user = new Folder("user", home);
        Folder test = new Folder("test", user);
        File hello = new File("hello.txt", test, "Hello World!");
        File world = new File("world.crt", test, "Hello World! 2");

        //this will print the folder tree
        System.out.println("Folder Tree Old:");
        System.out.println(root.getTree());

        //we can try to add a file with the same name as another file, but it won't work
        File hello2 = new File("hello.txt", test, "Ha! I replaced your file :)");
        System.out.println("hello.txt content:");
        System.out.println(((File) test.getChild("hello.txt")).getContent()); //this will give us the original file contents

        System.out.println("\nRemoving user");
        //we need to supply the key we used ("usr") because when attaching this object as a child to the root we used a custom key so without it, it won't detach itself from root
        user.detachParent("user");

        //this will print the folder tree
        System.out.println("Folder Tree New:");
        System.out.println(root.getTree());

        //Output:
        //Folder Tree Old:
        //root
        //|  bin
        //|  home
        //|  |  user
        //|  |  |  test
        //|  |  |  |  hello.txt
        //|  |  |  |  world.crt
        //|  usr
        //
        //hello.txt content:
        //Hello World!
        //
        //Removing user
        //Folder Tree New:
        //root
        //|  bin
        //|  home
        //|  usr
    }
}

//This is a simple interface that represents a file system object so our structure can use only files and folders
interface FileSystemObject extends NamedStructure<String>{
}

//This is a folder class that extends the bidirectional structure and implements the file system object interface
//This has a couple of checks made in the constructor that ensure everything works as expected
class Folder extends KeyedBidirectionalStructureWithChecks<String, Folder, FileSystemObject> implements FileSystemObject {
    private final String name;

    public Folder(String name) {
        this.name = name;

        //This just adds simple checks to ensure each file has a unique name and the key is the same as the name
        addChildCheck((key, child, action) -> {
            if(action.equals(Utils.Action.DETACH)) //let all files detach
                return true;

            if(!key.equals(child.getName())) //make sure the key is the same as the name
                return false;

            if(isChildKeyAttached(key)) //make sure the key is unique
                return false;

            return true;
        });
    }

    public Folder(String name, Folder parent) {
        this(name);
        //we use the useNameForChild parameter to ensure the key used to attach this to the parent is this folders name
        //that ensures it can pass the unique key check defined in the constructor
        attachParent(parent.getName(), parent, name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Cannot change the name of a folder");
    }

    /**
     * this will go through each parent to the root and get the directory path
     * @return the path to current folder
     */
    public String getDir(){
        if(isParentAttached()){
            return getParent().getDir() + "/" + getName();
        }else{
            return getName();
        }
    }

    /**
     * this will recursively get all children of this folder (folders and files) and print them in a tree structure for visualization
     * @return the tree structure of this folder
     */
    public String getTree(){
        return getTree("|  ", 0).toString();
    }

    private StringBuilder getTree(String tab, int tabs){
        StringBuilder tree = new StringBuilder(tab.repeat(tabs) + getName() + "\n");

        for(FileSystemObject child : getChildren()){
            if(child instanceof Folder folder){
                tree.append(folder.getTree(tab, tabs + 1));
            }else if(child instanceof File file){
                tree.append(tab.repeat(tabs + 1)).append(file.getName()).append("\n");
            }
        }

        return tree;
    }
}

//This is a file class that extends the parent structure and implements the file system object interface
//This only needs to extend the parent structure because files can only be attached to folders and can not have children
class File extends KeyedParentStructureImpl<String, Folder> implements FileSystemObject {
    private final String name;

    private String content;

    public File(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public File(String name, Folder parent, String content) {
        this(name, content);
        //this must call the parent attachChild function because the attachParent function from File would not add the File to the Folder's children as it is only a parent structure and not a bidirectional structure
        parent.attachChild(getName(), this, parent.getName());
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Cannot change the name of a file");
    }

    public String getDir(){
        if(isParentAttached()){
            return getParent().getDir() + "/" + getName();
        }else{
            return getName();
        }
    }
}