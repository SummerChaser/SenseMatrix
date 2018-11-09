
import java.io.*;
import java.util.*;

public class Probability {
    static HashMap<String, String> trainSet_true = new HashMap<String, String>();
    static HashMap<String, String> trainSet_false = new HashMap<String, String>();
    static HashMap<String, String> testSet = new HashMap<String, String>();
    static float[][] trueMatrix = null;
    static float[][] falseMatrix = null;
    static String aa = "ATGCRNDQEHILKMFPSWYV";// 氨基酸序列
    static int length;
    static int T;

    public static void main(String[] args) throws IOException {
        length = readTrains();
        buildMatrixTrue() ;
        buildMatrixFalse() ;
        printMatrix();
        check();
        
        
        
        

    }

    static int readTrains() throws IOException {
        // 输入训练集
        InputStream f_true = new FileInputStream("/Users/summerchaser/Desktop/1.txt");
        InputStream f_false = new FileInputStream("/Users/summerchaser/Desktop/2.txt");
        InputStream f_test_true = new FileInputStream("/Users/summerchaser/Desktop/3.txt");
        InputStream f_test_false = new FileInputStream("/Users/summerchaser/Desktop/4.txt");
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
        System.out.println("输入的训练集为 ： ");
        for (Map.Entry<String, String> item : trainSet_true.entrySet()) {
            System.out.println(item.getKey() + ":" + item.getValue());
        }
        for (Map.Entry<String, String> item : trainSet_false.entrySet()) {
            System.out.println(item.getKey() + ":" + item.getValue());
        }
        System.out.println("输入的测试集为 ： ");
        for (Map.Entry<String, String> item : testSet.entrySet()) {
            System.out.println(item.getKey() + ":" + item.getValue());
        }
        //System.out.println("lenth:"+length);
        return length;

    }

    static void buildMatrixTrue() {
        trueMatrix = new float[20][length];
     //   System.out.println(trainSet_true.size());
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
        falseMatrix  = new float[20][length];
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
        System.out.println("概率矩阵M为 ：");
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < length; j++) {
                 System.out.print(trueMatrix[i][j]+" ");
            }
             System.out.println("");
        }
        System.out.println("概率矩阵M'为 ：");
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < length; j++) {
                 System.out.print(falseMatrix[i][j]+" ");
            }
             System.out.println("");
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
            
            p_true *=trueMatrix[row][i];
            p_false *=falseMatrix[row][i];
        }
        lr = Math.log(p_true/p_false);
        System.out.println("item :"+str +" " +clas);
        System.out.println("功能位点概率 :"+p_true);
        System.out.println("非功能位点概率:"+p_false);
        System.out.println("lr:"+lr);
        if (lr > T) {
            result = "yes";
            System.out.println("判定为正");
        }else {
            result = "no";
            System.out.println("判定为负");
        }
        if (result.equals(clas)) {
            check = true;
            System.out.println("结果正确");
        }else {
            check = false;
            System.out.println("结果错误");
        }
        
        System.out.println("------------------------------------------------");
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
        System.out.println("模型检测正确率为 ："+rank);
    }
}
