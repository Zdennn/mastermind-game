import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class SettingsDialog extends JDialog {

    private JComboBox<Integer> pocetBarevCombo;
    private JComboBox<String> opakovaniCombo;
    private JComboBox<Integer> pocetPolicekCombo;
    private JComboBox<String> hodnoceniCombo;

    private Nastaveni nastaveni = new Nastaveni();

    private LogikFrame loginFrame;

    public SettingsDialog(LogikFrame logikFrame) {
        this.loginFrame = logikFrame;
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        initComponents();
    }

    public Nastaveni showDialog() {
        setVisible(true);
        return nastaveni;
    }

    private void initComponents() {
        setLayout(new GridLayout(5, 2, 10, 10));

        // 1. Počet barev
        add(new JLabel("Počet barev:"));
        pocetBarevCombo = new JComboBox<>(new Integer[] { 5, 6, 7, 8 });
        add(pocetBarevCombo);

        // 2. Opakování barev
        add(new JLabel("Opakování barev:"));
        opakovaniCombo = new JComboBox<>(new String[] { "ne", "ano" });
        add(opakovaniCombo);

        // 3. Počet políček v řádce
        add(new JLabel("Počet políček v jedné řádce:"));
        pocetPolicekCombo = new JComboBox<>(new Integer[] { 4, 5 });
        add(pocetPolicekCombo);

        // 4. Způsob hodnocení
        add(new JLabel("Způsob hodnocení:"));
        hodnoceniCombo = new JComboBox<>(new String[] { "přesná pozice", "bez přesné pozice" });
        add(hodnoceniCombo);

        // Tlačítko OK
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int pocetBarev = (Integer) pocetBarevCombo.getSelectedItem();
            boolean opakovani = opakovaniCombo.getSelectedItem().equals("ano");
            int pocetPolicek = (Integer) pocetPolicekCombo.getSelectedItem();
            boolean presnaPozice = hodnoceniCombo.getSelectedItem().equals("přesná pozice");

            nastaveni.setPocetBarev(pocetBarev);
            nastaveni.setOpakovaniBarev(opakovani);
            nastaveni.setPocetPolicek(pocetPolicek);
            nastaveni.setPresnaPozice(presnaPozice);

            dispose();
        });
        add(okButton);

        add(new JLabel(""));

        pack();
        setLocationRelativeTo(loginFrame);
    }

}
