import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

public class LogikFrame extends JFrame {
    Nastaveni nastaveni;
    LogikData data;

    private Color[] barvy = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN,
            Color.PINK };
    private Color vybranaBarva = barvy[0];

    private Policko[][][] vsechnaPolicka = new Policko[10][2][];

    public LogikFrame() {
        super("Logik");

        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ziskatNastaveni();
    }

    private void ziskatNastaveni() {
        SettingsDialog sd = new SettingsDialog(this);
        sd.setModal(true);

        this.nastaveni = sd.showDialog();
        data = new LogikData(nastaveni);
        initComponents();
    }

    private void initComponents() {

        // Panel pro kombinace
        JPanel kombinacePanel = new JPanel();
        kombinacePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        kombinacePanel.setBorder(BorderFactory.createTitledBorder("Kombinace"));
        byte[] kombinace = data.getKombinaci();
        for (byte b : kombinace) {
            JPanel barvaPanel = new JPanel();
            barvaPanel.setBackground(Color.GRAY);
            barvaPanel.setPreferredSize(new Dimension(30, 30));
            barvaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            kombinacePanel.add(barvaPanel);
        }

        // Panel pro tlačítka
        JPanel buttonPanel = new JPanel();
        JButton vyhodnotButton = new JButton("Vyhodnocení kola");
        JButton novaHraButton = new JButton("Nová hra");
        buttonPanel.add(vyhodnotButton);
        buttonPanel.add(novaHraButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(kombinacePanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Hlavní panel s 10 řádky a 2 sloupci
        JPanel gridPanel = new JPanel(new GridLayout(10, 2, 5, 5));

        for (int radek = 0; radek < 10; radek++) {
            for (int sloupec = 0; sloupec < 2; sloupec++) {
                JPanel radekPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
                Policko[] policka = new Policko[nastaveni.getPocetPolicek()];

                if (sloupec == 1) { // Sloupec pro vyhodnocení
                    for (int p = 0; p < nastaveni.getPocetPolicek(); p++) {
                        Policko policko = new Policko();
                        policko.setReadonly(true);
                        policka[p] = policko;
                        radekPanel.add(policko);
                    }
                } else { // Sloupec pro zadávání barev
                    for (int p = 0; p < nastaveni.getPocetPolicek(); p++) {
                        Policko policko = new Policko();
                        if (radek != data.getCisloPokusu()) {
                            policko.setReadonly(true);
                        }
                        policka[p] = policko;
                        radekPanel.add(policko);
                    }
                }

                vsechnaPolicka[radek][sloupec] = policka;
                gridPanel.add(radekPanel);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // Panel s výběrem barev
        JPanel colorPanel = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < nastaveni.getPocetBarev(); i++) {
            final int index = i;
            JButton colorBtn = new JButton();
            colorBtn.setBackground(barvy[index]);
            colorBtn.setPreferredSize(new Dimension(30, 30));
            colorBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            colorBtn.addActionListener(e -> {
                vybranaBarva = barvy[index]; // Nastavení vybrané barvy

                // Zvýraznění vybrané barvy
                for (Component comp : colorPanel.getComponents()) {
                    if (comp instanceof JButton) {
                        ((JButton) comp).setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }
                }
                colorBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); // Zvýraznění vybrané barvy
            });

            buttonGroup.add(colorBtn);
            colorPanel.add(colorBtn);
        }

        // Zvýraznění první barvy jako výchozí
        ((JButton) colorPanel.getComponent(0)).setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        add(colorPanel, BorderLayout.SOUTH);

        setSize(600, 700);
        setLocationRelativeTo(null);
        setVisible(true);

        vyhodnotButton.addActionListener(e -> {

            byte[] barvyPole = new byte[nastaveni.getPocetPolicek()];

            for (int i = 0; i < vsechnaPolicka[data.getCisloPokusu()][0].length; i++) {
                Integer cisloBarvy = vsechnaPolicka[data.getCisloPokusu()][0][i].getCisloBarvy(barvy);
                if (cisloBarvy == null) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Vyberte barvy do všech polí!", "Chyba",
                            javax.swing.JOptionPane.WARNING_MESSAGE);
                    return;
                }
                barvyPole[i] = cisloBarvy.byteValue();
            }

            // true = černá, false = bílá, null = nic
            Boolean[] vysledek = data.novyPokus(barvyPole);

            // Kontrola, zda jsou všechny výsledky true
            boolean vyhra = true;
            for (Boolean vys : vysledek) {
                if (!Boolean.TRUE.equals(vys)) {
                    vyhra = false;
                    break;
                }
            }
            if (vyhra) {
                zobrazitKombinaci(kombinacePanel);
                javax.swing.JOptionPane.showMessageDialog(this, "Gratulujeme! Vyhrál jste!", "Výhra",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                getContentPane().removeAll();
                ziskatNastaveni();
                revalidate();
                repaint();
                return;
            }
            if (data.getCisloPokusu() == 10) {
                zobrazitKombinaci(kombinacePanel);
                javax.swing.JOptionPane.showMessageDialog(this, "Bohužel, prohrál jste! Zkuste to znovu.", "Prohra",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                getContentPane().removeAll();
                ziskatNastaveni();
                revalidate();
                repaint();
                return;
            }

            aktualizaceMainPaneluVysledky(gridPanel, vysledek);
            aktualizaceMainPaneluVyber(gridPanel);
        });

        // Tlačítko pro zahájení nové hry
        novaHraButton.addActionListener(e -> {

            getContentPane().removeAll();
            ziskatNastaveni();
            revalidate();
            repaint();

        });

        // Zobrazení kombinace na začátku hry
        //zobrazitKombinaci(kombinacePanel);
    }

    private void zobrazitKombinaci(JPanel kombinacePanel) {
        Component[] komponenty = kombinacePanel.getComponents();
        byte[] kombinace = data.getKombinaci();

        for (int i = 0; i < komponenty.length; i++) {
            if (komponenty[i] instanceof JPanel) {
                JPanel barvaPanel = (JPanel) komponenty[i];
                barvaPanel.setBackground(barvy[kombinace[i]]);
            }
        }

        kombinacePanel.revalidate();
        kombinacePanel.repaint();
    }

    private void aktualizaceMainPaneluVyber(JPanel gridPanel) {

        int aktualniPokus = data.getCisloPokusu();

        // Uzamknutí předchozího řádku
        if (aktualniPokus > 0) {
            int predchoziPok = aktualniPokus - 1;
            for (Policko policko : vsechnaPolicka[predchoziPok][0]) {
                policko.setReadonly(true);
            }
        }

        // Odemknutí aktuálního řádku
        for (Policko policko : vsechnaPolicka[aktualniPokus][0]) {
            policko.setReadonly(false);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void aktualizaceMainPaneluVysledky(JPanel gridPanel, Boolean[] vysledky) {

        for (int i = 0; i < vsechnaPolicka[data.getCisloPokusu() - 1][1].length; i++) {
            if (Boolean.TRUE.equals(vysledky[i])) {
                vsechnaPolicka[data.getCisloPokusu() - 1][1][i].setCerna();
            } else if (Boolean.FALSE.equals(vysledky[i])) {
                vsechnaPolicka[data.getCisloPokusu() - 1][1][i].setBila();
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    class Policko extends JPanel {
        private Color barva = null;
        private boolean readonly = false;

        public Policko() {
            setPreferredSize(new Dimension(30, 30));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (!readonly) {
                        setBarva(vybranaBarva);
                    }
                }
            });
        }

        public void setBarva(Color c) {
            this.barva = c;
            repaint();
        }

        public void setReadonly(boolean readonly) {
            this.readonly = readonly;
            if (readonly) {
                setBackground(Color.LIGHT_GRAY);
            } else {
                setBackground(Color.WHITE);
            }
        }

        public void setCerna() {
            this.barva = Color.BLACK;
            repaint();
        }

        public void setBila() {
            this.barva = Color.WHITE;
            repaint();
        }

        public Integer getCisloBarvy(Color[] barvy) {
            if (barva == null) {
                return null;
            }
            for (int i = 0; i < barvy.length; i++) {
                if (barva.equals(barvy[i])) {
                    return i;
                }
            }
            return null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (barva != null) {
                g.setColor(barva);
                g.fillOval(4, 4, getWidth() - 8, getHeight() - 8); // Vykreslení kruhu s barvou
            }
        }
    }
}
