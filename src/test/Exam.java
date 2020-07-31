package test;


import domain.Item;
import org.junit.Test;
import service.ItemService;
import service.MyException;
import view.ExamView;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author barry
 * @create 2020-07-30 14:37
 */
public class Exam {
    //使用I/O流读取文本文件
   @Test
    public void test(){
       ItemService itemService = new ItemService();
       itemService.readTextFile1("items.txt");
   }
   //使用流的链接读取文本文件
//    @Test
//    public void test1(){
//       ItemService itemService = new ItemService();
//        List<String> list = itemService.readTextFile("items.txt");
//        for (String s:list
//             ) {
//            System.out.println(s);
//        }
//    }
    @Test
    public void testGetItem(){
       ItemService itemService = new ItemService();
       int num = 15;
        try {
            Item item = itemService.getItem(num);
            System.out.println(item);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testsaveAnswer(){
       char[] answer = {'a','v','d'};
       ItemService itemService = new ItemService();
//       itemService.saveAnswer(answer);
        char[] chars = new char[0];
        try {
            chars = itemService.readAnswer();
        } catch (MyException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(chars));
    }

    public static void main(String[] args) {
    ExamView examView = new ExamView();
        try {
            examView.enterMainMenu();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
