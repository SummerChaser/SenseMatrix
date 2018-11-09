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

public class SenceGUI extends JFrame {

    private JPanel contentPane;
    static JTextField ad1;
    private JTextField ad2;
    private JTextField ad3;
    private JTextField ad4;
    
    static HashMap<String, String> trainSet = new HashMap<String, String>();
    static HashMap<String, String> testSet = new HashMap<String, String>();
    static int[][] senceMatrix = null;
    static String aa = "ATGCRNDQEHILKMFPSWYV-";// 氨基酸序列
    static int Tmax = 4,Tmin = -4;
    static float K = (float) 0.81;
    
    static JTextArea out;
    private JTextField t1;
    private JTextField t2;
    private JTextField textField_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SenceGUI frame = new SenceGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * GUI部分
     */
    public SenceGUI() {
        setTitle("感知矩阵判定功能序列");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 735, 683);
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
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tmax = Integer.valueOf(t1.getText()) ;
                Tmax = Integer.valueOf(t2.getText());
                int length = 0;
                try {
                    length = readTrains();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                senceMatrix = createSenseMatrix(length);
                buildMatrix();
                printMatrix(length);
                test();
                try {
                    // 请在这修改文件输出路径
                    File fo = new File("/Users/summerchaser/Desktop/sense.txt");
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
        JLabel lblNewLabel = new JLabel("阈值T：");
        lblNewLabel.setToolTipText("");
        t1 = new JTextField();
        t1.setText("4");
        t1.setColumns(10);
        JLabel lblt = new JLabel("阈值T '：");
        lblt.setToolTipText("");
        t2 = new JTextField();
        t2.setText("-4");
        t2.setColumns(10);
        
        JLabel lblk = new JLabel("稳定性k :");
        
        textField_2 = new JTextField();
        textField_2.setText("0.9");
        textField_2.setColumns(10);
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(24)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(label_5)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_contentPane.createSequentialGroup()
                                    .addComponent(label_1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(ad2, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE))
                                .addGroup(gl_contentPane.createSequentialGroup()
                                    .addComponent(label)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(ad1, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
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
                            .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                                .addGroup(gl_contentPane.createSequentialGroup()
                                    .addComponent(lblNewLabel)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(t1, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                    .addGap(27)
                                    .addComponent(lblt)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(t2, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                    .addGap(20)
                                    .addComponent(lblk)
                                    .addGap(18))
                                .addGroup(gl_contentPane.createSequentialGroup()
                                    .addComponent(label_4)
                                    .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button)
                                    .addGap(31)
                                    .addComponent(btnDna)
                                    .addGap(27)))
                            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                .addComponent(btnRna)
                                .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 668, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
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
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblNewLabel)
                            .addComponent(t1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblt)
                            .addComponent(t2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblk)
                            .addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(26)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnRna)
                        .addComponent(btnDna)
                        .addComponent(button)
                        .addComponent(label_4))
                    .addGap(30)
                    .addComponent(label_5)
                    .addGap(26)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(28, Short.MAX_VALUE))
        );
        
        out = new JTextArea();
        scrollPane.setColumnHeaderView(out);
        contentPane.setLayout(gl_contentPane);
    }
    static int readTrains() throws IOException {
        // 输入训练集
        InputStream f_true = new FileInputStream("/Users/summerchaser/Desktop/正训练集.txt");
        InputStream f_false = new FileInputStream("/Users/summerchaser/Desktop/反训练集.txt");
        InputStream f_test_true = new FileInputStream("/Users/summerchaser/Desktop/正测试集.txt");
        InputStream f_test_false = new FileInputStream("/Users/summerchaser/Desktop/反训练集.txt");
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
                trainSet.put(str.trim().toUpperCase(), "yes");
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
                trainSet.put(str.trim().toUpperCase(), "no");// 序列去除首尾空格
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
        out.append("\n"+"\n输入的训练集为 ： ");
        for (Map.Entry<String, String> item : trainSet.entrySet()) {
            out.append("\n"+item.getKey() + ":" + item.getValue());
        }
        out.append("\n"+"\n输入的测试集为 ： ");
        for (Map.Entry<String, String> item : testSet.entrySet()) {
            out.append("\n"+item.getKey() + ":" + item.getValue());
        }
        return length;

    }

    static int[][] createSenseMatrix(int length) {
        int[][] senceMatrix = new int[21][length];
        // test
        return senceMatrix;
    }

    static void buildMatrix() {
        boolean stable = false;
        while (!stable) { // 若不稳定，重复该过程
            for (Map.Entry<String, String> item : trainSet.entrySet()) {
               
                if (item.getValue().equals("yes")) {
                //    out.append("\n"+"---"+item.getKey());
                    dealStr(item.getKey(), "yes");
                } else if (item.getValue().equals("no")) {
                //    out.append("\n"+"==="+item.getKey());
                    dealStr(item.getKey(), "no");
                }
            }
            stable = isStable(K);
        }
    }
    static void printMatrix(int length) {
        out.append("\n"+"\n感知矩阵为 ：\n");
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < length; j++) {
                 out.append(senceMatrix[i][j]+" ");
            }
             out.append("\n"+"");
        }
    }

    static void dealStr(String str, String mark) {
        int i = 0,row=0;
        int W = 0;
        for (i=0;i<str.length();i++) {
            row = aa.indexOf(str.charAt(i));
            W+=senceMatrix[row][i];
        }
        //out.append("\n"+"WL:"+W);
        if (mark.equals("yes") && !(W>=Tmax)){
            //out.append("\n"+str + " w+  "+W+"失败");
            for (i = 0; i < str.length(); i++) {
                row = aa.indexOf(str.charAt(i));
                senceMatrix[row][i]++;
       //         out.append("\n"+"0000"+row+i+senceMatrix[row][i]);
            }
        } else if (mark.equals("no") && !(W<=Tmin)) {
            //out.append("\n"+str + " w-  "+W+"失败");
            for (i = 0; i < str.length(); i++) {
                row = aa.indexOf(str.charAt(i));
                senceMatrix[row][i]--;
    //            out.append("\n"+"9999"+row+i+senceMatrix[row][i]);
            }

        }

    }
    static boolean isStable(float K) {
        int mistake = 0;
        float total = trainSet.size();
        String result = null;
        for (Map.Entry<String, String> item : trainSet.entrySet()) {
            result = identifyer(item.getKey());
            if (!item.getValue().equals(result)) {
                mistake++;
            }
        }
        out.append("\n"+"\n模型稳定率" + (total - mistake)/total);
        if ((total - mistake)/total >= K) {
            return true;
        }
        return false;
        
    }
    
    static String identifyer(String str) { //分类器 传入阈值
        int W = 0;
        int i =0 ,row = 0;
        for (i=0;i<str.length();i++) {
            row = aa.indexOf(str.charAt(i));
            W+=senceMatrix[row][i];
        }
        //out.append("\n"+"str :"+str+ "w:"+W);
        
        if (W >= Tmax) {
            return "yes";
        }else if (W < Tmin) {
            return "no";
        }
        return "undifined";
    }
    
    static void test() { //分类器 传入阈值
        String result,clas;
        int correct = 0 ;
        out.append("\n"+"\n测试结果 :");
        out.append("\n"+"----------------------------------------------");
        
        for (Map.Entry<String, String> item : testSet.entrySet()) {
            clas = item.getValue();
            result = identifyer(item.getKey());
            out.append("\n"+"item :"+item.getKey()+"\n实际分类 ："+clas+"\n测试结果 ："+result);
            
            if (!item.getValue().equals(result)) {
                out.append("\n"+"分类错误");
            }else {
                correct++;
                out.append("\n"+"分类正确");
            }
            out.append("\n"+"----------------------------------------------");
        }
        float total = testSet.size();
        float correctRare = correct/total;
        out.append("\n"+"测试模型正确率为 ： "+correctRare);
        
       
    }
}
