package view;

import domain.Item;
import service.ItemService;
import service.MyException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author barry
 * @create 2020-07-30 16:23
 */
public class ExamView {
    private ItemService itemService = new ItemService();
    private char[] answer;

    public ExamView() {
        answer = new char[itemService.TOTAL_NUM];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = ' ';
        }
    }
    public void enterMainMenu() throws MyException {
        while (true){
            displayMainMenu();
            char key = getUserAction();
            switch (key){
                case '1':
                    testExam();
                    break;
                case '2':
                    readLastExamResult();
                    break;
                case '3':
                    if (comfirmEnd("确认是否退出(Y/N):")){
                        return;
                    }
            }
        }
    }

    private void readLastExamResult() throws MyException {
        char[] answer = itemService.readAnswer();
        displayResult(answer);
    }

    /**
     * 获取用户输入的指定字符
     * @return
     */
    private char getUserAction() {
        char[] validKey = { '1', '2', '3', 'A', 'B', 'C', 'D', 'F', 'N', 'P',
                'Y' };
        char key = 0;

        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入指令");
        while (scanner.hasNext()) {
            String str = scanner.next();
            if (str.length() != 1)
                continue;

            str = str.toUpperCase();
            key = str.charAt(0);
            for (char k : validKey) {
                if (k == key) {
                    return key;
                }
            }
        }

        return key;
    }
    /**
     * 显示指定的考题内容
     * @param no
     */
    public void diaplayItem(int no) throws MyException {
        if (no < 0 || no > itemService.TOTAL_NUM){
            throw new MyException("输入题号错误");
        }
        Item item = itemService.getItem(no);
        System.out.println(item.showQuestion());
        if (answer[no-1] != ' '){
            System.out.println("你已选择答案：" + answer[no -1]);
        }
    }
    /**
     * 默认显示第一题，并记录答案，并提示上一题、下一题等
     */
    public void testExam() throws MyException {
        int curnum = 1;
        displayWelcome();

        while (true){
            if (curnum <=itemService.TOTAL_NUM){
                diaplayItem(curnum);
                System.out.println("请选择正确答案(p-上一题  n-下一题):");
            }
            char key = getUserAction();
            switch (key){
                case 'A':
                    answer[curnum -1] ='A';
                    break;
                case 'B':
                    answer[curnum -1] ='B';
                    break;
                case 'C':
                    answer[curnum -1] ='C';
                    break;
                case 'D':
                    answer[curnum -1] ='D';
                    break;
                case 'N':
                    if (curnum <itemService.TOTAL_NUM){
                        curnum++;
                    }else {
                        System.out.println("已经试最后一题");
                        curnum++;
                        System.out.println("请按f退出考试或者按p回去检测之前的试题");
                    }
                    break;
                case 'P':
                    if (curnum > 1){
                        curnum--;
                    }else {
                        System.out.println("已是第一题");
                    }
                    break;
                case 'F':
                    if(comfirmEnd("您是否确定退出")){
                    itemService.saveAnswer(answer);
                    displayResult(answer);
                    return;
                }
                    break;

            }
        }
    }

    private void displayResult(char[] answer) throws MyException {
        int score = 0;
        //计算分数
        for (int i = 0; i <itemService.TOTAL_NUM ; i++) {
            if (answer[i] == itemService.getItem(i+1).getAnswer()){
                score += 10;
            }
        }
        //输出标准成绩和自己成绩
        System.out.println("序   号   标准答案    你的答案");
        for (int i = 0; i <itemService.TOTAL_NUM ; i++) {
            Item item = itemService.getItem(i + 1);
            System.out.println("第" +((i < 9) ? " ":"") +(i+1)+"题      "
            +item.getAnswer()+ "\t"+answer[i]);
        }
        System.out.println("恭喜，本次考试成绩为：" + score + " 分");
    }

    private boolean comfirmEnd(String string) {
        System.out.println();
        System.out.println(string);
        while (true) {
            char key = getUserAction();
            if (key != 'N' && key !='Y' ){
                continue;
            }
            return (key == 'Y');
        }
    }

    private void displayWelcome() {
        System.out.println();
        System.out.println();
        System.out.println("-----------欢迎进入考试-----------");
        System.out.println();
        System.out.println("       使用以下按键进行考试：");
        System.out.println();
        System.out.println("        A-B：选择指定答案");
        System.out.println("        P  ：显示上一题");
        System.out.println("        N  ：显示下一题");
        System.out.println("        F  ：结束考试");
        System.out.println();
        System.out.print("        请按N键进入考试...");
        //按下n键进入第一题---按下N键就会跳出这一循环
        while (true){
            char key = getUserAction();
            if ('N' == key){
                break;
            }
        }

    }
    /**
     * 显示主页面信息
     */
    private void displayMainMenu() {
        System.out.println();
        System.out.println();
        System.out.println("-------欢迎使用在线考试系统-------");
        System.out.println();
        System.out.println("       1 进入考试");
        System.out.println("       2 查看成绩");
        System.out.println("       3 系统退出");
        System.out.println();
        System.out.print("       请选择...");
    }
}
