package service;

import domain.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**与考试题目相关的业务
 * @author barry
 * @create 2020-07-29 21:57
 */
public class ItemService {
    private Item[] items;
    private final String FILE_PATH="items.txt";
    private final int LINES_PRE_ITEM = 6;
    private final String ANSWER_FILENAME ="answer.dat";
    public final int TOTAL_NUM;


    /**完成题目的加载---题目的加载一般是通过new来加载，先把各个属性整理出来*/
    public ItemService() {
        List<String> list = readTextFile(FILE_PATH);
        TOTAL_NUM = list.size() /LINES_PRE_ITEM;
        items = new Item[TOTAL_NUM];
        for (int i = 0; i <TOTAL_NUM ; i++) {
            String question = list.get(i *LINES_PRE_ITEM);
            String[] options = {
                    list.get(i *LINES_PRE_ITEM + 1),
                    list.get(i *LINES_PRE_ITEM + 2),
                    list.get(i *LINES_PRE_ITEM + 3),
                    list.get(i *LINES_PRE_ITEM + 4)
            };
            char answer = list.get(i * LINES_PRE_ITEM + 5).charAt(0);
            items[i] =new Item(question,options,answer);
        }

    }
    //    public ItemService() {
//        List<String> list = readTextFile(FILE_PATH);
//        int num = list.size() / LINES_PRE_ITEM;
//        items = new Item[num];
//        String[] options = new String[4];
//        for (int i = 0; i < num; i++) {
//            items[i].setQuestion(list.get(i * LINES_PRE_ITEM +1));
//            for (int j = 0; j <options.length ; j++) {
//                options[j] = list.get(i * LINES_PRE_ITEM+2+j);
//            }
//        }
//    }

    /**
     * 读取指定位置文件的内容到内存中
     * @param filename
     * @return
     */
    private List<String> readTextFile(String filename){
        BufferedReader bfr =null;
        List<String> content = new ArrayList<>();
        try {
            bfr = new BufferedReader(new FileReader(new File(filename)));
            String data;
            while ((data = bfr.readLine()) !=null){
                if (!("".equals(data.trim()))){
                    content.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bfr != null){
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content;
    }
    public void readTextFile1(String filename){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filename)));
            String data;
            while ((data = br.readLine()) != null) {
                if (data.trim() !=""){
                    System.out.println(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br !=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 返回指定题号的题目
     * @param no
     * @return
     */
    public Item getItem(int no) throws MyException {
        if (no > 0 && no <TOTAL_NUM + 1){
            return items[no-1];
        }
        throw new MyException("找不到对应的题目");
    }
    /**
     * 将所有的答案构成的数组持久化到文件中
     * @param answer
     */
    public void saveAnswer(char[] answer){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File(ANSWER_FILENAME)));
            oos.writeObject(answer);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public char[] readAnswer() throws MyException {
       ObjectInputStream ois =null;
        char[] answer = new char[0];
        try {
            ois = new ObjectInputStream(new FileInputStream(new File(ANSWER_FILENAME)));
            answer = (char[]) ois.readObject();
            return answer;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new MyException("文件读取失败！");
    }
}
