package afterOne;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Gene {
    private String name;
    private String id;
    private String seq;

    public Gene(String name, String seq, String id){
        this.name = name;
        this.seq = seq;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSeq() {
        return seq;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static HashMap<String, Gene> parseGenes(String filepath) {
        HashMap<String, Gene> geneList = new HashMap<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            String id ="";
            while (((line=reader.readLine()))!=null){
                if(line.startsWith(">")){
                    //wichtige annotationen einlesen, gen objekt damit erstellen
                    String[] attributes = line.split("\t");
                    id = attributes[0].replace(">", "");
                    String name = attributes[1];
                    geneList.put(id, new Gene(id, "", name));
                } else {
                    //alles was nach der Gene id zeile kommt, gehoert zur sequenz des jeweiligen gens
                    String seq = geneList.get(id).getSeq();
                    seq=seq+line; // alternativ StringBuilder benutzen (effizienter)
                    geneList.get(id).setSeq(seq);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return geneList;
    }


    //Aufgabe 3
    //3a)
    public int getLength(){
        int length = seq.length();
        return length; //or return seq.length();
    }

    //3b)
    public float calculateGcContent() {

        //initilaize counter for G/C-Content in String
        int gcCounter = 0;

        //iterare through every char in String
        for (int i = 0; i < seq.length(); i++) {
            char base = seq.charAt(i);

            //check if char C or G: if yes -> increase counter
            if(base == 'C' || base == 'G'){
                gcCounter++;
            }
        }

        //get length of Sequence by calling getLength() (from 3a)
        int length = getLength();

        //calculate GC-Content: gcCounter divided by length
        //use float, since division could give you decimal digits
        float gcContent = gcCounter/length;

        //return gcContent, since calculateGcContent() is not void (= doesn't return anything), but float
        return gcContent; //or return (gcCounter/length);
    }

    //3c)
    public static String calculateComplement(String sequence){
        //initialize new String, where the complement will be build
        StringBuilder builder = new StringBuilder();

        //iterare through every char in String
        for (int i = 0; i < sequence.length(); i++) {
            char base = sequence.charAt(i);

            //1. Option: if-statements
            //check base and append complement of this base to new String (complementString)
            if(base == 'C'){
                builder.append('G');
            }
            if(base == 'G'){
                builder.append('C');
            }
            if(base == 'A'){
                builder.append('T');
            }
            if(base == 'T'){
                builder.append('A');
            }

            //2. Option: switch-case
            /*switch (base){
                case 'G':
                    builder.append('C');
                    break;
                case 'C':
                    builder.append('G');;
                    break;
                case 'A':
                    builder.append('T');;
                    break;
                case 'T':
                    builder.append('A');;
                    break;
            }*/

        }

        return builder.toString();
    }


    //3d)
    public static String calculateReverseComplement(String sequence){
        //calculate Complement by calling calculateComplement()
        String complement = calculateComplement(sequence);

        //reverse this String
        String reverse = new StringBuffer(complement).reverse().toString();
        //or other methods from the internet (e.g: https://www.geeksforgeeks.org/reverse-a-string-in-java/)

        return reverse;
    }




}
