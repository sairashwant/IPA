package view;

import controller.ImageController;
import controller.ImageGUIController;
import model.EnhancedImage;
import model.EnhancedImageModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import model.imagetransformation.basicoperation.Flip.Direction;

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
    buttonPanel.setLayout(new GridLayout(0, 1, 5, 5)); // Vertical layout with uniform button sizes

    // Initialize buttons for all functions
    JButton loadButton = new JButton("Load Image");
    JButton saveButton = new JButton("Save Image");
    JButton brightenButton = new JButton("Brighten");
    JButton blurButton = new JButton("Blur");
    JButton sharpenButton = new JButton("Sharpen");
    JButton greyscaleButton = new JButton("Greyscale");
    JButton sepiaButton = new JButton("Sepia");
    JButton histogramButton = new JButton("Histogram");
    JButton horizontalFlipButton = new JButton("Horizontal Flip");
    JButton verticalFlipButton = new JButton("Vertical Flip");
    JButton redComponentButton = new JButton("Red Component");
    JButton greenComponentButton = new JButton("Green Component");
    JButton blueComponentButton = new JButton("Blue Component");
    JButton compressButton = new JButton("Compress");
    JButton rgbSplitButton = new JButton("RGB Split");
    JButton rgbCombineButton = new JButton("RGB Combine");
    JButton levelsAdjustButton = new JButton("Levels Adjust");
    JButton exitButton = new JButton("Exit");

    // Add action listeners to buttons
    loadButton.addActionListener(e -> controller.handleLoad(this,"load1"));
    saveButton.addActionListener(e -> controller.handleSave(new String[]{"save", "output.png", controller.getLatestKey()}));
    brightenButton.addActionListener(e -> handleBrighten());
    blurButton.addActionListener(e -> controller.applyOperation(new String[]{"blur", controller.getLatestKey(), "blurred"}));
    sharpenButton.addActionListener(e -> controller.applyOperation(new String[]{"sharpen", controller.getLatestKey(), "sharpened"}));
    greyscaleButton.addActionListener(e -> controller.applyOperation(new String[]{"greyscale", controller.getLatestKey(), "greyscale"}));
    sepiaButton.addActionListener(e -> controller.applyOperation(new String[]{"sepia", controller.getLatestKey(), "sepia"}));
    histogramButton.addActionListener(e -> controller.applyOperation(new String[]{"histogram", controller.getLatestKey(), "histogram"}));
    horizontalFlipButton.addActionListener(e -> controller.handleFlip(new String[]{"horizontal-flip", controller.getLatestKey(), "flipped-horizontal"}, Direction.HORIZONTAL));
    verticalFlipButton.addActionListener(e -> controller.handleFlip(new String[]{"vertical-flip", controller.getLatestKey(), "flipped-vertical"}, Direction.VERTICAL));
    redComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"red-component", controller.getLatestKey(), "red"}));
    greenComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"green-component", controller.getLatestKey(), "green"}));
    blueComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"blue-component", controller.getLatestKey(), "blue"}));
    compressButton.addActionListener(e -> handleCompression());
    rgbSplitButton.addActionListener(e -> handleRGBSplit());
    rgbCombineButton.addActionListener(e -> handleRGBCombine());
    levelsAdjustButton.addActionListener(e -> handleLevelsAdjust());
    exitButton.addActionListener(e -> System.exit(0)); // Exit the application

    // Add buttons to the button panel
    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(brightenButton);
    buttonPanel.add(blurButton);
    buttonPanel.add(sharpenButton);
    buttonPanel.add(greyscaleButton);
    buttonPanel.add(sepiaButton);
    buttonPanel.add(histogramButton);
    buttonPanel.add(horizontalFlipButton);
    buttonPanel.add(verticalFlipButton);
    buttonPanel.add(redComponentButton);
    buttonPanel.add(greenComponentButton);
    buttonPanel.add(blueComponentButton);
    buttonPanel.add(compressButton);
    buttonPanel.add(rgbSplitButton);
    buttonPanel.add(rgbCombineButton);
    buttonPanel.add(levelsAdjustButton);
    buttonPanel.add(exitButton);

    // Set up the image display area
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER); // Center the image
    imageLabel.setPreferredSize(new Dimension(400, 400)); // Set preferred size for the image area

    // Set up the histogram display area
    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(JLabel.CENTER); // Center the histogram
    histogramLabel.setPreferredSize(new Dimension(400, 200)); // Set preferred size for the histogram area

    // Create a panel for the image and histogram
    JPanel imagePanel = new JPanel();
    imagePanel.setLayout(new BorderLayout());
    imagePanel.add(imageLabel, BorderLayout.NORTH);
    imagePanel.add(histogramLabel, BorderLayout.SOUTH);

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
    }
  }

  private void handleCompression() {
    String ratio = JOptionPane.showInputDialog("Enter compression ratio (0-100):");
    if (ratio != null) {
      controller.handleCompression(new String[]{"compress", ratio, controller.getLatestKey(), "compressed"});
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

  private void handleLevelsAdjust() {
    String black = JOptionPane.showInputDialog("Enter black level (0-255):");
    String mid = JOptionPane.showInputDialog("Enter mid level (0-255):");
    String white = JOptionPane.showInputDialog("Enter white level (0-255):");
    if (black != null && mid != null && white != null) {
      controller.handleLevelsAdjust(new String[]{"levels-adjust", black, mid, white, controller.getLatestKey(), "adjusted"});
    }
  }

  public static void main(String[] args) {
    EnhancedImageModel i1 = new EnhancedImage();
    // Create an instance of the controller
    ImageGUIController controller = new ImageGUIController(new ImageController(i1));
    new ImageProcessorGUI(controller); // Launch the GUI with the controller
  }
}
