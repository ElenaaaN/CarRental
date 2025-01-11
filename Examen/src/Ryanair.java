public class Ryanair {
    private String NUMAR_ZBOR;
    private String DESTINATIE;
    private String ORIGINE;
    private String DATA_PLECĂRII;
    private String ORA_PLECĂRII;
    private String ORA_SOSIRII;
    private int DURATA_ZBORULUI_MINUTE;
    private int PRET;
    private int LOCURI_DISPONIBILE;
    private String TIP_AVION;

    public Ryanair(String NUMAR_ZBOR, String DESTINATIE, String ORIGINE, String DATA_PLECĂRII, String ORA_PLECĂRII, String ORA_SOSIRII, int DURATA_ZBORULUI_MINUTE, int PRET, int LOCURI_DISPONIBILE, String TIP_AVION) {
        this.NUMAR_ZBOR = NUMAR_ZBOR;
        this.DESTINATIE = DESTINATIE;
        this.ORIGINE = ORIGINE;
        this.DATA_PLECĂRII = DATA_PLECĂRII;
        this.ORA_PLECĂRII = ORA_PLECĂRII;
        this.ORA_SOSIRII = ORA_SOSIRII;
        this.DURATA_ZBORULUI_MINUTE = DURATA_ZBORULUI_MINUTE;
        this.PRET = PRET;
        this.LOCURI_DISPONIBILE = LOCURI_DISPONIBILE;
        this.TIP_AVION = TIP_AVION;
    }

    public String getNUMAR_ZBOR() {
        return NUMAR_ZBOR;
    }

    public void setNUMAR_ZBOR(String NUMAR_ZBOR) {
        this.NUMAR_ZBOR = NUMAR_ZBOR;
    }

    public String getDESTINATIE() {
        return DESTINATIE;
    }

    public void setDESTINATIE(String DESTINATIE) {
        this.DESTINATIE = DESTINATIE;
    }

    public String getORIGINE() {
        return ORIGINE;
    }

    public void setORIGINE(String ORIGINE) {
        this.ORIGINE = ORIGINE;
    }

    public String getDATA_PLECĂRII() {
        return DATA_PLECĂRII;
    }

    public void setDATA_PLECĂRII(String DATA_PLECĂRII) {
        this.DATA_PLECĂRII = DATA_PLECĂRII;
    }

    public String getORA_PLECĂRII() {
        return ORA_PLECĂRII;
    }

    public void setORA_PLECĂRII(String ORA_PLECĂRII) {
        this.ORA_PLECĂRII = ORA_PLECĂRII;
    }

    public String getORA_SOSIRII() {
        return ORA_SOSIRII;
    }

    public void setORA_SOSIRII(String ORA_SOSIRII) {
        this.ORA_SOSIRII = ORA_SOSIRII;
    }

    public int getDURATA_ZBORULUI_MINUTE() {
        return DURATA_ZBORULUI_MINUTE;
    }

    public void setDURATA_ZBORULUI_MINUTE(int DURATA_ZBORULUI_MINUTE) {
        this.DURATA_ZBORULUI_MINUTE = DURATA_ZBORULUI_MINUTE;
    }

    public int getPRET() {
        return PRET;
    }

    public void setPRET(int PRET) {
        this.PRET = PRET;
    }

    public int getLOCURI_DISPONIBILE() {
        return LOCURI_DISPONIBILE;
    }

    public void setLOCURI_DISPONIBILE(int LOCURI_DISPONIBILE) {
        this.LOCURI_DISPONIBILE = LOCURI_DISPONIBILE;
    }

    public String getTIP_AVION() {
        return TIP_AVION;
    }

    public void setTIP_AVION(String TIP_AVION) {
        this.TIP_AVION = TIP_AVION;
    }

    @Override
    public String toString() {
        return "Ryanair{" +
                "NUMAR_ZBOR='" + NUMAR_ZBOR + '\'' +
                ", DESTINATIE='" + DESTINATIE + '\'' +
                ", ORIGINE='" + ORIGINE + '\'' +
                ", DATA_PLECĂRII='" + DATA_PLECĂRII + '\'' +
                ", ORA_PLECĂRII='" + ORA_PLECĂRII + '\'' +
                ", ORA_SOSIRII='" + ORA_SOSIRII + '\'' +
                ", DURATA_ZBORULUI_MINUTE=" + DURATA_ZBORULUI_MINUTE +
                ", PRET=" + PRET +
                ", LOCURI_DISPONIBILE=" + LOCURI_DISPONIBILE +
                ", TIP_AVION='" + TIP_AVION + '\'' +
                '}';
    }
}
