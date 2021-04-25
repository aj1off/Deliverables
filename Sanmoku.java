import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.util.List;
import java.util.*;

public class Sanmoku extends JFrame implements ActionListener{

    // フィールド変数
    JLabel label, label1;
    JButton bt[];
    int[] mas = new int[9]; // index: 0-8マス, value: マスの状態(0=空、1=○，2=×)
    int nanTe=0, p1WinsCnt=0, p2WinsCnt=0;
    boolean endFlg = false;
    int gmcnt=1;
    int cnt1=0;
    int cnt2=0;
    int cnt3=0;
    int cnt4=0;
    int cnt5=0;
    int cnt6=0;
    int cnt7=0;
    int cnt8=0;


    // メイン関数
    public static void main(String[] args){
        Sanmoku sanmoku = new Sanmoku("三目並べ");

        sanmoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sanmoku.setVisible(true);
    }

    Sanmoku(String title){
        setTitle(title);
        setSize(800, 800);

        label = new JLabel("");
        label.setHorizontalAlignment(JLabel.CENTER);

        JButton btn2 = new JButton("RESET");
        btn2.addActionListener(this);
        btn2.setActionCommand("Reset");

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3,3));

        bt = new JButton[9]; 
        for (int i=0; i<9; i++) {
            bt[i] = new JButton();
            bt[i].addActionListener(this);
            bt[i].setActionCommand(Integer.toString(i));
            p.add(bt[i]);
        }

        JPanel p2 = new JPanel();
        label1 = new JLabel("GAME:"+Integer.toString(gmcnt));
        p2.add(label1);
        p2.add(btn2);

        getContentPane().add(label, BorderLayout.PAGE_START);
        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(p2, BorderLayout.PAGE_END);
    }

    // 勝利判定
    public int chkEnd() {
        int c=0;
        if ((mas[0] == mas[1]) && (mas[1] == mas[2]) && (mas[0] > 0)) {
            c = 1;
        }
        else if ((mas[3] == mas[4]) && (mas[4] == mas[5]) && (mas[3] > 0)) {
            c = 2;
        }
        else if ((mas[6] == mas[7]) && (mas[7] == mas[8]) && (mas[6] > 0)) {
            c = 3;
        }
        else if ((mas[0] == mas[3]) && (mas[3] == mas[6]) && (mas[0] > 0)) {
            c = 4;
        }
        else if ((mas[1] == mas[4]) && (mas[4] == mas[7]) && (mas[1] > 0)) {
            c = 5;
        }
        else if ((mas[2] == mas[5]) && (mas[5] == mas[8]) && (mas[2] > 0)) {
            c = 6;
        }
        else if ((mas[0] == mas[4]) && (mas[4] == mas[8]) && (mas[0] > 0)) {
            c = 7;
        }
        else if ((mas[2] == mas[4]) && (mas[4] == mas[6]) && (mas[2] > 0)) {
            c = 8;
        }
        return c;
    }

    public int check_hantei() {
        int c=999;

        if ((mas[0] == mas[1]) && (mas[0] > 0) && (cnt1 == 0)) {
            c = 2;
            cnt1++;
        }
        else if ((mas[3] == mas[4]) && (mas[3] > 0) && (cnt2 == 0)) {
            c = 5;
            cnt2++;
        }
        else if ((mas[6] == mas[7]) && (mas[6] > 0) && (cnt3 == 0)) {
            c = 8;
            cnt3++;
        }
        else if ((mas[0] == mas[3]) && (mas[0] > 0) && (cnt4 == 0)) {
            c = 6;
            cnt4++;
        }
        else if ((mas[1] == mas[4]) && (mas[1] > 0) && (cnt5 == 0)) {
            c = 7;
            cnt5++;
        }
        else if ((mas[2] == mas[5]) && (mas[2] > 0) && (cnt6 == 0)) {
            c = 8;
            cnt6++;
        }
        else if ((mas[0] == mas[4]) && (mas[0] > 0) && (cnt7 == 0)) {
            c = 8;
            cnt7++;
        }
        else if ((mas[2] == mas[4]) && (mas[2] > 0) && (cnt8 == 0)) {
            c = 6;
            cnt8++;
        }
        return c;
    }

    // マスが押された時の処理．
    public void clickMas(int m){

        if(!endFlg){
            if(mas[m] == 0){   // 要素が空なら1か2を入れて、マス目を表示 mas[m] = (nanTe % 2) + 1;
                nanTe++;
                dispMas(m); // マスにまるかばつを表示
                label.setText("");
            }else if(mas[m] != 0){
                label.setText("このマスは打てません．");
            }
            endProcess();
            //System.out.print("check");
        }

        if(!endFlg){
            comp();
            endProcess();
            //System.out.print("check2");
        }
    }

    public void endProcess(){
        int check = chkEnd();
        if(check>0){ //終了をチェックし、どちらの勝ちか表示する
            endFlg = true;
            if(nanTe%2 == 1){
                label.setText("P1 wins.");
                p1WinsCnt++;
            }
            else if(nanTe%2 == 0){
                label.setText("P2 wins.");
                p2WinsCnt++;
            }
        }else if(nanTe>8){
            label.setText("Draw.");
        }
    }

    // 相手の手
    public void comp(){
        // ローカル変数
        //boolean Bool;
        int n=0;
        //Check check = new Check();
        //check = check.check_hantei(mas);
        n = check_hantei();
        System.out.print(n);
        if(n==999){
            List<Integer> left = new ArrayList<>();
            for(int i=0;i<9;i++){
                if(mas[i]==0){
                    left.add(i);
                }
            }
            int index = new Random().nextInt(left.size());
            int result = left.get(index);
            nanTe++;
            dispMas(result);
        }else{
            nanTe++;
            dispMas(n);
        }
    }

    // 指定したボタンにマークを記述．
    public void setButton(int c, String s){
        if(mas[c] != 0){
            //setButton(c, "");
            System.out.print("errorrrrrr");
            System.out.print(c);
        }else{
        bt[c].setText(s);
        bt[c].setFont(new Font("ＭＳ ゴシック", Font.BOLD, 50));
        }
    }


    // まるばつの表示．
    public void dispMas(int i){  // この関数が呼び出されるとマスにまるかばつが表示される．
        if(nanTe%2 == 1){
            setButton(i, "○");
            mas[i] = 1;
        }else{
            setButton(i, "×");
            mas[i] = 2;
        }
    }

    // リセットボタンが押された時の処理．
    public void reset(){
        for(int i=0;i<9;i++){
            mas[i] = 0;
            setButton(i, "");
            label.setText("");
            endFlg = false;
            nanTe = 0;
        }
        gmcnt++;
        gameCounter(gmcnt);
    }


    // 何ゲーム目かカウントする．
    public void gameCounter(int cnt){
        label1.setText("GAME:"+Integer.toString(cnt));
    }

    // ボタンが押された時の処理．
    public void actionPerformed(ActionEvent ae){
        // ローカル変数
        int n;

        try {
            n = Integer.parseInt(ae.getActionCommand());
            clickMas(n);
        } catch(Exception e){};

        String cmd = ae.getActionCommand();

        if (cmd.equals("Reset")){
            reset();
        }
    }
}