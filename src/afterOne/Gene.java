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
}
