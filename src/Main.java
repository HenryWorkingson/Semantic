import javax.swing.*;
import java.awt.*;

public class Main {
    JMenu menu, submenu;
    JMenuItem i1, i2, i3, i4, i5;
    public Main(){
        JFrame frame = new JFrame("Bibliotecas");
        // Setting the width and height of frame
        frame.setSize(950, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);

        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", 5, Boolean.FALSE},
                {"John", "Doe",
                        "Rowing", 3, Boolean.TRUE},
                {"Sue", "Black",
                        "Knitting", 2, Boolean.FALSE},
                {"Jane", "White",
                        "Speed reading", 20, Boolean.TRUE},
                {"Joe", "Brown",
                        "Pool", 10, Boolean.FALSE}
        };

        JTable table = new JTable(data, columnNames);
        table.setRowHeight(50);
        table.setFillsViewportHeight(true);
        panel.setLayout(new BorderLayout());
        panel.add(table.getTableHeader(),BorderLayout.PAGE_START);

        panel.add(table,BorderLayout.CENTER);


        /*
         * 调用用户定义的方法并添加组件到面板
         */
        // 设置界面可见
        frame.setVisible(true);
    }
    public static void main(String args[])
    {
        new Main();
    }

}