package view;

import controller.ImageController;
import controller.ImageGUIController;
import model.EnhancedImage;
import model.EnhancedImageModel;
import model.imagetransformation.basicoperation.Flip.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcessorGUI extends JFrame {
  private ImageGUIController controller; // Reference to the controller
  private JLabel imageLabel; // To display the loaded image
  private JLabel histogramLabel; // To display the histogram

  public ImageProcessorGUI(ImageGUIController controller) {
    this.controller = controller; // Initialize the controller

    // Set up the main frame
    setTitle("Image Processor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 800);
    setLayout(new BorderLayout());

    // Create a panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 2, 5, 5)); // Two-column layout: buttons in left column, toggles in right

    // Initialize buttons for all functions
    JButton loadButton = new JButton("Load Image");
    JButton saveButton = new JButton("Save Image");
    JButton brightenButton = new JButton("Brighten");
    JButton blurButton = new JButton("Blur");
    JCheckBox blurSplitToggle = new JCheckBox("Split");
    JButton sharpenButton = new JButton("Sharpen");
    JCheckBox sharpenSplitToggle = new JCheckBox("Split");
    JButton greyscaleButton = new JButton("Greyscale");
    JCheckBox greyscaleSplitToggle = new JCheckBox("Split");
    JButton sepiaButton = new JButton("Sepia");
    JCheckBox sepiaSplitToggle = new JCheckBox("Split");
    JButton horizontalFlipButton = new JButton("Horizontal Flip");
    JButton verticalFlipButton = new JButton("Vertical Flip");
    JButton redComponentButton = new JButton("Red Component");
    JButton greenComponentButton = new JButton("Green Component");
    JButton blueComponentButton = new JButton("Blue Component");
    JButton compressButton = new JButton("Compress");
    JButton rgbSplitButton = new JButton("RGB Split");
    JButton rgbCombineButton = new JButton("RGB Combine");
    JButton levelsAdjustButton = new JButton("Levels Adjust");
    JCheckBox levelsAdjustSplitToggle = new JCheckBox("Split");
    JButton exitButton = new JButton("Exit");

    // Add action listeners to buttons
    loadButton.addActionListener(e -> controller.handleLoad(this,"load1"));
    saveButton.addActionListener(e -> controller.handleSave(new String[]{"save", "output.png", controller.getLatestKey()}));
    brightenButton.addActionListener(e -> handleBrighten());
    blurButton.addActionListener(e -> handleBlur(blurSplitToggle.isSelected()));
    sharpenButton.addActionListener(e -> handleSharpen(sharpenSplitToggle.isSelected()));
    greyscaleButton.addActionListener(e -> handleGreyscale(greyscaleSplitToggle.isSelected()));
    sepiaButton.addActionListener(e -> handleSepia(sepiaSplitToggle.isSelected()));
    horizontalFlipButton.addActionListener(e -> controller.handleFlip(new String[]{"horizontal-flip", controller.getLatestKey(), "flipped-horizontal"}, Direction.HORIZONTAL));
    verticalFlipButton.addActionListener(e -> controller.handleFlip(new String[]{"vertical-flip", controller.getLatestKey(), "flipped-vertical"}, Direction.VERTICAL));
    redComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"red-component", controller.getLatestKey(), "red"}));
    greenComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"green-component", controller.getLatestKey(), "green"}));
    blueComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"blue-component", controller.getLatestKey(), "blue"}));
    compressButton.addActionListener(e -> handleCompression());
    rgbSplitButton.addActionListener(e -> handleRGBSplit());
    rgbCombineButton.addActionListener(e -> handleRGBCombine());
    levelsAdjustButton.addActionListener(e -> handleLevelsAdjust(levelsAdjustSplitToggle.isSelected()));
    exitButton.addActionListener(e -> System.exit(0)); // Exit the application

    // Add buttons and checkboxes to the button panel (buttons in left column, checkboxes in right column)
    buttonPanel.add(loadButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(saveButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(brightenButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(blurButton);
    buttonPanel.add(blurSplitToggle);
    buttonPanel.add(sharpenButton);
    buttonPanel.add(sharpenSplitToggle);
    buttonPanel.add(greyscaleButton);
    buttonPanel.add(greyscaleSplitToggle);
    buttonPanel.add(sepiaButton);
    buttonPanel.add(sepiaSplitToggle);
    buttonPanel.add(horizontalFlipButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(verticalFlipButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(redComponentButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(greenComponentButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(blueComponentButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(compressButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(rgbSplitButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(rgbCombineButton);
    buttonPanel.add(new JPanel()); // Empty space
    buttonPanel.add(levelsAdjustButton);
    buttonPanel.add(levelsAdjustSplitToggle);
    buttonPanel.add(exitButton);
    buttonPanel.add(new JPanel()); // Empty space

    // Set up the image display area
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER); // Center the image
    imageLabel.setPreferredSize(new Dimension(900, 900)); // Set preferred size for the image area

    // Set up the histogram display area
    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(JLabel.CENTER); // Position the histogram
    histogramLabel.setPreferredSize(new Dimension(50, 50)); // Set preferred size for the histogram area

    // Wrap the image and histogram labels in JScrollPane to enable scrolling
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(800, 800)); // Set preferred size for the scrollable area
    imageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    imageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    JScrollPane histogramScrollPane = new JScrollPane(histogramLabel);
    histogramScrollPane.setPreferredSize(new Dimension(400, 400)); // Set preferred size for the scrollable histogram area
    histogramScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    histogramScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    // Create a panel for the image and histogram with BorderLayout
    JPanel imagePanel = new JPanel();
    imagePanel.setLayout(new BorderLayout(10, 10)); // Use BorderLayout to align image and histogram side by side
    imagePanel.add(imageScrollPane, BorderLayout.CENTER); // Image in the center
    imagePanel.add(histogramScrollPane, BorderLayout.EAST); // Histogram to the right

    // Add panels to the main frame
    add(buttonPanel, BorderLayout.WEST); // Place button panel on the left
    add(imagePanel, BorderLayout.CENTER); // Center panel for image and histogram

    // Set the frame visibility
    setVisible(true);
  }

  // Method to display the loaded image
  public void displayImage(BufferedImage image) {
    imageLabel.setIcon(new ImageIcon(image));
  }

  // Method to display the histogram
  public void displayHistogram(BufferedImage histogram) {
    histogramLabel.setIcon(new ImageIcon(histogram));
  }

  // Helper methods for advanced operations
  private void handleBrighten() {
    String factor = JOptionPane.showInputDialog("Enter brightness factor:");
    if (factor != null) {
      controller.handleBrighten(new String[]{"brighten", factor, controller.getLatestKey(), "brightened"});
      handleHistogram();
    }
  }

  private void handleBlur(boolean isSplit) {
    String percentage = isSplit ? JOptionPane.showInputDialog("Enter split percentage (0-100):") : "100";
    if (percentage != null) {
      controller.applyOperation(new String[]{"blur", controller.getLatestKey(), "blurred", percentage});
      handleHistogram();
    }
  }

  private void handleSharpen(boolean isSplit) {
    String percentage = isSplit ? JOptionPane.showInputDialog("Enter split percentage (0-100):") : "100";
    if (percentage != null) {
      controller.applyOperation(new String[]{"sharpen", controller.getLatestKey(), "sharpened", percentage});
      handleHistogram();
    }
  }

  private void handleGreyscale(boolean isSplit) {
    String percentage = isSplit ? JOptionPane.showInputDialog("Enter split percentage (0-100):") : "100";
    if (percentage != null) {
      controller.applyOperation(new String[]{"greyscale", controller.getLatestKey(), "greyscale", percentage});
      handleHistogram();
    }
  }

  private void handleHistogram() {
    controller.applyHistogram(new String[]{"histogram", controller.getLatestKey(), "histogram"});
  }

  private void handleSepia(boolean isSplit) {
    String percentage = isSplit ? JOptionPane.showInputDialog("Enter split percentage (0-100):") : "100";
    if (percentage != null) {
      controller.applyOperation(new String[]{"sepia", controller.getLatestKey(), "sepia", percentage});
      handleHistogram();
    }
  }

  private void handleCompression() {
    String ratio = JOptionPane.showInputDialog("Enter compression ratio (0-100):");
    if (ratio != null) {
      controller.handleCompression(new String[]{"compress", ratio, controller.getLatestKey(), "compressed"});
      handleHistogram();
    }
  }

  private void handleRGBSplit() {
    String redKey = JOptionPane.showInputDialog("Enter red channel key:");
    String greenKey = JOptionPane.showInputDialog("Enter green channel key:");
    String blueKey = JOptionPane.showInputDialog("Enter blue channel key:");
    if (redKey != null && greenKey != null && blueKey != null) {
      controller.handleRGBSplit(new String[]{"rgb-split", controller.getLatestKey(), redKey, greenKey, blueKey});
    }
  }

  private void handleRGBCombine() {
    String redKey = JOptionPane.showInputDialog("Enter red channel key:");
    String greenKey = JOptionPane.showInputDialog("Enter green channel key:");
    String blueKey = JOptionPane.showInputDialog("Enter blue channel key:");
    if (redKey != null && greenKey != null && blueKey != null) {
      controller.handleCombine(new String[]{"rgb-combine", controller.getLatestKey(), redKey, greenKey, blueKey});
    }
  }

  private void handleLevelsAdjust(boolean isSplit) {
    String black = JOptionPane.showInputDialog("Enter black level (0-255):");
    String mid = JOptionPane.showInputDialog("Enter mid level (0-255):");
    String white = JOptionPane.showInputDialog("Enter white level (0-255):");
    String percentage = isSplit ? JOptionPane.showInputDialog("Enter split percentage (0-100):") : "100";
    if (black != null && mid != null && white != null && percentage != null) {
      controller.handleLevelsAdjust(new String[]{"levels-adjust", black, mid, white, percentage});
    }
  }

  public static void main(String[] args) {
    EnhancedImageModel i1 = new EnhancedImage();
    ImageController image = new ImageController(i1);
    ImageGUIController controller = new ImageGUIController(i1, image);
    new ImageProcessorGUI(controller); // Launch the GUI with the controller
  }
}
