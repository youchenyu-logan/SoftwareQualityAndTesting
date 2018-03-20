package bag;
import java.io.*;
import java.util.Vector;
import java.util.regex.Pattern;//用于正则表达式匹配

public class Count {
	private int charNum;
    private int wordNum;
    private int lineNum;
    private int codeLineNum = 0;
    private int whiteLineNum = 0;
    private int annotationLineNum = 0;
    private String filepath;
    private String outfilepath;
    private Vector<File> sourcefilepath;
    private File outfile;
    private InputStreamReader inputStreamreader;
    private OutputStream outputstream;
    private BufferedReader bufferReader;
    
    public Count(Vector<File> sourcefilepath, String outfilepath)
    		throws IOException{
    	File file = new File("");
        this.filepath = file.getAbsolutePath()+"\\";
        this.sourcefilepath = sourcefilepath;
        this.outfilepath = outfilepath;
        this.outfile = new File(outfilepath);
        this.outputstream = new FileOutputStream(outfile);
    }
    
    public void readFile(int location) throws FileNotFoundException{
    	inputStreamreader = new InputStreamReader(
                new FileInputStream(sourcefilepath.get(location)));
    	bufferReader = new BufferedReader(inputStreamreader);
    }
    
    public void wordcount(Vector<String> instructions) throws IOException {
    	for (int i = 0; i < sourcefilepath.size(); ++i)
    	{	readFile(i);
    		if (instructions.contains("-c"))
                countChar(i);
            if (instructions.contains("-w"))
                countWord(i);
            if (instructions.contains("-l"))
                countLine(i);
            if (instructions.contains("-a"))
            	countMoreLine(i);
    	} 
            inputStreamreader.close();
            bufferReader.close();
            outputstream.close();
    }
    
    public String countChar(int location) throws IOException {
    	readFile(location);
    	String s;
        charNum = 0;
        while ((s = bufferReader.readLine()) != null)
            charNum += s.length();
        String result = sourcefilepath.get(location).getAbsolutePath().substring(filepath.length())
                + ", 字符数: " + String.valueOf(charNum) + "\n";
        byte[] outputresult = result.getBytes();
        outputstream.write(outputresult);
        return result;
    }
    
    public String countWord(int location) throws IOException {
    	readFile(location);
        String s;
        wordNum = 0;
        while ((s = bufferReader.readLine()) != null) {
            if (Pattern.matches("\\s*", s))//匹配任何不可见字符
                continue;
            String[] word = s.split(" |,");//以空格，逗号，|进行分解，因此不会对于空格进行统计，与范例有点区别
            for (String l : word)
                if (!Pattern.matches("\\s*",l))
                    wordNum++;
        }
        String result = sourcefilepath.get(location).getAbsolutePath().substring(filepath.length())
                + ", 单词数: " + String.valueOf(wordNum) + "\n";
        byte[] outputresult = result.getBytes();
        outputstream.write(outputresult);
        return result;
    }
    
    public String countLine(int location) throws IOException {
    	readFile(location);
        String s;
        lineNum = 0;
        while ((s = bufferReader.readLine()) != null)
            lineNum++;
        String result = sourcefilepath.get(location).getAbsolutePath().substring(filepath.length())
                + ", 行数: " + String.valueOf(lineNum) + "\r\n";
        byte[] outputresult = result.getBytes();
        outputstream.write(outputresult);
        return result;
    }
    
    public String countMoreLine(int location) throws IOException {
        readFile(location);
        String s;
        while((s = bufferReader.readLine())!=null) {
            if (Pattern.matches("\\s*.?\\s*", s)) {
                whiteLineNum++;
                continue;
            }else if(Pattern.matches("\\s*.?\\s*//.*", s)){
                annotationLineNum++;
                continue;
            }else{
                codeLineNum++;
                continue;
            }
        }
        String result = sourcefilepath.get(location).getAbsolutePath().substring(filepath.length()) + ", 代码行/空行/注释行: "
                + String.valueOf(codeLineNum) + "/"
                + String.valueOf(whiteLineNum)+ "/"
                + String.valueOf(annotationLineNum) + "\r\n";
        byte data[] = result.getBytes();
        outputstream.write(data);
        return result;
    }
    
    
    
}
    

