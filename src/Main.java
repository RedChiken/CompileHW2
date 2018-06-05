import java.util.*;
import java.io.*;

public class Main {

    static HashMap<String, ArrayList<String>> syntaxes = new HashMap<String, ArrayList<String>>();
    static HashMap<String, String> rule = new HashMap<>();
    static ArrayList<String> keyList = new ArrayList<String>();
    public static void main(String[] args) {
        try {
            readFile("test.txt");
            String startSymbol = keyList.get(0);
            for(String key : keyList){
                System.out.println(key);
                for(String value : syntaxes.get(key)){
                    System.out.println(key + ">" + value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        String ruleName, line;
        String[] splitString;
        while((ruleName = br.readLine()) != null){
            line = br.readLine();
            rule.put(ruleName, line);
            splitString = line.split(">");
            for(int i = 2; i < splitString.length; i++){
                splitString[1] += splitString[i];
            }
            if(!keyList.contains(splitString[0])){
                keyList.add(splitString[0]);
                syntaxes.put(splitString[0], new ArrayList<String>());
            }
            syntaxes.get(splitString[0]).add(splitString[1]);
        }
    }

    public static void writeFile(String file){

    }

}
