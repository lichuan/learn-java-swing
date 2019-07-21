import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LearnSwing {
    public static void main(String[] args) throws Exception {
        learn_20();
    }

    static void learn_20() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello myui");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                JButton b1 = new JButton("Dialog box");
                class MyDialog extends JDialog {
                    MyDialog() {
                        super((Frame)null, "My dialog", true);
                        setLayout(new FlowLayout());
                        add(new JLabel("here is my dialog"));
                        JButton ok = new JButton("OK");
                        ok.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dispose();
                            }
                        });
                        add(ok);
                        setSize(150, 125);
                    }
                }
                MyDialog dlg = new MyDialog();
                b1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dlg.setVisible(true);
                    }
                });
                frame.add(b1);
            }
        });
    }

    static void learn_19() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello myui");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                JDialog f;
                class SineDraw extends JPanel {
                    int SCALEFACTOR = 200;
                    int cycles, points;
                    double[] sines;
                    int[] pts;
                    SineDraw() {
                        setCycles(5);
                    }
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        int maxWidth = getWidth();
                        double hstep = (double)maxWidth / (double)points;
                        int maxHeight = getHeight();
                        pts = new int[points];
                        for(int i = 0; i < points; ++i) {
                            pts[i] = (int)(sines[i] * maxHeight / 2 * .95 + maxHeight / 2);
                        }
                        g.setColor(Color.RED);
                        for(int i = 1; i < points; ++i) {
                            int x1 = (int)((i - 1) * hstep);
                            int x2 = (int)(i * hstep);
                            int y1 = pts[i - 1];
                            int y2 = pts[i];
                            g.drawLine(x1, y1, x2, y2);
                        }
                    }
                    void setCycles(int newCycles) {
                        cycles = newCycles;
                        points = SCALEFACTOR * cycles * 2;
                        sines = new double[points];
                        for(int i = 0; i < points; ++i) {
                            double radians = (Math.PI / SCALEFACTOR) * i;
                            sines[i] = Math.sin(radians);
                        }
                        repaint();
                    }
                }
                SineDraw sines = new SineDraw();
                JSlider adjustCycles = new JSlider(1, 30, 5);
                frame.add(sines);
                adjustCycles.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        sines.setCycles(((JSlider)e.getSource()).getValue());
                    }
                });
                frame.add(BorderLayout.SOUTH, adjustCycles);
            }
        });
    }

    static void learn_18() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello myui");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                JPopupMenu popup = new JPopupMenu();
                JTextField t = new JTextField(10);
                frame.add(t);
                ActionListener al = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        t.setText(((JMenuItem)e.getSource()).getText());
                    }
                };
                JMenuItem m = new JMenuItem("Hither");
                m.addActionListener(al);
                popup.add(m);
                m = new JMenuItem("Yon");
                m.addActionListener(al);
                popup.add(m);
                m = new JMenuItem("Afa");
                m.addActionListener(al);
                popup.add(m);
                popup.addSeparator();
                m = new JMenuItem("Stay here");
                m.addActionListener(al);
                popup.add(m);
                MouseAdapter pl = new MouseAdapter() {
                    void maybeShowPopup(MouseEvent e) {
                        if(e.isPopupTrigger()) {
                            popup.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        maybeShowPopup(e);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        maybeShowPopup(e);
                    }
                };
                frame.addMouseListener(pl);
                t.addMouseListener(pl);
            }
        });
    }

    static void learn_17() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello myui");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
//                frame.setLayout(new FlowLayout());
                String[] flavors = {
                        "Chocolate", "Strawberry", "Vanilla Fudge Swirl", "Mint Chip",
                        "Mocha Almond Fudge", "Rum Raisin", "Praline Cream", "Mud Pie"
                };
                JTextField t = new JTextField("No flavor", 30);
                JMenuBar mb1 = new JMenuBar();
                JMenu
                        f = new JMenu("File"),
                        m = new JMenu("Flavors"),
                        s = new JMenu("Safety");
                JCheckBoxMenuItem[] safety = {
                        new JCheckBoxMenuItem("Guard"),
                        new JCheckBoxMenuItem("Hide")
                };
                JMenuItem[] file = {new JMenuItem("Open")};
                JMenuBar mb2 = new JMenuBar();
                JMenu fooBar = new JMenu("fooBar");
                JMenuItem[] other = {
                        new JMenuItem("Foo", KeyEvent.VK_F),
                        new JMenuItem("Bar", KeyEvent.VK_A),
                        new JMenuItem("Baz")
                };
                JButton b = new JButton("Swap Menus");
//                safety[0].setActionCommand("Guard");
                safety[0].setMnemonic(KeyEvent.VK_G);
//                safety[1].setActionCommand("Hide");
                safety[1].setMnemonic(KeyEvent.VK_H);
                ItemListener cmi = new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        JCheckBoxMenuItem target = (JCheckBoxMenuItem)e.getSource();
                        String cmd = target.getActionCommand();
                        if(cmd.equals("Guard")) {
                            t.setText("Guard the Ice Cream! Guarding is " + target.getState());
                        } else if(cmd.equals("Hide")) {
                            t.setText("Hide the Ice Cream! Is it hidden? " + target.getState());
                        }
                    }
                };
                safety[0].addItemListener(cmi);
                safety[1].addItemListener(cmi);
                other[0].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        t.setText("Foo selected");
                    }
                });
                other[1].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        t.setText("bar selected");
                    }
                });
                other[2].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        t.setText("baz selected");
                    }
                });
                ActionListener fl = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JMenuItem target = (JMenuItem)e.getSource();
                        t.setText(target.getText());
                    }
                };
                int n = 0;
                for(String flavor : flavors) {
                    JMenuItem mi = new JMenuItem(flavor);
                    mi.addActionListener(fl);
                    m.add(mi);
                    if((n++ + 1) % 3 == 0) {
                        m.addSeparator();
                    }
                }
                for(JCheckBoxMenuItem sfty : safety) {
                    s.add(sfty);
                }
                s.setMnemonic(KeyEvent.VK_A);
                f.add(s);
                f.setMnemonic(KeyEvent.VK_F);
                ActionListener ml = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JMenuItem target = (JMenuItem)e.getSource();
                        String cmd = target.getActionCommand();
                        if(cmd.equals("Open")) {
                            String s = t.getText();
                            boolean chosen = false;
                            for(String flavor : flavors) {
                                if(s.equals(flavor)) {
                                    chosen = true;
                                }
                            }
                            if(!chosen) {
                                t.setText("Choosing a flavor first");
                            } else {
                                t.setText("Opening " + s + ". Mmm, mm!");
                            }
                        }
                    }
                };
                for(int i = 0; i < file.length; ++i) {
                    file[i].addActionListener(ml);
                    f.add(file[i]);
                }
                mb1.add(f);
                mb1.add(m);
                frame.setJMenuBar(mb1);
                t.setEditable(false);
                frame.add(t, BorderLayout.CENTER);
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JMenuBar m = frame.getJMenuBar();
                        frame.setJMenuBar(m == mb1 ? mb2 : mb1);
                        frame.validate();
                    }
                });
                b.setMnemonic(KeyEvent.VK_S);
                frame.add(b, BorderLayout.NORTH);
                for(JMenuItem oth : other) {
                    fooBar.add(oth);
                }
                fooBar.setMnemonic(KeyEvent.VK_B);
                mb2.add(fooBar);
            }
        });
    }

    static void learn_16() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello myui");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                JTextField t = new JTextField(15);
                ActionListener al = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        t.setText(((JMenuItem)e.getSource()).getText());
                    }
                };
                JMenu[] menus = {
                        new JMenu("Winken"), new JMenu("Blinken"), new JMenu("Nod")
                };
                JMenuItem[] items = {
                        new JMenuItem("Fee"), new JMenuItem("Fi"), new JMenuItem("Fo"), new JMenuItem("Zip"),
                        new JMenuItem("Zap"), new JMenuItem("Zot"), new JMenuItem("Olly"), new JMenuItem("Oxen"),
                        new JMenuItem("Free")
                };
                for(int i = 0; i < items.length; ++i) {
                    items[i].addActionListener(al);
                    menus[i % 3].add(items[i]);
                }
                JMenuBar mb = new JMenuBar();
                for(JMenu jm : menus) {
                    mb.add(jm);
                }
                frame.setJMenuBar(mb);
                frame.add(t);
            }
        });
    }

    static void learn_15() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello myui");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                JButton[] b = {
                        new JButton("Alert"), new JButton("Yes/No"), new JButton("Color"),
                        new JButton("Input"), new JButton("3 Vals")
                };
                JTextField txt = new JTextField(15);
                ActionListener al = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = ((JButton)e.getSource()).getText();
                        if(id.equals("Alert")) {
                            JOptionPane.showMessageDialog(null,
                                    "There's a bug on you!", "Hey!", JOptionPane.ERROR_MESSAGE);
                        } else if(id.equals("Yes/No")) {
                            JOptionPane.showConfirmDialog(null,
                                    "or not", "choose yes", JOptionPane.YES_NO_OPTION);
                        } else if(id.equals("Color")) {
                            Object[] options = {"Red", "Green"};
                            int sel = JOptionPane.showOptionDialog(null,
                                    "Choose a Color", "Warning", JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                            if(sel != JOptionPane.CLOSED_OPTION) {
                                txt.setText("Color selected: " + options[sel]);
                            }
                        } else if(id.equals("Input")) {
                            String val = JOptionPane.showInputDialog("How many fingers do you see");
                            txt.setText(val);
                        } else if(id.equals("3 Vals")) {
                            Object[] selections = {"First", "Second", "Three"};
                            Object val = JOptionPane.showInputDialog(null, "Choose one", "Input",
                                    JOptionPane.INFORMATION_MESSAGE, null, selections, selections[0]);
                            if(val != null) {
                                txt.setText(val.toString());
                            }
                        }
                    }
                };
                for(int i = 0; i < b.length; ++i) {
                    b[i].addActionListener(al);
                    frame.add(b[i]);
                }
                frame.add(txt);
            }
        });
    }

    static void learn_14() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
//                frame.setLayout(new FlowLayout());
                String[] flavors = {
                        "Chocolate", "Strawberry", "Vanilla Fudge Swirl",
                        "Mint Chip", "Mocha Almond Fudge", "Rum Raisin", "Praline Cream", "Mud Pie"
                };
                JTabbedPane tabs = new JTabbedPane();
                JTextField txt = new JTextField(20);
                int i = 0;
                for(String flavor : flavors) {
                    tabs.addTab(flavors[i], new JTextArea("Tabbed frame " + i++));
                }
                tabs.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        txt.setText("Tab selected: " + tabs.getSelectedIndex());
                    }
                });
                frame.add(BorderLayout.SOUTH, txt);
                frame.add(tabs);
            }
        });
    }

    static void learn_13() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                String[] flavors = {
                        "Chocolate", "Strawberry", "Vanilla Fudge Swirl",
                        "Mint Chip", "Mocha Almond Fudge", "Rum Raisin", "Praline Cream", "Mud Pie"
                };
                DefaultListModel lItems = new DefaultListModel();
                JList lst = new JList(lItems);
                JTextArea t = new JTextArea(flavors.length, 20);
                JButton b = new JButton("Add Items");
                class Inner {
                    int count = 0;
                    Inner() {
                        ActionListener bl = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(count < flavors.length) {
                                    lItems.add(0, flavors[count++]);
                                } else {
                                    b.setEnabled(false);
                                }
                            }
                        };
                        ListSelectionListener ll = new ListSelectionListener() {
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                if (e.getValueIsAdjusting()) {
                                    return;
                                }
                                t.setText("");
                                for(Object item : lst.getSelectedValuesList()) {
                                    t.append(item + "\n");
                                }
                            }
                        };
                        t.setEditable(false);
                        Border brd = BorderFactory.createMatteBorder(1, 1, 2, 2, Color.BLACK);
                        lst.setBorder(brd);
                        t.setBorder(brd);
                        for(int i = 0; i < 4; ++i) {
                            lItems.addElement(flavors[count++]);
                        }
                        frame.add(new JScrollPane(t));
                        frame.add(new JScrollPane(lst));
                        frame.add(b);
                        lst.addListSelectionListener(ll);
                        b.addActionListener(bl);
                    }
                }
                new Inner();
            }
        });
    }

    static void learn_12() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                String[] descs = {
                        "Ebullient", "Obtuse", "Recalcitrant", "Brilliant", "Somnescent", "Timorous", "Florid", "Putrescent"
                };
                JTextField t = new JTextField(15);
                JComboBox c = new JComboBox();
                JButton b = new JButton("add Items");
                class Inner {
                    int count = 0;
                    Inner() {
                        for(int i = 0; i < 4; ++i) {
                            c.addItem(descs[count++]);
                        }
                        t.setEditable(false);
                        b.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(count < descs.length) {
                                    c.addItem(descs[count++]);
                                }
                            }
                        });
                        c.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                t.setText("index: " + c.getSelectedIndex() + " " + ((JComboBox)e.getSource()).getSelectedItem());
                            }
                        });
                        frame.add(t);
                        frame.add(c);
                        frame.add(b);
                    }
                }
                new Inner();
            }
        });
    }

    static void learn_11() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                JTextField t = new JTextField(15);
                ButtonGroup g = new ButtonGroup();
                JRadioButton
                        rb1 = new JRadioButton("one", false),
                        rb2 = new JRadioButton("two", false),
                        rb3 = new JRadioButton("three", false);
                ActionListener al = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        t.setText("raiDio button " + ((JRadioButton)e.getSource()).getText());
                    }
                };
                rb1.addActionListener(al);
                rb2.addActionListener(al);
                rb3.addActionListener(al);
                g.add(rb1);
                g.add(rb2);
                g.add(rb3);
                t.setEditable(false);
                frame.add(t);
                frame.add(rb1);
                frame.add(rb2);
                frame.add(rb3);
            }
        });
    }

    static void learn_10() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                JTextArea t = new JTextArea(6, 15);
                JCheckBox
                        cb1 = new JCheckBox("checkbox 1"),
                        cb2 = new JCheckBox("checkbox 2"),
                        cb3 = new JCheckBox("checkbox 3");
                class Inner {
                    void trace(String b, JCheckBox cbb) {
                        if(cbb.isSelected()) {
                            t.append("box " + b + " Set\n");
                        } else {
                            t.append("box " + b + " cleared\n");
                        }
                    }
                    Inner() {
                        cb1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                trace("1", cb1);
                            }
                        });
                        cb2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                trace("2", cb2);
                            }
                        });
                        cb3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                trace("3", cb3);
                            }
                        });
                        frame.add(new JScrollPane(t));
                        frame.add(cb1);
                        frame.add(cb2);
                        frame.add(cb3);
                    }
                }
                new Inner();
            }
        });
    }

    static void learn_9() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                JButton b = new JButton("add Text");
                JTextPane tp = new JTextPane();
                Random r = new Random();
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0; i < 10; ++i) {
                            tp.setText(tp.getText() + r.nextDouble() + "\n");
                        }
                    }
                });
                frame.add(new JScrollPane(tp));
                frame.add(b, BorderLayout.SOUTH);
            }
        });
    }

    static void learn_8() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new GridLayout(2, 4));
                class Inner {
                    JPanel showBorder(Border b) {
                        JPanel jp = new JPanel();
                        jp.setLayout(new BorderLayout());
                        String nm = b.getClass().toString();
                        nm = nm.substring(nm.lastIndexOf(".") + 1);
                        jp.add(new JLabel(nm, JLabel.CENTER), BorderLayout.CENTER);
                        jp.setBorder(b);
                        return jp;
                    }
                    Inner() {
                        frame.add(showBorder(new TitledBorder("title")));
                        frame.add(showBorder(new EtchedBorder()));
                        frame.add(showBorder(new LineBorder(Color.BLUE)));
                        frame.add(showBorder(new MatteBorder(50, 15, 30, 10, Color.GREEN)));
                        frame.add(showBorder(new BevelBorder(BevelBorder.RAISED)));
                        frame.add(showBorder(new SoftBevelBorder(BevelBorder.LOWERED)));
                        frame.add(showBorder(new CompoundBorder(new EtchedBorder(), new LineBorder(Color.RED))));
                    }
                }
                new Inner();
            }
        });
    }

    static void learn_7() {
        class Inner {
            String s = "";
            Inner() {
                class UpperCaseDocument extends PlainDocument {
                    boolean upperCase = true;

                    void setUpperCase(boolean flag) {
                        upperCase = flag;
                    }

                    public void insertString(int offset, String str, AttributeSet attSet) throws BadLocationException {
                        if (upperCase) {
                            str = str.toUpperCase();
                        }
                        super.insertString(offset, str, attSet);
                    }
                }

                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                JButton
                        b1 = new JButton("get text"),
                        b2 = new JButton("set text");
                JTextField
                        t1 = new JTextField(30),
                        t2 = new JTextField(30),
                        t3 = new JTextField(30);
                UpperCaseDocument ucd = new UpperCaseDocument();
                t1.setDocument(ucd);
                ucd.addDocumentListener(new DocumentListener() {
                    int count = 0;
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        t2.setText(t1.getText());
                        t3.setText("insert count: " + ++count);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        t2.setText(t1.getText());
                        t3.setText("remove count: " + ++count);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        t3.setText("change text");
                    }
                });
                b1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(t1.getSelectedText() == null) {
                            s = t1.getText();
                        } else {
                            s = t1.getSelectedText();
                        }
                        t1.setEditable(true);
                    }
                });
                b2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ucd.setUpperCase(false);
                        t1.setText("insert by Button2: " + s);
                        ucd.setUpperCase(true);
                        t1.setEditable(false);
                    }
                });
                t1.addActionListener(new ActionListener() {
                    int count = 0;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        t3.setText("t1 action event: " + count++);
                    }
                });
                frame.add(b1);
                frame.add(b2);
                frame.add(t1);
                frame.add(t2);
                frame.add(t3);
            }
        }
        new Inner();
    }

    static void learn_6() {
        class Inner {
            boolean mad = false;
            Inner() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame("hello fmy swing");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setSize(800, 600);
                        frame.setVisible(true);
                        frame.setLayout(new FlowLayout());
                        Icon[] faces = new Icon[]{
                                new ImageIcon(getClass().getResource("0.png")),
                                new ImageIcon(getClass().getResource("1.png")),
                                new ImageIcon(getClass().getResource("2.png")),
                                new ImageIcon(getClass().getResource("3.png")),
                                new ImageIcon(getClass().getResource("4.png"))
                        };

                        JButton jb = new JButton("Jbutton", faces[3]);
                        JButton jb2 = new JButton("disable");
                        jb.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (Inner.this.mad) {
                                    jb.setIcon(faces[3]);
                                    Inner.this.mad = false;
                                } else {
                                    jb.setIcon(faces[0]);
                                    mad = true;
                                }
                                jb.setVerticalAlignment(JButton.TOP);
                                jb.setHorizontalAlignment(JButton.LEFT);
                            }
                        });
                        jb.setRolloverEnabled(true);
                        jb.setRolloverIcon(faces[1]);
                        jb.setPressedIcon(faces[2]);
                        jb.setDisabledIcon(faces[4]);
                        jb.setToolTipText("yow this button");
                        frame.add(jb);
                        jb2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(jb.isEnabled()) {
                                    jb.setEnabled(false);
                                    jb2.setText("enable");
                                } else {
                                    jb.setEnabled(true);
                                    jb2.setText("disable");
                                }
                            }
                        });
                        frame.add(jb2);
                    }
                });
            }
        }
        new Inner();
    }

    static void learn_5() {
        SwingUtilities.invokeLater(new Runnable() {
            JPanel makeBPanel(Class<? extends AbstractButton> kind, String[] ids) {
                ButtonGroup bg = new ButtonGroup();
                JPanel jp = new JPanel();
                String title = kind.getName();
                title = title.substring(title.lastIndexOf('.') + 1);
                jp.setBorder(new TitledBorder(title));
                for(String id : ids) {
                    AbstractButton ab = new JButton("failed");
                    try {
                        Constructor ctor = kind.getConstructor(String.class);
                        ab = (AbstractButton)ctor.newInstance(id);
                    } catch (Exception ex) {
                        System.err.println("can't create " + kind);
                    }
                    bg.add(ab);
                    jp.add(ab);
                }
                return jp;
            }

            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                String[] ids = {
                        "June", "Ward", "Beaver", "Wally", "Eddie", "Lumpy"
                };
                frame.add(makeBPanel(JButton.class, ids));
                frame.add(makeBPanel(JToggleButton.class, ids));
                frame.add(makeBPanel(JCheckBox.class, ids));
                frame.add(makeBPanel(JRadioButton.class, ids));
            }
        });
    }

    static void learn_4() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout());
                frame.add(new JButton("jbutton"));
                frame.add(new JToggleButton("jtoggle button"));
                frame.add(new JCheckBox("jcheckbox"));
                frame.add(new JRadioButton("jradio button"));
                JPanel jp = new JPanel();
                jp.setBorder(new TitledBorder("direction"));
                jp.add(new BasicArrowButton(BasicArrowButton.NORTH));
                jp.add(new BasicArrowButton(BasicArrowButton.SOUTH));
                jp.add(new BasicArrowButton(BasicArrowButton.EAST));
                jp.add(new BasicArrowButton(BasicArrowButton.WEST));
                frame.add(jp);

            }
        });
    }

    static void learn_3() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
                HashMap<String, JTextField> h = new HashMap<>();
                String[] event = {
                        "focusGained", "focusLost", "keyPressed", "keyReleased", "keyTyped", "mouseClicked", "mouseEntered",
                        "mouseExited", "mousePressed", "mouseReleased", "mouseDragged", "mouseMoved"
                };
                class MyButton extends JButton {
                    void report(String field, String msg) {
                        h.get(field).setText(msg);
                    }
                    FocusListener fl = new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            report("focusGained", e.paramString());
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            report("focusLost", e.paramString());
                        }
                    };
                    KeyListener kl = new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            report("keyTyped", e.paramString());
                        }

                        @Override
                        public void keyPressed(KeyEvent e) {
                            report("keyPressed", e.paramString());
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {
                            report("keyReleased", e.paramString());
                        }
                    };
                    MouseListener ml = new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            report("mouseClicked", e.paramString());
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            report("mousePressed", e.paramString());
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            report("mouseReleased", e.paramString());
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            report("mouseEntered", e.paramString());
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            report("mouseExited", e.paramString());
                        }
                    };
                    MouseMotionListener mml = new MouseMotionListener() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            report("mouseDragged", e.paramString());
                        }

                        @Override
                        public void mouseMoved(MouseEvent e) {
                            report("mouseMoved", e.paramString());
                        }
                    };
                    MyButton(Color color, String label) {
                        super(label);
                        setBackground(color);
                        addFocusListener(fl);
                        addKeyListener(kl);
                        addMouseListener(ml);
                        addMouseMotionListener(mml);
                    }
                }
                MyButton b1 = new MyButton(Color.BLUE, "test1");
                MyButton b2 = new MyButton(Color.RED, "test2");
                frame.setLayout(new GridLayout(event.length + 1, 2));
                for(String evt : event) {
                    JTextField t = new JTextField();
                    t.setEditable(false);
                    frame.add(new JLabel(evt, JLabel.RIGHT));
                    frame.add(t);
                    h.put(evt, t);
                }
                frame.add(b1);
                frame.add(b2);
            }
        });
    }

    static void learn_2() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
//                frame.setLayout(new GridLayout(7, 3));
                frame.setVisible(true);
                JTextField name = new JTextField(25);
                JTextArea result = new JTextArea(40, 65);
                Pattern addListener = Pattern.compile("(add\\w+?Listener\\(.*?\\))");
                Pattern qualifier = Pattern.compile("\\w+\\.");
                name.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nm = name.getText().trim();
                        if(nm.length() == 0) {
                            result.setText("no match");
                            return;
                        }
                        Class<?> kind;
                        try {
                            kind = Class.forName("javax.swing." + nm);
                        } catch (ClassNotFoundException ex) {
                            result.setText("no match 2");
                            return;
                        }
                        Method[] methods = kind.getMethods();
                        result.setText("");
                        for(Method m : methods) {
                            Matcher matcher = addListener.matcher(m.toString());
                            if(matcher.find()) {
                                result.append(qualifier.matcher(matcher.group(1)).replaceAll("") + "\n");
//                                Matcher matcher2 = qualifier.matcher(matcher.group(1));
//                                matcher2.find();
//                                result.append(matcher.group(1) + "\n");
//                                result.append(matcher2.group() + "\n");
                            }
                        }
                    }
                });
                JPanel top = new JPanel();
                top.add(new JLabel("swing class name (press enter):"));
                top.add(name);
                frame.add(BorderLayout.NORTH, top);
                frame.add(new JScrollPane(result));
            }
        });
    }

    static void learn_1() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                JFrame frame = new JFrame("hello fmy swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLayout(new GridLayout(7, 3));
                frame.setVisible(true);

//                frame.add(BorderLayout.NORTH, new JButton("north"));
//                frame.add(BorderLayout.SOUTH, new JButton("south"));
//                frame.add(BorderLayout.EAST, new JButton("e"));
//                frame.add(BorderLayout.WEST, new JButton("w"));
//                frame.add(BorderLayout.CENTER, new JScrollPane(new JButton("center")));


                for(int i = 0; i < 50; ++i) {
                    frame.add(new JButton("Button " + i));
                }
                JLabel label = new JLabel("a lable");
                frame.add(label);
                JButton b1 = new JButton("button 1");
                JButton b2 = new JButton("button 2");
                JTextField txt = new JTextField(8);
                class ButtonListener implements ActionListener {
                    public void actionPerformed(ActionEvent e) {
                        String name = ((JButton)e.getSource()).getText();
                        txt.setText(name);
                    }
                }
                b1.addActionListener(new ButtonListener());
                b2.addActionListener(new ButtonListener());
                frame.add(b1);
                frame.add(b2);
                frame.add(txt);
                frame.add(new JScrollPane(new JTextArea(20, 40)));
                frame.add(new JTextArea(10, 30));
                label.setText("hey, this is different");
            }
        });
    }
}