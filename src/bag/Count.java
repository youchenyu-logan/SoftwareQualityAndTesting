package bag;
import java.io.*;
import java.util.Vector;
import java.util.regex.Pattern;//����������ʽƥ��

public class Count {
	private int charNum;
    private int wordNum;
    private int lineNum;
    private String filepath;
    private String outfilepath;
    private File sourcefilepath;
    private File outfile;
    private InputStreamReader inputStreamreader;
    private OutputStream outputstream;
    private BufferedReader bufferReader;
    
    public Count(File sourcefilepath, String outfilepath)
    		throws IOException{
    	File file = new File("");
        this.filepath = file.getAbsolutePath()+"\\";
        this.sourcefilepath = sourcefilepath;
        this.outfilepath = outfilepath;
        this.outfile = new File(outfilepath);
        this.outputstream = new FileOutputStream(outfile);
    }
    
    public void readFile() throws FileNotFoundException{
    	inputStreamreader = new InputStreamReader(
                new FileInputStream(sourcefilepath));
    	bufferReader = new BufferedReader(inputStreamreader);
    }
    
    public void wordcount(Vector<String> instructions) throws IOException {
    		readFile();
    		if (instructions.contains("-c"))
                countChar();
            if (instructions.contains("-w"))
                countWord();
            if (instructions.contains("-l"))
                countLine();
            
            inputStreamreader.close();
            bufferReader.close();
            outputstream.close();
    }
    
    public String countChar() throws IOException {
    	readFile();
    	String s;
        charNum = 0;
        while ((s = bufferReader.readLine()) != null)
            charNum += s.length();
        String result = sourcefilepath.getAbsolutePath().substring(filepath.length())
                + ", �ַ���: " + String.valueOf(charNum) + "\n";
        byte[] outputresult = result.getBytes();
        outputstream.write(outputresult);
        return result;
    }
    
    public String countWord() throws IOException {
    	readFile();
        String s;
        wordNum = 0;
        while ((s = bufferReader.readLine()) != null) {
            if (Pattern.matches("\\s*", s))//ƥ���κβ��ɼ��ַ�
                continue;
            String[] word = s.split(" |,");//�Կո񣬶��ţ�|���зֽ�
            for (String l : word)
                if (!Pattern.matches("\\s*",l))
                    wordNum++;
        }
        String result = sourcefilepath.getAbsolutePath().substring(filepath.length())
                + ", ������: " + String.valueOf(wordNum) + "\n";
        byte[] outputresult = result.getBytes();
        outputstream.write(outputresult);
        return result;
    }
    
    public String countLine() throws IOException {
    	readFile();
        String s;
        lineNum = 0;
        while ((s = bufferReader.readLine()) != null)
            lineNum++;
        String result = sourcefilepath.getAbsolutePath().substring(filepath.length())
                + ", ����: " + String.valueOf(lineNum) + "\r\n";
        byte[] outputresult = result.getBytes();
        outputstream.write(outputresult);
        return result;
    }
    
}
