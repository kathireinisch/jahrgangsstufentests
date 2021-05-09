import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
                seq=seq+line;
                geneList.get(id).setSeq(seq);
            }

        }
        return geneList;
    }
}
