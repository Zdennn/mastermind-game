import java.util.ArrayList;

public class LogikData {

    private byte[] kombinace;
    private ArrayList<byte[]> pokusy = new ArrayList<>();// 1 = RED, 2 = BLUE, 3 = GREEN, 4 = YELLOW, 5 = ORANGE, 6 =
                                                         // MAGENTA, 7 = CYAN, 8 = PINK

    private Nastaveni nastaveni;

    public LogikData(Nastaveni nastaveni) {
        this.nastaveni = nastaveni;

        novaHra();
    }

    private void novaHra() {
        kombinace = new byte[nastaveni.getPocetPolicek()];

        if (nastaveni.isOpakovaniBarev()) {
            //Kombinace s opakováním barev
            for (int i = 0; i < kombinace.length; i++) {
                kombinace[i] = (byte) (Math.random() * nastaveni.getPocetBarev());
            }
        } else {
            //Kombinace bez opakování barev
            ArrayList<Byte> dostupneBarvy = new ArrayList<>();
            for (byte i = 0; i < nastaveni.getPocetBarev(); i++) {
                dostupneBarvy.add(i);
            }

            for (int i = 0; i < kombinace.length; i++) {
                int index = (int) (Math.random() * dostupneBarvy.size());
                kombinace[i] = dostupneBarvy.remove(index);
            }
        }
    }

    public byte[] getKombinaci() {
        return kombinace;
    }

    public int getCisloPokusu() {
        return pokusy.size();
    }

    public Boolean[] novyPokus(byte[] barvy) {
        int delka = kombinace.length;
        Boolean[] vysledek = new Boolean[delka];
        
        if (nastaveni.isPresnaPozice()) {
            //S přesnou pozicí
            
            //Null (žádná shoda)
            for (int i = 0; i < delka; i++) {
                vysledek[i] = null;
            }
            
            //Přesné shody (černá)
            boolean[] pouziteVKombinaci = new boolean[delka];
            
            for (int i = 0; i < delka; i++) {
                if (barvy[i] == kombinace[i]) {
                    vysledek[i] = true;
                    pouziteVKombinaci[i] = true;
                }
            }
            
            //Částečné shody (bílá)
            for (int i = 0; i < delka; i++) {
                if (vysledek[i] == null) {  //Pokud ještě není označeno
                    for (int j = 0; j < delka; j++) {
                        if (!pouziteVKombinaci[j] && barvy[i] == kombinace[j]) {
                            vysledek[i] = false;
                            pouziteVKombinaci[j] = true;
                            break;
                        }
                    }
                }
            }
        } else {
            //Bez přesné pozice
            
            int presneShody = 0;
            int castecneShody = 0;
            
            boolean[] pouziteVKombinaci = new boolean[delka];
            boolean[] pouziteVHadani = new boolean[delka];
            
            //Kontrola přesných shod
            for (int i = 0; i < delka; i++) {
                if (barvy[i] == kombinace[i]) {
                    presneShody++;
                    pouziteVKombinaci[i] = true;
                    pouziteVHadani[i] = true;
                }
            }
            
            //Kontrola částečných shod
            for (int i = 0; i < delka; i++) {
                if (!pouziteVHadani[i]) {
                    for (int j = 0; j < delka; j++) {
                        if (!pouziteVKombinaci[j] && barvy[i] == kombinace[j]) {
                            castecneShody++;
                            pouziteVKombinaci[j] = true;
                            break;
                        }
                    }
                }
            }
            
            int index = 0;
            
            for (int i = 0; i < presneShody; i++) {
                vysledek[index++] = true;
            }
            for (int i = 0; i < castecneShody; i++) {
                vysledek[index++] = false;
            }
            while (index < delka) {
                vysledek[index++] = null;
            }
        }
        
        pokusy.add(barvy);
        return vysledek;
    }
    

}
