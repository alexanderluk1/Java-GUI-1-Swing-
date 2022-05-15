import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AlexanderLuk_A3 extends JFrame implements ActionListener {

    public static void main(String[] args) {
        
        AlexanderLuk_A3 myApp = new AlexanderLuk_A3();
    }

    public AlexanderLuk_A3()
    {
        createGUI();
    }

    // Creating a Frame and Panel
    JFrame frame;
    JPanel panel;

    // Creating a font object to hold the fonts for the labels
    Font font = new Font("Courier", Font.BOLD, 12);

    // Creating Labels to hold label
    JLabel codeLabel = new JLabel("Code");
    JLabel quantityLabel = new JLabel("Quantity");
    JLabel priceLabel = new JLabel("Price");
    JLabel discountLabel = new JLabel("Discount");
    JLabel totalLabel = new JLabel("Total");

    // Creating TB to hold all the read values
    JTextField codeTB = new JTextField();
    JTextField quantityTB = new JTextField();
    JTextField priceTB = new JTextField();
    JTextField discountTB = new JTextField();
    JTextField totalTB = new JTextField();

    // Creating Buttons to handle Event
    JButton loadBn = new JButton("Load");
    JButton prevBn = new JButton("Prev");
    JButton nextBn = new JButton("Next");

    // ArrayList to hold the values read
    ArrayList<quotationItem> listQuotes = new ArrayList<quotationItem>();

    // Counter to track which record is shown
    int counter = 0;

    public void createGUI()
    {
        // ------------------- Frame Settings -------------------
        frame = new JFrame();
        frame.setTitle("Quotation Management");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setResizable(false);

        // ------------------- Main Panel Settings -------------------
        panel = new JPanel(new GridLayout(6, 2)); // 6x2 grid layout
        panel.setBackground(Color.LIGHT_GRAY);
        frame.add(panel);

        // ------------------- Settings for Label -------------------
        codeLabel.setFont(font);
        quantityLabel.setFont(font);
        priceLabel.setFont(font);
        discountLabel.setFont(font);
        totalLabel.setFont(font);

        // ------------------- Settings for TB -------------------
        codeTB.setEditable(false);
        codeTB.setColumns(20);

        quantityTB.setEditable(false);
        quantityTB.setColumns(20);

        priceTB.setEditable(false);
        priceTB.setColumns(20);

        discountTB.setEditable(false);
        discountTB.setColumns(20);

        totalTB.setEditable(false);
        totalTB.setColumns(20);

        // ------------------- Settings for Button -------------------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0 , 0));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        loadBn.setPreferredSize(new Dimension(80, 40));
        prevBn.setPreferredSize(new Dimension(80, 40));
        nextBn.setPreferredSize(new Dimension(80, 40));

        // Set Prev and Next to disable
        prevBn.setEnabled(false);
        nextBn.setEnabled(false);

        buttonPanel.add(loadBn);
        buttonPanel.add(prevBn);
        buttonPanel.add(nextBn);

        loadBn.addActionListener(this);
        prevBn.addActionListener(this);
        nextBn.addActionListener(this);

        // ------------------- Adding Label and TB into the panel -------------------
        panel.add(codeLabel);
        panel.add(codeTB);

        panel.add(quantityLabel);
        panel.add(quantityTB);

        panel.add(priceLabel);
        panel.add(priceTB);

        panel.add(discountLabel);
        panel.add(discountTB);

        panel.add(totalLabel);
        panel.add(totalTB);

        panel.add(new JLabel());
        panel.add(buttonPanel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        BufferedReader br = null;
        String eachLine = ""; // To hold each line of file

        if (e.getSource() == loadBn)
        {
            // ------------------- READING OF FILE TO ARRAYLIST -------------------
            try 
            {
                br = new BufferedReader(new FileReader("src/output.txt"));

                while ((eachLine = br.readLine())!= null)
                {
                    String[] values = eachLine.split(",");

                    listQuotes.add(new quotationItem(values[0], values[1], values[2], values[3], values[4]));
                }
                loadBn.setEnabled(false);
                nextBn.setEnabled(true);

                displayQuotes();
            } 
            catch (FileNotFoundException e1) // If no record found, display warning message
            {
                JOptionPane.showMessageDialog(null, "No quotation record", "Message", JOptionPane.WARNING_MESSAGE);
            }
            catch (IOException err)
            {
                err.printStackTrace();
            }
            finally
            {
                if (br != null)
                {
                    try 
                    {
                        br.close();
                    } 
                    catch (IOException e1) 
                    {
                        e1.printStackTrace();
                    }
                }
            }
        }
        // ------------------- PREV BUTTON -------------------
        else if (e.getSource() == prevBn)
        {   
            nextBn.setEnabled(true);
            --counter;
            displayQuotes();
        
            // After displaying, if counter == 0, disable prev bn
            if (counter == 0)
            {
                prevBn.setEnabled(false);
            }
        }
        // ------------------- NEXT BUTTON -------------------
        else if (e.getSource() == nextBn)
        {
            prevBn.setEnabled(true);
            ++counter;
            displayQuotes();
            
            // After displaying, if counter == lastRecord, disable next bn
            if (counter == listQuotes.size() - 1)
            {
                nextBn.setEnabled(false);
            }
        }
    }

    public void displayQuotes()
    {
        codeTB.setText(listQuotes.get(counter).getCode());
        quantityTB.setText(listQuotes.get(counter).getQuantity());
        priceTB.setText(listQuotes.get(counter).getPrice());
        discountTB.setText(listQuotes.get(counter).getDiscount());
        totalTB.setText(listQuotes.get(counter).getDiscount());
    }
}

class quotationItem {
    
    // Since we aren't manipulating the values, I will be initialising as String
    private String code;
    private String quantity;
    private String price;
    private String discount;
    private String total;

    public quotationItem(String code, String quantity, String price, String discount, String total)
    {
        this.code = code;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}