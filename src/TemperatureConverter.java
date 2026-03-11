import javax.swing.*;
import java.awt.*;

public class TemperatureConverter
{
    // -------------------------------
    // Conversion Logic
    // -------------------------------
    private static double convert(double temp, String from, String to)
    {
        // If both scales are the same, return the input unchanged
        if (from.equals(to)) return temp;

        // Convert input temperature to Celsius first
        double celsius = temp;
        if (from.equals("Fahrenheit")) celsius = (temp - 32) * 5 / 9;
        else if (from.equals("Kelvin")) celsius = temp - 273.15;

        // Convert Celsius to the target scale
        if (to.equals("Fahrenheit")) return (celsius * 9 / 5) + 32;
        if (to.equals("Kelvin")) return celsius + 273.15;

        return celsius; // Default: return Celsius
    }

    // -------------------------------
    // Main Method (GUI Setup)
    // -------------------------------
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            // ----- Global Font Settings -----
            Font myFont = new Font("Arial", Font.PLAIN, 18);
            UIManager.put("Label.font", myFont);
            UIManager.put("Button.font", myFont);
            UIManager.put("ComboBox.font", myFont);

            // ----- Frame Setup -----
            JFrame frame = new JFrame("Temperature Converter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(6, 2, 50, 5));

            // ----- Components -----
            String[] scales = {"Celsius", "Fahrenheit", "Kelvin"};
            JComboBox<String> fromScale = new JComboBox<>(scales);
            JComboBox<String> toScale = new JComboBox<>(scales);

            JTextField inputField = new JTextField();
            JTextField resultField = new JTextField();
            resultField.setEditable(false); // Result field is read-only

            JButton convertBtn = new JButton("Convert");
            JButton clearBtn = new JButton("Clear");
            JButton swapBtn = new JButton("Swap Scales");

            // ----- Add Components to Frame -----
            frame.add(new JLabel(" From Scale:"));
            frame.add(fromScale);
            frame.add(new JLabel(" To Scale:"));
            frame.add(toScale);
            frame.add(new JLabel(" Input Temp:"));
            frame.add(inputField);
            frame.add(convertBtn);
            frame.add(resultField);
            frame.add(clearBtn);
            frame.add(swapBtn);

            // -------------------------------
            // Event Handling
            // -------------------------------

            // Convert button: perform conversion
            convertBtn.addActionListener(e ->
            {
                try
                {
                    double temp = Double.parseDouble(inputField.getText());
                    String from = (String) fromScale.getSelectedItem();
                    String to = (String) toScale.getSelectedItem();
                    assert from != null;
                    double result = convert(temp, from, to);
                    resultField.setText(String.format("%.2f", result));
                }
                catch (NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid numerical value.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Clear button: reset fields
            clearBtn.addActionListener(e ->
            {
                inputField.setText("");
                resultField.setText("");
            });

            // Swap button: swap selected scales
            swapBtn.addActionListener(e ->
            {
                int tempIndex = fromScale.getSelectedIndex();
                fromScale.setSelectedIndex(toScale.getSelectedIndex());
                toScale.setSelectedIndex(tempIndex);
            });

            // ----- Final Frame Settings -----
            frame.pack();
            frame.setLocationRelativeTo(null); // Center on screen
            frame.setVisible(true);
        });
    }
}