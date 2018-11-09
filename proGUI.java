import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class proGUI extends JFrame {

    private JPanel contentPane;
    private JTextField ad1;
    private JTextField ad2;
    private JTextField ad3;
    private JTextField ad4;
    static HashMap<String, String> trainSet_true = new HashMap<String, String>();
    static HashMap<String, String> trainSet_false = new HashMap<String, String>();
    static HashMap<String, String> testSet = new HashMap<String, String>();
    static double[][] trueMatrix = null;
    static double[][] falseMatrix = null;
    static String aa = "ATGCRNDQEHILKMFPSWYV-";// 氨基酸序列
    static int length;
    static int T ;
    
    static JTextArea out;
    private JTextField tt;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    proGUI frame = new proGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public proGUI()  throws IOException  {
        setTitle("概率矩阵判定功能位点");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 733, 702);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JLabel label = new JLabel("正训练集 ：");
        
        JLabel label_1 = new JLabel("负训练集 ：");
        
        JLabel label_2 = new JLabel("正测试集 ：");
        
        ad1 = new JTextField();
        ad1.setText("/Users/summerchaser/Desktop/正训练集.txt");
        ad1.setColumns(10);
        
        ad2 = new JTextField();
        ad2.setText("/Users/summerchaser/Desktop/反训练集.txt");
        ad2.setColumns(10);
        
        JLabel label_3 = new JLabel("负测试集 ：");
        
        ad3 = new JTextField();
        ad3.setText("/Users/summerchaser/Desktop/正测试集.txt");
        ad3.setColumns(10);
        
        ad4 = new JTextField();
        ad4.setText("/Users/summerchaser/Desktop/负测试集.txt");
        ad4.setColumns(10);
        
        JButton button = new JButton("氨基酸序列");
        button.addMouseListener(new MouseAdapter()  {
            @Override
            public void mouseClicked(MouseEvent e)  {
                out.setText("");
                try {
                    length = readTrains();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                buildMatrixTrue() ;
                buildMatrixFalse() ;
                printMatrix();
                check();
                try {
                    // 请在这修改文件输出路径
                    File fo = new File("/Users/summerchaser/Desktop/pro.txt");
                    FileWriter fileWriter = new FileWriter(fo);
                    fileWriter.write(out.getText());
                    fileWriter.close(); // 关闭数据流
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                
                
            }
        });
        
        JButton btnDna = new JButton("DNA序列");
        btnDna.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        JButton btnRna = new JButton("RNA序列");
        
        JLabel label_4 = new JLabel("模式选择 ：");
        
        JLabel label_5 = new JLabel("运行结果 ：");
        
        JScrollPane scrollPane = new JScrollPane();
        
        JLabel lblt = new JLabel("阈值T ：");
        
        tt = new JTextField();
        tt.setText("0");
        tt.setColumns(10);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(24)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(label_5)
                            .addContainerGap())
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 662, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                    .addGroup(gl_contentPane.createSequentialGroup()
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                            .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(label)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(ad1, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                                    .addComponent(label_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblt))
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                    .addComponent(tt, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(ad2, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                            .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(label_2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(ad3, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(label_3, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(ad4))))
                                    .addGroup(gl_contentPane.createSequentialGroup()
                                        .addPreferredGap(ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                                        .addComponent(label_4)
                                        .addGap(18)
                                        .addComponent(button)
                                        .addGap(18)
                                        .addComponent(btnDna)
                                        .addGap(18)
                                        .addComponent(btnRna)
                                        .addGap(17)))
                                .addGap(15)))))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(22)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                            .addComponent(ad1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_2)
                            .addComponent(ad3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(label))
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_1)
                        .addComponent(ad2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_3)
                        .addComponent(ad4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(tt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblt)
                        .addComponent(label_4)
                        .addComponent(button)
                        .addComponent(btnDna)
                        .addComponent(btnRna))
                    .addGap(20)
                    .addComponent(label_5)
                    .addGap(18)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(25, Short.MAX_VALUE))
        );
        
        out = new JTextArea();
        scrollPane.setRowHeaderView(out);
        contentPane.setLayout(gl_contentPane);
    }
    static int readTrains() throws IOException {
        // 输入训练集
        InputStream f_true = new FileInputStream("/Users/summerchaser/Desktop/正训练集.txt");
        InputStream f_false = new FileInputStream("/Users/summerchaser/Desktop/反训练集.txt");
        InputStream f_test_true = new FileInputStream("/Users/summerchaser/Desktop/正测试集.txt");
        InputStream f_test_false = new FileInputStream("/Users/summerchaser/Desktop/负测试集.txt");
        BufferedReader reader_true = new BufferedReader(new InputStreamReader(f_true));
        BufferedReader reader_false = new BufferedReader(new InputStreamReader(f_false));
        BufferedReader reader_test_true = new BufferedReader(new InputStreamReader(f_test_true));
        BufferedReader reader_test_false = new BufferedReader(new InputStreamReader(f_test_false));
        String str = null;
        int length = 0;
        while (true) {
            str = reader_true.readLine();
            if (str != null) {
                if (str.equals("")) {
                    continue;
                }
                length = str.trim().length();
                trainSet_true.put(str.trim().toUpperCase(), "yes");
            } else {
                break;
            }
        }

        while (true) {
            str = reader_false.readLine();
            if (str != null) {
                if (str.equals("")) {
                    continue;
                }
                trainSet_false.put(str.trim().toUpperCase(), "no");// 序列去除首尾空格
            } else {
                break;
            }
        }

        while (true) {
            str = reader_test_true.readLine();
            if (str != null) {
                if (str.equals("")) {
                    continue;
                }
                testSet.put(str.trim().toUpperCase(), "yes");// 序列去除首尾空格
            } else {
                break;
            }
        }

        while (true) {
            str = reader_test_false.readLine();
            if (str != null) {
                if (str.equals("")) {
                    continue;
                }
                testSet.put(str.trim().toUpperCase(), "no");// 序列去除首尾空格
            } else {
                break;
            }
        }
        // test
        out.append("\n"+"输入的训练集为 ： ");
        for (Map.Entry<String, String> item : trainSet_true.entrySet()) {
            out.append("\n"+item.getKey() + ":" + item.getValue());
        }
        for (Map.Entry<String, String> item : trainSet_false.entrySet()) {
            out.append("\n"+item.getKey() + ":" + item.getValue());
        }
        out.append("\n"+"输入的测试集为 ： ");
        for (Map.Entry<String, String> item : testSet.entrySet()) {
            out.append("\n"+item.getKey() + ":" + item.getValue());
        }
        //out.append("\n"+"lenth:"+length);
        return length;

    }

    static void buildMatrixTrue() {
        trueMatrix = new double[21][length];
     //   out.append("\n"+trainSet_true.size());
        for (int i = 0;i<trainSet_true.size()+1;i++) {
            
            HashMap<Character, Integer> col_i = new HashMap<Character, Integer> ();
            for (Map.Entry<String, String> item : trainSet_true.entrySet()) {
                String str = item.getKey();
                char a = str.charAt(i);
                if (col_i.containsKey(a)) {
                    col_i.put(a, col_i.get(a)+1);
                }else {
                    col_i.put(a,1);
                }
            }
            for (Map.Entry<Character, Integer> item : col_i.entrySet()) {
                int row = aa.indexOf(item.getKey()); 
                trueMatrix[row][i] = item.getValue() / ( (float) trainSet_true.size() ) ;
            }
        }

    }

    static void buildMatrixFalse() {
        falseMatrix  = new double[21][length];
        for (int i = 0;i<trainSet_false.size()+1;i++) {
            HashMap<Character, Integer> col_i = new HashMap<Character, Integer> ();
            for (Map.Entry<String, String> item : trainSet_false.entrySet()) {
                String str = item.getKey();
                char a = str.charAt(i);
                if (col_i.containsKey(a)) {
                    col_i.put(a, col_i.get(a)+1);
                }else {
                    col_i.put(a,1);
                }
            }
            for (Map.Entry<Character, Integer> item : col_i.entrySet()) {
                int row = aa.indexOf(item.getKey()); 
                falseMatrix[row][i] = item.getValue() / ( (float) trainSet_false.size() ) ;
            }
        }
    }
    static void printMatrix() {
        out.append("\n"+"概率矩阵M为 ：\n");
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < length; j++) {
                out.append(trueMatrix[i][j]+" ");
            }
             out.append("\n"+"");
        }
        out.append("\n"+"概率矩阵M'为 ：\n");
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < length; j++) {
                out.append(falseMatrix[i][j]+" ");
            }
             out.append("\n"+"");
        }
    }
    
    static boolean LR(Map.Entry<String, String> item ) {
        boolean check;
        String str = item.getKey();
        String clas = item.getValue();
        String result;
        int i = 0,row=0;
        double p_true = 1;
        double p_false = 1;
        double lr = 0;
        for (i=0;i<str.length();i++) {
            row = aa.indexOf(str.charAt(i));
            System.out.println(trueMatrix[row][i]);
            if (trueMatrix[row][i] == 0) {
                trueMatrix[row][i] = (double) 0.0001;
                System.out.println("=0"+trueMatrix[row][i]);
            }
            if (falseMatrix[row][i] == 0) {
                falseMatrix[row][i] = (double) 0.0001;
            }
            p_true *=trueMatrix[row][i];
            p_false *=falseMatrix[row][i];
        }
        lr = Math.log(p_true/p_false);
        out.append("\n"+"item :"+str +" " +clas);
        out.append("\n"+"功能位点概率 :"+p_true);
        out.append("\n"+"非功能位点概率:"+p_false);
        out.append("\n"+"lr:"+lr);
        if (lr > T) {
            result = "yes";
            out.append("\n"+"判定为正");
        }else {
            result = "no";
            out.append("\n"+"判定为负");
        }
        if (result.equals(clas)) {
            check = true;
            out.append("\n"+"结果正确");
        }else {
            check = false;
            out.append("\n"+"结果错误");
        }
        
        out.append("\n"+"------------------------------------------------");
        return check;
    }
    
    static void check() {
        double right = 0;
        for (Map.Entry<String, String> item : testSet.entrySet()) {
            if(LR(item)) { 
                right++;
            };
        }
        double rank = right/testSet.size();
        out.append("\n"+"模型检测正确率为 ："+rank);
    }
}
