package sfdc.file.demo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//--https://www.quora.com/What-is-the-Salesforce-2-hour-remote-programming-test-2nd-round-interview-like

public class FIleDemo {
	private static final String ROOT = "/Users/prash/Documents/MyWorkspace/InterviewPrep/root_sfdc";
	//private static final String ROOT = "/Users/prash/Documents/MyWorkspace/Salesforce/TEST/root";
	private static final String FILE_NAME = ROOT +"/prog.txt";  //TODO update the name to .dat
	private static final String OUTPUT_FILE_NAME = ROOT +"/out.txt";  //TODO update the name to .dat
	
	private String pwd = ROOT;
	
	public static void main(String[] args) {
			System.out.println("File Demo\n\n");
			
			FIleDemo fileDemo = new FIleDemo();
			
			List<String> cmdList = fileDemo.readFile();
			
			for(String cmd : cmdList) {
				
				if(cmd.contains("mkdir")) {
					String arg = (cmd.split(" "))[1];
					boolean isSuccess = fileDemo.mkdir(arg);
					
					fileDemo.writeFile("Commmand: mkdir	"+arg);
					if(!isSuccess) 
						fileDemo.writeFile("\nSubdirectory already exists");
					
					fileDemo.writeFile("\n");
						
				}
				if(cmd.equals("dir")) {
					fileDemo.writeFile("Commmand: dir\n");
					fileDemo.writeFile("Directory of "+fileDemo.getPwd()+":\n");
					String dirs = fileDemo.dir(fileDemo.getPwd());
					if(dirs.isEmpty()) {
						fileDemo.writeFile("No subdirectories");
					} else {
						fileDemo.writeFile(dirs);
					}
					fileDemo.writeFile("\n");
				}
				if(cmd.contains("cd")) {
					String arg = (cmd.split(" "))[1];
					fileDemo.cd(arg);
					fileDemo.writeFile("Commmand: cd	"+arg+"\n");
				}
				if(cmd.contains("up")) {
					fileDemo.writeFile("Commmand: up\n");
					fileDemo.up();
				}
			}
			
	}
	
	
	public void cd(String dirName) {
		setPwd(ROOT +"/"+dirName);
	}
	public void up() {
		String currentPath = pwd;

		if(ROOT.equals(currentPath))  //--can't go up 
			return;
		
		String newPath = currentPath.substring(0, currentPath.lastIndexOf("/"));
		setPwd(newPath);
	}

	public void writeFile(String content) {
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME, true))) {
			bw.append(content);
			
		} catch(IOException e) {
			
			System.err.println("Error writing file");
			e.printStackTrace();
		} finally {
			
		}
		
	}
	public  List<String> readFile() {
		
		List<String> cmdList = new ArrayList<String>();
		
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			
			fr = new FileReader(FILE_NAME);
			br = new BufferedReader(fr);
			
			String cmd;
			while((cmd = br.readLine()) != null) {
				System.out.println(cmd);
				cmdList.add(cmd);
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return cmdList;
	}
	
	
	public  String dir(String currDir) {
		StringBuilder dirs = new StringBuilder();
		
		FileFilter dirFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
		
		File dir = new File(currDir);
		File[] listofFiles = dir.listFiles(dirFilter);
		
		for(File fName : listofFiles) {
			dirs.append(fName.getName());
			dirs.append(" ");
			
			//System.out.println(fName.getName() + " ");//TODO remove
		}
		return dirs.toString();
	}
	
	public boolean mkdir(String dirName) {
		File dir = new File(getPwd() +"/"+ dirName);
		boolean isSuccess = dir.mkdirs();
		
		if(isSuccess) {
			System.out.println("Dir created");
		} else {
			System.err.println("Fail creating dir");
		}
		
		return isSuccess;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
	