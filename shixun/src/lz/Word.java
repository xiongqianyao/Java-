package lz;

public class Word {
    private String spell;
    private String mean;
    private String[] options;
    private boolean proper;

    public Word(String spell, String mean, String[] options) {
        this.spell = spell;
        this.mean = mean;
        this.options = options;
    }
    public boolean isProper()
    {
        return proper;
    }
    public void setProper(boolean proper) {
        this.proper = proper;
    }

    public String getSpell() {
        return spell;
    }

    public String getMean() {
        return mean;
    }

    public String[] getOptions() {
        return options;
    }
}


