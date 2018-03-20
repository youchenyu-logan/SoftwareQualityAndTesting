package bag;
import java.io.*;
import java.util.regex.Pattern;
import java.util.Vector;


public class main {
	public static String Path;
	public static String outPath;
	public static Vector<File> sourcefile;
	public static Vector<String> instruction;
	public static Vector<String> stopList;
	
	public static void main(String[] args){//args是命令行参数
		File file = new File("");
        Path = file.getAbsolutePath()+"\\";//获取绝对路径
		outPath = "result.txt";
		sourcefile = new Vector<File>();
		instruction = new Vector<String>();
		boolean flag = false;
		for(int i = 0; i < args.length; i++) {
            if (args[i].equals("-o")) {
            	i++;
                if (i == args.length) 
                	return;
                outPath = args[i];
                
            }
            else if (args[i].equals("-s"))
                flag = true;
            else if (args[i].equals("-c") || args[i].equals("-w") || args[i].equals("-l") || args[i].equals("-a"))
                {
            	instruction.addElement(args[i]);
                }
            else if(args[i].equals("-e"))
            	{
            	try {
            		stopingList(args[i]);
            	}
                catch (IOException e){
                    System.out.println("找不到停用文件");
                    return;
                }
            	}
            else {
            	if(!flag)
            	sourcefile.addElement(new File(Path +args[i]));
            	else
            		recursionFiles(Path);
            }
                
            }
		System.out.println(sourcefile);
		System.out.println(outPath);
		try{
			Count wordcount = new Count(sourcefile,outPath);
			wordcount.wordcount(instruction);
		}
		catch(IOException e){
			System.out.println("找不到输入文件");
			return;
		}
		return;
	}
	
	public static void recursionFiles(String filePath) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("目录下没有文件");
            return;
        }
        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".c"))
                sourcefile.addElement(f);
            else if (f.isDirectory())
            	recursionFiles(f.getAbsolutePath());
        }
    }
	
	public static void stopingList(String stopListFilename) throws IOException {
        InputStreamReader inputStreamreader = new InputStreamReader(
                new FileInputStream(Path + stopListFilename));
        BufferedReader bufferReader = new BufferedReader(inputStreamreader);
        String s;
        while ((s = bufferReader.readLine()) != null) {
            String[] list = s.split(" ");
            for (String List : list)
                if(!Pattern.matches("\\s*",List))
                	stopList.addElement(List.toLowerCase());
        }
    }
	
}

	
