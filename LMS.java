import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableCellEditor;


class Item {
    private int typeId;
    private int itemId;
    private String name;
    private String author;
    private int popularityIndex;
    private double cost;

    public Item(int typeId, int itemId, String name, String author, int popularityIndex, double cost) 
    {
        this.typeId = typeId;
        this.itemId = itemId;
        this.name = name;
        this.author = author;
        this.popularityIndex = popularityIndex;
        this.cost = cost;
    }

    public int getTypeId() 
    {
        return typeId;
    }

    public int getItemId() 
    {
        return itemId;
    }

    public void setTypeId(int typeId) 
    {
        this.typeId = typeId;
    }

    public void setItemId(int itemId) 
    {
        this.itemId = itemId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public void setPopularityIndex(int popularityIndex) 
    {
        this.popularityIndex = popularityIndex;
    }

    public void setCost(double cost) 
    {
        this.cost = cost;
    }

    public String getName() 
    {
        return name;
    }

    public String getAuthor() 
    {
        return author;
    }

    public int getPopularityIndex() 
    {
        return popularityIndex;
    }

    public double getCost() 
    {
        return cost;
    }
}

class LibraryManagementSystem extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private List<Item> items;

    public LibraryManagementSystem() 
    {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        model.addColumn("Type ID");
        model.addColumn("Item ID");
        model.addColumn("Name");
        model.addColumn("Author");
        model.addColumn("Popularity Index");
        model.addColumn("Cost");

        table = new JTable(model) 
        {
            @Override
            public TableCellEditor getCellEditor(int row, int column) 
            {
                return null;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton viewButton = new JButton("View");
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");

        viewButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                JFrame viewFrame = new JFrame("Random Text Data");
                viewFrame.setSize(400, 300);
                viewFrame.setLocationRelativeTo(null);
                viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                String randomText = generateRandomText();

                textArea.setText(randomText);

                JScrollPane scrollPane = new JScrollPane(textArea);
                viewFrame.add(scrollPane);

                viewFrame.setVisible(true);
            }
        });

        addButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                JFrame addItemFrame = new JFrame("Add Item");
                addItemFrame.setSize(600, 600);
                addItemFrame.setLocationRelativeTo(null);
                addItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.anchor = GridBagConstraints.WEST;
                constraints.insets = new Insets(10, 10, 10, 10);

                JLabel typeIdLabel = new JLabel("Type ID:");
                JTextField typeIdField = new JTextField(10);

                JLabel itemIdLabel = new JLabel("Item ID:");
                JTextField itemIdField = new JTextField(10);

                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField(10);

                JLabel authorLabel = new JLabel("Author:");
                JTextField authorField = new JTextField(10);

                JLabel popularityIndexLabel = new JLabel("Popularity Index:");
                JTextField popularityIndexField = new JTextField(10);

                JLabel costLabel = new JLabel("Cost:");
                JTextField costField = new JTextField(10);

                constraints.gridx = 0;
                constraints.gridy = 0;
                panel.add(typeIdLabel, constraints);

                constraints.gridx = 1;
                panel.add(typeIdField, constraints);

                constraints.gridx = 0;
                constraints.gridy = 1;
                panel.add(itemIdLabel, constraints);

                constraints.gridx = 1;
                panel.add(itemIdField, constraints);

                constraints.gridx = 0;
                constraints.gridy = 2;
                panel.add(nameLabel, constraints);

                constraints.gridx = 1;
                panel.add(nameField, constraints);

                constraints.gridx = 0;
                constraints.gridy = 3;
                panel.add(authorLabel, constraints);

                constraints.gridx = 1;
                panel.add(authorField, constraints);

                constraints.gridx = 0;
                constraints.gridy = 4;
                panel.add(popularityIndexLabel, constraints);

                constraints.gridx = 1;
                panel.add(popularityIndexField, constraints);

                constraints.gridx = 0;
                constraints.gridy = 5;
                panel.add(costLabel, constraints);

                constraints.gridx = 1;
                panel.add(costField, constraints);

                JButton addButton = new JButton("Add");
                addButton.addActionListener(new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int typeId = Integer.parseInt(typeIdField.getText());
                        int itemId = Integer.parseInt(itemIdField.getText());
                        String name = nameField.getText();
                        String author = authorField.getText();
                        int popularityIndex = Integer.parseInt(popularityIndexField.getText());
                        double cost = Double.parseDouble(costField.getText());

                        Item item = new Item(typeId, itemId, name, author, popularityIndex, cost);
                        items.add(item);
                        displayItems();
                        addItemFrame.dispose();
                    }
                });

                constraints.gridx = 0;
                constraints.gridy = 6;
                constraints.gridwidth = 2;
                constraints.anchor = GridBagConstraints.CENTER;
                panel.add(addButton, constraints);

                addItemFrame.add(panel);
                addItemFrame.setVisible(true);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int itemId = (int) table.getValueAt(selectedRow, 1);
                deleteItem(itemId);
            } else {
                JOptionPane.showMessageDialog(LibraryManagementSystem.this, "Please select an item to delete.");
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) 
            {
                int itemId = (int) table.getValueAt(selectedRow, 1);
                updateItem(itemId);
            } 
            else 
            {
                JOptionPane.showMessageDialog(LibraryManagementSystem.this, "Please select an item to update.");
            }
        });

        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadItemsFromFile("data.txt");
        displayItems();
    }

    private String generateRandomText() {
        StringBuilder sb = new StringBuilder();

        // Generate random text data
        for (int i = 0; i < 10; i++) {
            String randomWord = generateRandomWord();
            sb.append(randomWord).append(" ");
        }

        return sb.toString();
    }

    private String generateRandomWord() {
        String[] words = { "Hello", "I", "me", "sit", "groot", "shout", "mouth", "aspouth", "meow", "do" };
        int randomIndex = (int) (Math.random() * words.length);
        return words[randomIndex];
    }

    private void loadItemsFromFile(String filename) {
        items = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int typeId = Integer.parseInt(data[0]);
                int itemId = Integer.parseInt(data[1]);
                String name = data[2];
                String author = data[3];
                int popularityIndex = Integer.parseInt(data[4]);
                double cost = Double.parseDouble(data[5]);

                Item item = new Item(typeId, itemId, name, author, popularityIndex, cost);
                items.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayItems() 
    {
        model.setRowCount(0);

        for (Item item : items) 
        {
            Object[] rowData = 
            {
                    item.getTypeId(),
                    item.getItemId(),
                    item.getName(),
                    item.getAuthor(),
                    item.getPopularityIndex(),
                    item.getCost()
            };
            model.addRow(rowData);
        }

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Item item = items.get(selectedRow);
                    showItemDetails(item);
                }
            }
        });
    }

    private void showItemDetails(Item item) 
    {
        JFrame detailsFrame = new JFrame("Item Details");
        detailsFrame.setSize(400, 300);
        detailsFrame.setLocationRelativeTo(null);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Type ID:"));
        panel.add(new JLabel(String.valueOf(item.getTypeId())));
        panel.add(new JLabel("Item ID:"));
        panel.add(new JLabel(String.valueOf(item.getItemId())));
        panel.add(new JLabel("Name:"));
        panel.add(new JLabel(item.getName()));
        panel.add(new JLabel("Author:"));
        panel.add(new JLabel(item.getAuthor()));
        panel.add(new JLabel("Popularity Index:"));
        panel.add(new JLabel(String.valueOf(item.getPopularityIndex())));
        panel.add(new JLabel("Cost:"));
        panel.add(new JLabel(String.valueOf(item.getCost())));

        detailsFrame.add(panel);
        detailsFrame.setVisible(true);
    }

    private void deleteItem(int itemId) 
    {
        Item itemToRemove = null;
        for (Item item : items) 
        {
            if (item.getItemId() == itemId) 
            {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) 
        {
            items.remove(itemToRemove);
            displayItems();
        }
    }

    private void updateItem(int itemId) 
    {
        Item itemToUpdate = null;
        for (Item item : items) {
            if (item.getItemId() == itemId) 
            {
                itemToUpdate = item;
                break;
            }
        }

        if (itemToUpdate != null) 
        {
            showUpdateItemForm(itemToUpdate);
        }
    }

    private void showUpdateItemForm(Item item) 
    {
        JFrame updateItemFrame = new JFrame("Update Item");
        updateItemFrame.setSize(400, 300);
        updateItemFrame.setLocationRelativeTo(null);
        updateItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel typeIdLabel = new JLabel("Type ID:");
        JTextField typeIdField = new JTextField(String.valueOf(item.getTypeId()), 10);

        JLabel itemIdLabel = new JLabel("Item ID:");
        JTextField itemIdField = new JTextField(String.valueOf(item.getItemId()), 10);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(item.getName(), 10);

        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField(item.getAuthor(), 10);

        JLabel popularityIndexLabel = new JLabel("Popularity Index:");
        JTextField popularityIndexField = new JTextField(String.valueOf(item.getPopularityIndex()), 10);

        JLabel costLabel = new JLabel("Cost:");
        JTextField costField = new JTextField(String.valueOf(item.getCost()), 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(typeIdLabel, constraints);

        constraints.gridx = 1;
        panel.add(typeIdField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(itemIdLabel, constraints);

        constraints.gridx = 1;
        panel.add(itemIdField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(nameLabel, constraints);

        constraints.gridx = 1;
        panel.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(authorLabel, constraints);

        constraints.gridx = 1;
        panel.add(authorField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(popularityIndexLabel, constraints);

        constraints.gridx = 1;
        panel.add(popularityIndexField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(costLabel, constraints);

        constraints.gridx = 1;
        panel.add(costField, constraints);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            int typeId = Integer.parseInt(typeIdField.getText());
            int itemId = Integer.parseInt(itemIdField.getText());
            String name = nameField.getText();
            String author = authorField.getText();
            int popularityIndex = Integer.parseInt(popularityIndexField.getText());
            double cost = Double.parseDouble(costField.getText());

            item.setTypeId(typeId);
            item.setItemId(itemId);
            item.setName(name);
            item.setAuthor(author);
            item.setPopularityIndex(popularityIndex);
            item.setCost(cost);

            displayItems();

            updateItemFrame.dispose();
        });

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(updateButton, constraints);

        updateItemFrame.add(panel);
        updateItemFrame.setVisible(true);
    }

}

public class LMS {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new LibraryManagementSystem().setVisible(true);
            }
        });
    }
}
