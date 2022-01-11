package Coinbase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DesignInMemoryFileSystem_588_Hard {

	public static void main(String[] args) {
		FileSystem fileSystem = new FileSystem();
		System.out.println(fileSystem.ls("/"));                         // return []
		fileSystem.mkdir("/a/b/c");
		fileSystem.addContentToFile("/a/b/c/d", "hello");
		System.out.println(fileSystem.ls("/"));                         // return ["a"]
		System.out.println(fileSystem.readContentFromFile("/a/b/c/d")); // return "hello"
	}

}

class FileSystem {

    FileNode root;
    
    public FileSystem() {
        root = new FileNode("/");    
    }
    
    public List<String> ls(String path) {
        List<String> res = new ArrayList<>();
        FileNode node = traverse(path);
        
        if(node.isFile) {
            res.add(node.name);
        }
        else {
            res.addAll(node.children.keySet());
        }
        
        return res;
    }
    
    public void mkdir(String path) {
        traverse(path);
    }
    
    public void addContentToFile(String filePath, String content) {
        FileNode node = traverse(filePath);
        node.isFile = true;
        node.content.append(content);
    }
    
    public String readContentFromFile(String filePath) {
        return traverse(filePath).content.toString();
    }
    
    // traverse to end file/dir in the given path
    private FileNode traverse(String path) {
        String[] arr = path.split("/");
        FileNode curr = root;
        
        //start from index 1 as 0th element is ""
        for(int i=1; i<arr.length; i++) {
            curr.children.putIfAbsent(arr[i], new FileNode(arr[i]));
            curr = curr.children.get(arr[i]);
        }
        return curr;
    }
    
    private class FileNode {
        String name;
        boolean isFile;
        StringBuilder content;
        Map<String, FileNode> children;
        
        public FileNode(String name) {
            this.name = name;
            this.content = new StringBuilder();
            children = new TreeMap<>(); // to maintain lexicographic order of files
        }
    }
}