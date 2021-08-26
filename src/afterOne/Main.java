package afterOne;

import afterOne.Gene;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class Main {

    public static void main(String[] args){
        //filepath aus commandline parsen
        ArgumentParser parser = ArgumentParsers.newFor("GeneRunner").build()
                .defaultHelp(true)
                .description("Get interesting information from genes");
        parser.addArgument("-f")
                .help("File to parse genes from.");
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        String filepath = ns.getString("f");

        HashMap<String,Gene> genes = Gene.parseGenes(filepath);


        //Aufgabe 4:

        //initialize scanner
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(System.lineSeparator());

        String gene_entered;
        int task;

        while(true){
            System.out.println("Willkommen in ihrer Gen-Sammlung, was möchten Sie tun?");
            System.out.println("[1] Gen-Id anzeigen\n" +
                    "[2] Sequenz von Gen anzeigen\n" +
                    "[3] Länge eines Gens anzeigen\n" +
                    "[4] GC-Gehalt eines Gens anzeigen\n" +
                    "[5] Revers-Komplementäre Sequenz anzeigen\n" +
                    "[6] Alignment von zwei Genen machen\n" +
                    "[7] Programm beenden");

            // Nachschauen ob die nächste Aufgabe gültig ist
            if(scanner.hasNext("[1-7]")){
                task = scanner.nextInt();
            } else {
                System.out.println("Ungültige Eingabe!");
                task = 0; //switch case wird nicht ausgeführt
                scanner.next();
            }

            // Je nach Eingabe die richtige Methode ausführen
            switch(task){
                case 1:
                    printGeneNames(genes);
                    break;
                case 2:
                    System.out.println("Für welches Gen wollen Sie diese Interaktion betätigen?");
                    gene_entered = scanner.next(); //User gibt Gen Id ein
                    if (checkIfGeneInMap(gene_entered, genes)){
                        Gene gene = genes.get(gene_entered);
                        System.out.println(gene.getSeq());
                    } else {
                        System.out.println("Für diese Gen haben wir leider keinen Eintrag! Versuchen Sie es erneut!");
                    }
                    break;
                case 3:
                    System.out.println("Für welches Gen wollen Sie diese Interaktion betätigen?");
                    gene_entered = scanner.next(); //User gibt Gen Id ein
                    if (checkIfGeneInMap(gene_entered, genes)){
                        Gene gene = genes.get(gene_entered);
                        System.out.println(gene.getLength());
                    } else {
                        System.out.println("Für diese Gen haben wir leider keinen Eintrag! Versuchen Sie es erneut!");
                    }
                    break;
                case 4:
                    System.out.println("Für welches Gen wollen Sie diese Interaktion betätigen?");
                    gene_entered = scanner.next(); //User gibt Gen Id ein
                    if (checkIfGeneInMap(gene_entered, genes)){
                        Gene gene = genes.get(gene_entered);
                        System.out.println(gene.calculateGcContent());
                    } else {
                        System.out.println("Für diese Gen haben wir leider keinen Eintrag! Versuchen Sie es erneut!");
                    }
                    break;
                case 5:
                    System.out.println("Für welches Gen wollen Sie diese Interaktion betätigen?");
                    gene_entered = scanner.next(); //User gibt Gen Id ein
                    if (checkIfGeneInMap(gene_entered, genes)){
                        Gene gene = genes.get(gene_entered);
                        System.out.println(gene.calculateReverseComplement(gene.getSeq()));
                    } else {
                        System.out.println("Für diese Gen haben wir leider keinen Eintrag! Versuchen Sie es erneut!");
                    }
                    break;
                case 6:
                    System.out.println("Geben Sie das erste Gen für das Alignment ein.");
                    String first = scanner.next();
                    if (checkIfGeneInMap(first, genes)) {
                        Gene first_gene = genes.get(first);
                        System.out.println("Geben Sie das zweite Gen für das Alignment ein.");
                        String second = scanner.next();
                        if (checkIfGeneInMap(second, genes)){
                            Gene second_gene = genes.get(second);
                            compareSequences(first_gene, second_gene);
                        } else {
                            System.out.println("Für diese Gen haben wir leider keinen Eintrag! Versuchen Sie es erneut!");
                        }
                    } else {
                        System.out.println("Für diese Gen haben wir leider keinen Eintrag! Versuchen Sie es erneut!");
                    }

                    break;
                case 7:
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    break;

            }

        }

    }


    //Aufgabe 3
    //3e)
    public static void compareSequences(Gene gene, Gene otherGene){


        // check if sequences are the same length
        if (gene.getLength() != otherGene.getLength()){
            System.out.println("Sequences have unequal length!"); //could also throw an Exception
        }

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
        System.out.println("Matching-Sequenz: "+ alignment);
        System.out.println("Score: "+ score);
    }

    //3f)
    public static void printGeneNames(HashMap<String, Gene> genes){
        List<String> names = new ArrayList<>();
        //iterate through every Gene object in HashMap ("genes")
        for(Gene gene : genes.values()){
            //get name of gene by calling getName()
            //String name = gene.getName();

            names.add(gene.getName());

            //print names separated by "," (without going into a new line: System.out.print()) (with new line: System.out.println())
            //caution: last gene will have "," at the end
            //(if you don't want this, one of many options is to build a new empty String before the for-loop: String geneNames = "";
            // iterate through for-loop and append gene names with "," to this String:  geneNames += name+ "," ;
            // after for-loop eliminate last element of string (","): String newGeneNames = geneNames.substring(0,geneNames.lenght()-2);
            // print this new String: System.out.println(newGeneNames);

            //System.out.print(name+ ","); // or System.out.print(gene.getName()+ ",")
        }

        String joined = String.join(", ", names);
        System.out.println(joined);
    }

    // Aufgabe 4
    public static boolean checkIfGeneInMap(String gene_id, HashMap<String, Gene> genes){
        if (genes.containsKey(gene_id)){
            return true;
        } else return false;
    }
}
