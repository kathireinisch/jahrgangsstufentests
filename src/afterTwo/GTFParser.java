package afterTwo;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
//import org.apache.commons.cli.*;

public class GTFParser {
    private HashMap<String, Gene> geneMap = new HashMap<>();

    /**
     * Konstruktor der das GTF parsed
     * @param gtfPath String mit Pfad für das modifizierte GTF
     */
    public GTFParser(String gtfPath){
        this.parseInput(gtfPath);
    }

    /**
     * Parsed eine Zeile des GTFs; Methode ist private da man sie nicht von außerhalb aufrufen soll
     * @param line String
     */
    private void parseOneLine(String line){
        int SEQNAME = 0;
        int FEATURE = 1;
        int START = 2;
        int STOP = 3;
        int ATTRIBUTE = 4;

        String[] lineElements = line.split("\t");
        // Wir können Exons, CDS, UTR etc für die Aufgabe ignorieren
        if (lineElements[FEATURE].equals("gene") || lineElements[FEATURE].equals("transcript")){
            // .strip() löscht Leerzeichen am Anfang oder Ende des Strings
            String chromosome = lineElements[SEQNAME].strip();
            String gene_id = lineElements[ATTRIBUTE].strip();

            if (lineElements[FEATURE].equals("gene")){
                // Für Gene die jeweiligen Informationen speichern
                int start = Integer.parseInt(lineElements[START].strip());
                int stop = Integer.parseInt(lineElements[STOP].strip());
                Gene gene = new Gene(gene_id, chromosome, start, stop);
                this.geneMap.put(gene_id, gene);
            } else {
                // Für Transkripte den Transkript Count des jeweiligen Gens erhöhen
                if (this.geneMap.containsKey(gene_id)){
                    this.geneMap.get(gene_id).addTranscript();
                } else {
                    throw new RuntimeException("In the GTF file exists a transcript without a gene");
                }
            }
        }
    }

    /**
     * Geht alle Zeilen des GTFs durch und führt für jede Zeile die Methode parseOneLine aus
     * @param path String GTF Pfad
     */
    private void parseInput(String path){
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            // br.readLine == null wenn die Datei am Ende ist
            while ((line = br.readLine()) != null) {
                // Überspringe leere Zeilen und GTF Kommentarzeilen
                if(line.length() > 0 && line.charAt(0) != '#') {
                    parseOneLine(line);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Issue while reading the file");
            e.printStackTrace();
        }
    }

    /**
     * Schreibt eine TSV mit den Spalten gene_id und transcript_count
     * @param path String Pfad für den Output
     */
    public void writeFile(String path){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write("gene_id\ttranscript_count\n");
            // Über alle Gene in der HashMap loopen und die Zeile schreiben
            for (Gene curGene: this.geneMap.values()){
                writer.write(curGene.getId()+"\t"+curGene.getTranscriptCount()+"\n");
            }
            writer.close();
        } catch (IOException e){
            System.out.println("Issue while writing the file");
            e.printStackTrace();
        }
    }

    /**
     * Main Methode, diese wird als erstes ausgeführt. Hier werden die CLI Parameter geparsed und
     * ein Element der GTFParser Klasse erstellt. Der Output wird am Ende geschrieben.
     * @param args CLI Arguments
     */
    public static void main(String[] args) {
       /* // Erstellen der Command Line Arguments
        Options options = new Options();
        // GTF ist eine notwendige Flag und hat ein Argument (den Pfad)
        options.addOption(Option.builder("gtf").hasArg().required().desc("gtf file").build());
        // output ist eine notwendige Flag und hat ein Argument (den Pfad)
        options.addOption(Option.builder("output").hasArg().required().desc("output file").build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String gtfPath = cmd.getOptionValue("gtf");
            String outputPath = cmd.getOptionValue("output");
            // parsen des GTFs
            GTFParser gtfParser = new GTFParser(gtfPath);
            // schreiben des Ergebnisses
            gtfParser.writeFile(outputPath);
        } catch (ParseException e){
            System.err.println("Error when parsing the command line inputs");
            e.printStackTrace();
        }*/
    }
}
