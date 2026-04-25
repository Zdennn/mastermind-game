public class Nastaveni {

    private int pocetBarev;
    private boolean opakovaniBarev;
    private int pocetPolicek;
    private boolean presnaPozice; // true = s presnou pozici, false = bez presne pozice

    public Nastaveni(int pocetBarev, boolean opakovaniBarev, int pocetPolicek, boolean presnaPozice) {
        this.pocetBarev = pocetBarev;
        this.opakovaniBarev = opakovaniBarev;
        this.pocetPolicek = pocetPolicek;
        this.presnaPozice = presnaPozice;
    }

    public Nastaveni() {

    }

    public int getPocetBarev() {
        return pocetBarev;
    }

    public void setPocetBarev(int pocetBarev) {
        this.pocetBarev = pocetBarev;
    }

    public boolean isOpakovaniBarev() {
        return opakovaniBarev;
    }

    public void setOpakovaniBarev(boolean opakovaniBarev) {
        this.opakovaniBarev = opakovaniBarev;
    }

    public int getPocetPolicek() {
        return pocetPolicek;
    }

    public void setPocetPolicek(int pocetPolicek) {
        this.pocetPolicek = pocetPolicek;
    }

    public boolean isPresnaPozice() {
        return presnaPozice;
    }

    public void setPresnaPozice(boolean presnaPozice) {
        this.presnaPozice = presnaPozice;
    }

}
