package bag;
import java.io.*;
import java.util.regex.Pattern;
import java.util.Vector;


public class main {
	public static String Path;
	public static String outPath;
	public static File sourcefile;
	public static Vector<String> instruction;
	
	public static void main(String[] args){//args是命令行参数
		File file = new File("");
        Path = file.getAbsolutePath()+"\\";//获取绝对路径
		outPath = "result.txt";
		instruction = new Vector<String>();
		//System.out.println(args.length);
		for(int i = 0; i < args.length; i++) {
            if (args[i].equals("-o")) {
            	i++;
                if (i == args.length) 
                	return;
                outPath = args[i];
                
            }
            else if (args[i].equals("-c") || args[i].equals("-w") || args[i].equals("-l"))
                {
            	instruction.addElement(args[i]);
                }
            else 
                sourcefile=new File(Path +args[i]);
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
	
	
	
}

	
