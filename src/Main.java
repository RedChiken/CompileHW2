import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

class Container{
    public String state;
    public String syntax;
    public int index;
    public Container(String state, String key, int index){
        this.state = state;
        this.syntax = key;
        this.index = index;
    }

    public boolean isEnd(){
        return syntax.length() == index;
    }

    public Container getNextOne(){
        if(isEnd()){
            return null;
        }
        else{
            return new Container(state, syntax,index + 1);
        }
    }

    public String getFullSyntax(){
        return state + "->" + syntax.substring(0, index) + "." + syntax.substring(index, syntax.length() );
    }

    @Override
    public boolean equals(Object obj) {
        Container c = (Container)obj;
        return (this.state == c.state) && (this.syntax == c.syntax) && (this.index == c.index);
    }
}

public class Main {

    static HashMap<String, ArrayList<String>> syntaxes = new HashMap<String, ArrayList<String>>();
    static HashMap<String, String> rule = new HashMap<>();
    static ArrayList<String> keyList = new ArrayList<String>();
    static ArrayList<ArrayList<Container>> iZero = new ArrayList<ArrayList<Container>>();

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
        c0();
        try{
            writeFile("output.txt");
        }
        catch(IOException e){
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
        br.close();
    }

    public static void writeFile(String file) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        int number = 0;
        String str = new String();
        for(ArrayList<Container> list : iZero){
            str += "I" + number + ">";
            for(Container iter : list){
                str += "[" + iter.getFullSyntax() + "]";
            }
            bw.write(str);
            bw.newLine();
            str = new String();
        }
        bw.close();
    }

    public static void c0(){
        Container start = new Container("S", keyList.get(0), 0);
        iZero.add(new ArrayList<>());
        iZero.get(0).add(start);
        for(String key : keyList){
            for(String value : syntaxes.get(key)){
                iZero.get(0).add(new Container(key, value, 0));
            }
        }
        i0(iZero.get(0));
    }

    public static void i0(ArrayList<Container> list){
        HashMap<String, ArrayList<Container>> input = new HashMap<>();
        ArrayList<String> state = new ArrayList<>();
        for(Container c : list) {
            if (c.isEnd()) {
                continue;
            } else {
                state.add(Character.toString(c.syntax.charAt(c.index)));
            }
        }
        ArrayList<Container> storage;
        for(String s: state){
            storage = new ArrayList<>();
            for(Container c : list){
                if(!c.isEnd()){
                    if(Character.toString(c.syntax.charAt(c.index)).equals(s)){
                        storage.add(c.getNextOne());
                    }
                }
            }
            if(!iZero.contains(storage)){
                iZero.add(storage);
                i0(storage);
            }
        }
    }

    public static ArrayList<Container> existOnIZero(Container c){
        for(ArrayList<Container> inter : iZero){
            if(inter.get(0).equals(c)){
                return inter;
            }
        }
        return null;
    }
}
