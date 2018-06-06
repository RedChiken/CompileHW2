import java.util.*;
import java.io.*;

class Container{
    public String syntax;
    public int index;
    public Container(String key, int index){
        this.syntax = key;
        this.index = index;
    }

    public boolean isEnd(){
        return syntax.length() - index > 0;
    }

    public Container getNextOne(){
        if(isEnd()){
            return null
        }
        else{
            return new Container(syntax, index + 1);
        }
    }
}

public class Main {

    static HashMap<String, ArrayList<String>> syntaxes = new HashMap<String, ArrayList<String>>();
    static HashMap<String, String> rule = new HashMap<>();
    static ArrayList<String> keyList = new ArrayList<String>();
    static HashMap<Container, ArrayList<Container>> iZero = new HashMap<Container, ArrayList<Container>>();


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
            splitString = line.split(">", 2);
            if(!keyList.contains(splitString[0])){
                keyList.add(splitString[0]);
                syntaxes.put(splitString[0], new ArrayList<String>());
            }
            syntaxes.get(splitString[0]).add(splitString[1]);
        }
    }

    public static void c0(){
        Container start = new Container("S->" + keyList.get(0), 0);
        iZero.put(start, new ArrayList<>());
        for(String key : keyList){
            for(String value : syntaxes.get(key)){
                iZero.get(start).add(new Container(value, 0));
                String syntaxkey = Character.toString(value.charAt(0));
                if (keyList.contains(syntaxkey)){
                }
            }
        }

    }

    public static void i(String str){

    }

    public static void writeFile(String file){

    }

}
