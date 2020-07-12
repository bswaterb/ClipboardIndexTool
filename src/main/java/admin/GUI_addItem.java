package admin;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import po.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;


public class GUI_addItem extends JFrame implements ActionListener {
    JFrame jFrame = new JFrame();
    JPanel jPanel = new JPanel();
    JButton submit = new JButton("提交");
    JLabel label_question = new JLabel("索引：");
    JLabel label_answer = new JLabel("内容：");
    JTextField JQuestion = new JTextField(20);
    JTextField JAnswer = new JTextField(20);
    static SqlSession sqlSession;
    static SqlSessionFactory factory;
    public GUI_addItem(){
        jFrame.setLayout(new GridLayout(3,2));

        jPanel.add(label_question);
        jPanel.add(JQuestion);
        jPanel.add(label_answer);
        jPanel.add(JAnswer);
        jPanel.add(submit);

        label_answer.setHorizontalTextPosition(SwingConstants.RIGHT);
        label_question.setHorizontalTextPosition(SwingConstants.RIGHT);
        submit.addActionListener(this);
        this.add(jPanel,BorderLayout.CENTER);
        this.pack();
        this.setLocation(500,300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        InputStream config = null;
        config = Resources.getResourceAsStream("mybatis-config.xml");
        factory = new SqlSessionFactoryBuilder().build(config);
        new GUI_addItem();


    }

    public void actionPerformed(ActionEvent e){

        sqlSession = factory.openSession();
        String question = JQuestion.getText();
        String answer = JAnswer.getText();
        Item item = new Item();
        item.setAnswer(answer);
        item.setQuestion(question);
        if(sqlSession.selectOne("mapper.ExamMapper.selectAnswerByQuestion", item)==null){
            sqlSession.insert("mapper.ExamMapper.addQuestion", item);
            sqlSession.commit();
            JOptionPane.showMessageDialog(null,"添加成功！");
        }
        else{
            sqlSession.update("mapper.ExamMapper.updateQuestion", item);
            sqlSession.commit();
            JOptionPane.showMessageDialog(null,"修改成功！");
        }
        sqlSession.close();


    }
}
