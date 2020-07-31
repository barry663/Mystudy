package domain;

import java.util.List;

/**题目分成三个部分：题目，选项，答案
 * 选项由list存储
 * 题目为String, 答案为char
 * @author barry
 * @create 2020-07-29 21:43
 */
public class Item {
    private String question;
    private String[] options;
    private char answer;

    public Item() {
    }

    public Item(String question, String[] options, char answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public char getAnswer() {
        return answer;
    }

    public void setAnswer(char answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return question + "\n"
                +options[0]+"\n"
                +options[1]+"\n"
                +options[2]+"\n"
                +options[3]+"\n标准答案："+ answer+"\n";
    }
    public String showQuestion() {
        return question + "\n" +
                options[0] + "\n" +
                options[1] + "\n" +
                options[2] + "\n" +
                options[3] + "\n";
    }
}
