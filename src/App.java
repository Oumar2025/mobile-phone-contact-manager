import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class App {

    // Input fields
    private final JTextField nameField;
    private final JTextField phoneField;

    // Table and model
    private final DefaultTableModel tableModel;
    private final JTable table;

    public App() {

        JFrame frame = new JFrame("Mobile Phone Contact Manager");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Create text fields
        nameField = new JTextField(20);
        phoneField = new JTextField(20);

        // Create table model
        tableModel = new DefaultTableModel(
                new String[] { "Name", "Phone Number" },
                0);

        // Create table
        table = new JTable(tableModel);

        // Improve table appearance
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);

        // When a row is selected, load data into text fields
        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row != -1) {

                nameField.setText(
                        tableModel.getValueAt(row, 0).toString());

                phoneField.setText(
                        tableModel.getValueAt(row, 1).toString());
            }
        });

        frame.add(createInputPanel(), BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Create input form and buttons
    private JPanel createInputPanel() {

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(
                BorderFactory.createTitledBorder("Contact Information"));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        // Name label
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);

        // Name field
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Phone label
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Phone:"), gbc);

        // Phone field
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        // Buttons
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        addButton.addActionListener(e -> addContact());
        editButton.addActionListener(e -> editContact());
        deleteButton.addActionListener(e -> deleteContact());
        clearButton.addActionListener(e -> clearFields());

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        panel.add(buttonPanel, gbc);

        return panel;
    }

    // Add a new contact
    private void addContact() {

        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        // Validation
        if (name.isEmpty() || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Please fill all fields.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        // Check duplicate phone number
        for (int i = 0; i < tableModel.getRowCount(); i++) {

            String existingPhone = tableModel.getValueAt(i, 1).toString();

            if (existingPhone.equals(phone)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Phone number already exists!",
                        "Duplicate",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }
        }

        // Add contact
        tableModel.addRow(
                new Object[] { name, phone });

        JOptionPane.showMessageDialog(
                null,
                "Contact added successfully!");

        clearFields();
    }

    // Edit selected contact
    private void editContact() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    null,
                    "Please select a contact to edit.");

            return;
        }

        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Please fill all fields.");

            return;
        }

        // Update table
        tableModel.setValueAt(name, selectedRow, 0);
        tableModel.setValueAt(phone, selectedRow, 1);

        JOptionPane.showMessageDialog(
                null,
                "Contact updated successfully!");

        clearFields();
    }

    // Delete selected contact
    private void deleteContact() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    null,
                    "Please select a contact to delete.");

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this contact?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            tableModel.removeRow(selectedRow);

            JOptionPane.showMessageDialog(
                    null,
                    "Contact deleted successfully!");

            clearFields();
        }
    }

    // Clear text fields
    private void clearFields() {

        nameField.setText("");
        phoneField.setText("");

        table.clearSelection();

        nameField.requestFocus();
    }

    // Main method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(App::new);
    }
}