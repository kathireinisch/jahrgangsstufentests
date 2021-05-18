import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args){
        //filepath aus commandline parsen
        ArgumentParser parser = ArgumentParsers.newFor("GeneRunner").build()
                .defaultHelp(true)
                .description("Get interesting information from genes");
        parser.addArgument("-f", "--filepath")
                .help("File to parse genes from.");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        String filepath = ns.getString("filepath");

        
        HashMap<String, Gene> genes = new HashMap<>();
        try {
            //fasta file einlesen in hashmap
            genes = parseGenes(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO weitere Aufgaben
    }

    public static HashMap<String, Gene> parseGenes(String filepath) throws IOException {
        HashMap<String, Gene> geneList = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        String id ="";
        char[] allowedChars = {'A', 'T', 'C', 'G'};
        while (((line=reader.readLine()))!=null){
            if(line.startsWith(">")){
                //wichtige annotationen einlesen, gen objekt damit erstellen
                String[] attributes = line.split("\t");
                id = attributes[0].replace(">", "");
                String name = attributes[1];
                geneList.put(id, new Gene(id, "", name));
            } else {
                String test ="helloAT";
                //alles was nach der Gene id zeile kommt, gehoert zur sequenz des jeweiligen gens
                //falls die sequenz ein zeichen != A/T/C/G enthält, wird das gen rausgelassen
                if (!line.matches("[A|T|C|G]*")){
                    if (geneList.get(id)!= null){
                        geneList.remove(id);
                    }
                    continue;
                }
                String seq = geneList.get(id).getSeq();
                seq=seq+line;
                geneList.get(id).setSeq(seq);
            }

        }
        return geneList;
    }




    //Aufgabe 3
    //3e)
    public void calculateAlignment(Gene gene, Gene otherGene){
        //get Sequence of both Gene objects
        String sequence = gene.getSeq();
        String otherSequence = otherGene.getSeq();

        //initialize alignment String and score counter
        String alignment = "";
        int score = 0;

        //iterate through one seuence (does not matter which one since, both have the same length)
        for (int i = 0; i < sequence.length(); i++) {

            //check if both sequences have the same character at position i
            //if yes: append this character to alignmentString and increase score by 1
            //if no: append "N" to alignmentString
            if(sequence.charAt(i) == otherSequence.charAt(i)){
                alignment += sequence.charAt(i);
                score++;
            }else{
                alignment += "N";
            }
        }

        //print Alignment and Score
        System.out.println("Alignment: "+alignment);
        System.out.println("Score: "+ score);
    }

    //3f)
    public void printGeneNames(ArrayList<Gene> genes){

        //iterate through every Gene object in ArrayList ("genes")
        for(Gene gene : genes){
            //get name of gene by calling getName()
            String name = gene.getName();

            //print names separated by "," (without going into a new line: System.out.print()) (with new line: System.out.println())
            //caution: last gene will have "," at the end
            //(if you don't want this, one of many options is to build a new empty String before the for-loop: String geneNames = "";
            // iterate through for-loop and append gene names with "," to this String:  geneNames += name+ "," ;
            // after for-loop eliminate last element of string (","): String newGeneNames = geneNames.substring(0,geneNames.lenght()-2);
            // print this new String: System.out.println(newGeneNames);

            System.out.print(name+ ","); // or System.out.print(gene.getName()+ ",")
        }
    }
}
