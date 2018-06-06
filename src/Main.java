import java.lang.reflect.Array;
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
        return syntax.length() == index;
    }

    public Container getNextOne(){
        if(isEnd()){
            return null;
        }
        else{
            return new Container(syntax, index + 1);
        }
    }

    public String getFullSyntax(){

        if(index == 0){
            return "." + syntax;
        }
        else if(index == syntax.length() - 1){
            return syntax + ".";
        }
        else{
            return syntax.substring(0, index - 1) + "." + syntax.substring(index - 1, syntax.length() );
        }
        //return syntax.substring(index - 1) + "." + syntax.substring(index, syntax.length() - 1);

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
        for(ArrayList<Container> list : iZero){
            System.out.print("I>");
            for(Container iter : list){
                System.out.print("[" + iter.getFullSyntax() + "]");
            }
            System.out.println();
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
        iZero.add(new ArrayList<>());
        for(String key : keyList){
            for(String value : syntaxes.get(key)){
                iZero.get(0).add(new Container(value, 0));
            }
        }
        //fix it
        int length;
        int index = 0;
        do{
            length = iZero.size();
            i0(iZero.get(index++));
        }while(iZero.size() != length);
    }

    public static void i0(ArrayList<Container> list){
        HashMap<String, ArrayList<Container>> input = new HashMap<>();
        for(Container c : list){
            //error
            String state;
            if(c.isEnd()){
                continue;
            }
            else{
                state = Character.toString(c.syntax.charAt(c.index));
            }
            if(!input.containsKey(state)){
                //add ararylist if this state is first time
                input.put(state, new ArrayList<>());
            }
            ArrayList<Container> temp = input.get(state);
            Container nextone = c.getNextOne();
            if(nextone != null){
                //check if string is ended
                ArrayList<Container> value = existOnIZero(nextone);
                if(value != null){
                    //add exist data
                    temp.addAll(value);
                }
                else{
                    //add next state
                    temp.add(nextone);
                }
            }
        }
        for(String str : input.keySet()){
            iZero.add(input.get(str));
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

    public static void i(String str){

    }

    public static void writeFile(String file){

    }

}
