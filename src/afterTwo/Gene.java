public class Gene {
    private String id;
    private String chromosome;
    private int start;
    private int stop;
    private int transcriptCount = 0;

    /**
     * Konstruktor der Gene Klasse
     * @param id Gene ID
     * @param chromosome Name des Chromosoms
     * @param start Startposition des Genes
     * @param stop Stopposition des Genes
     */
    public Gene(String id, String chromosome, int start, int stop) {
        this.id = id;
        this.chromosome = chromosome;
        this.start = start;
        this.stop = stop;
    }

    /**
     * Erhöht den Transcript Count des jeweiligen Genes
     */
    public void addTranscript(){
        this.transcriptCount++;
    }

    /**
     * Getter
     * @return Transcript Count
     */
    public int getTranscriptCount() {
        return transcriptCount;
    }

    /**
     * Getter
     * @return Gene ID
     */
    public String getId() {
        return id;
    }
}
