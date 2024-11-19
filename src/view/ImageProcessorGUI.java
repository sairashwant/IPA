package view;

import controller.ImageController;
import controller.ImageGUIController;
import model.EnhancedImage;
import model.imagetransformation.basicoperation.Flip.Direction;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcessorGUI extends JFrame {
  private ImageGUIController controller; // Reference to the controller
  private JLabel imageLabel; // To display the loaded image
  private JLabel histogramLabel; // To display the histogram
  private BufferedImage currentImage; // To store the current loaded image

  public ImageProcessorGUI(ImageGUIController controller) {
    this.controller = controller; // Initialize the controller

    // Set up the main frame
    setTitle("Image Processor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 800);
    setLayout(new BorderLayout());

    // Create a panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(0, 1, 5, 5)); // Single column layout

    // Initialize buttons
    JButton loadButton = new JButton("Load Image");
    JButton saveButton = new JButton("Save Image");
    JButton undoButton = new JButton("Undo");
    JButton originalImageButton = new JButton("Revert to Original Image");
    JButton brightenButton = new JButton("Brighten");
    JButton horizontalFlipButton = new JButton("Horizontal Flip");
    JButton verticalFlipButton = new JButton("Vertical Flip");
    JButton redComponentButton = new JButton("Red Component");
    JButton greenComponentButton = new JButton("Green Component");
    JButton blueComponentButton = new JButton("Blue Component");
    JButton compressButton = new JButton("Compress");
    JButton blurButton = new JButton("Blur");
    JButton sharpenButton = new JButton("Sharpen");
    JButton greyscaleButton = new JButton("Greyscale");
    JButton sepiaButton = new JButton("Sepia");
    JButton levelsAdjustButton = new JButton("Levels Adjust");
    JButton exitButton = new JButton("Exit");

    // Add action listeners
    loadButton.addActionListener(e -> controller.handleLoad(this, "load1"));
    saveButton.addActionListener(e -> controller.handleSave(new String[]{"save", "output.png", controller.getLatestKey()}));
    brightenButton.addActionListener(e -> handleBrighten());
    blurButton.addActionListener(e -> handlePopupOperation("Blur"));
    sharpenButton.addActionListener(e -> handlePopupOperation("Sharpen"));
    greyscaleButton.addActionListener(e -> handlePopupOperation("Greyscale"));
    sepiaButton.addActionListener(e -> handlePopupOperation("Sepia"));
    levelsAdjustButton.addActionListener(e -> handleLevelsAdjust());
    horizontalFlipButton.addActionListener(e -> controller.handleFlip(new String[]{"horizontal-flip", controller.getLatestKey(), "flipped-horizontal"}, Direction.HORIZONTAL));
    verticalFlipButton.addActionListener(e -> controller.handleFlip(new String[]{"vertical-flip", controller.getLatestKey(), "flipped-vertical"}, Direction.VERTICAL));
    redComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"red-component", controller.getLatestKey(), "red"}));
    greenComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"green-component", controller.getLatestKey(), "green"}));
    blueComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"blue-component", controller.getLatestKey(), "blue"}));
    compressButton.addActionListener(e -> handleCompression());
    undoButton.addActionListener(e -> undo());
    exitButton.addActionListener(e -> System.exit(0));
    originalImageButton.addActionListener(e -> handleShowOriginalImage()); // Action listener for the new button

    // Add buttons to the button panel
    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(undoButton);
    buttonPanel.add(originalImageButton); // Add the new button
    buttonPanel.add(brightenButton);
    buttonPanel.add(horizontalFlipButton);
    buttonPanel.add(verticalFlipButton);
    buttonPanel.add(redComponentButton);
    buttonPanel.add(greenComponentButton);
    buttonPanel.add(blueComponentButton);
    buttonPanel.add(compressButton);
    buttonPanel.add(blurButton);
    buttonPanel.add(sharpenButton);
    buttonPanel.add(greyscaleButton);
    buttonPanel.add(sepiaButton);
    buttonPanel.add(levelsAdjustButton);
    buttonPanel.add(exitButton);

    // Set up the image display area
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);

    // Set up the histogram display area
    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(JLabel.CENTER);
    histogramLabel.setPreferredSize(new Dimension(75, 75));

    // Wrap the image and histogram labels in JScrollPane to enable scrolling
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(600, 600));
    imageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    imageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    JScrollPane histogramScrollPane = new JScrollPane(histogramLabel);
    histogramScrollPane.setPreferredSize(new Dimension(400, 400));
    histogramScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    histogramScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    // Create a panel for the image and histogram with BorderLayout
    JPanel imagePanel = new JPanel();
    imagePanel.setLayout(new BorderLayout(10, 10));
    imagePanel.add(imageScrollPane, BorderLayout.CENTER);
    imagePanel.add(histogramScrollPane, BorderLayout.EAST);

    // Add panels to the main frame
    add(buttonPanel, BorderLayout.WEST);
    add(imagePanel, BorderLayout.CENTER);

    // Set the frame visibility
    setVisible(true);
  }


  public void undo(){
    controller.handleUndo();
  }
  // Method to display the loaded image
  public void displayImage(BufferedImage image) {
    currentImage = image; // Save the current image

    // Set the image without resizing and let JScrollPane handle scrolling
    imageLabel.setIcon(new ImageIcon(image));

    // Ensure the JScrollPane allows scrolling if needed
    handleHistogram();
  }

  // Method to display the histogram
  public void displayHistogram(BufferedImage histogram) {
    histogramLabel.setIcon(new ImageIcon(histogram));
  }

  // Method to handle displaying the original image
  private void handleShowOriginalImage() {
    controller.handleShowOriginalImage();// Fetch the original image from the controller
  }

  // Helper methods for advanced operations
  private void handleBrighten() {
    String factor = JOptionPane.showInputDialog("Enter brightness factor:");
    if (factor != null) {
      controller.handleBrighten(new String[]{"brighten", factor, controller.getLatestKey(), "brightened"});
    }
  }

  private void handlePopupOperation(String operation) {
    int isSplit = JOptionPane.showConfirmDialog(this, "Do you want to use split and transform?", operation, JOptionPane.YES_NO_OPTION);

    if (isSplit == JOptionPane.YES_OPTION) {
      String input = JOptionPane.showInputDialog("Enter split percentage (0-100):");
      try {
        int value = Integer.parseInt(input);
        String percentage = input;
        if (value < 0 || value > 100) {
          throw new NumberFormatException("Percentage out of range");
        }
        controller.handleSplit(new String[]{operation.toLowerCase(), percentage});
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid percentage between 0 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    } else if (isSplit == JOptionPane.NO_OPTION) {
      controller.applyOperation(new String[]{operation.toLowerCase(), controller.getLatestKey(), operation.toLowerCase()});
    }
  }

  private void handleHistogram() {
    controller.applyHistogram(new String[]{"histogram"});
  }

  private void handleCompression() {
    String ratio = JOptionPane.showInputDialog("Enter compression ratio (0-100):");
    if (ratio != null) {
      controller.handleCompression(new String[]{"compress", ratio, controller.getLatestKey(), "compressed"});
    }
  }

  private void handleLevelsAdjust() {
    int isSplit = JOptionPane.showConfirmDialog(this, "Do you want to use split for levels adjust?", "Levels Adjust", JOptionPane.YES_NO_OPTION);

    if (isSplit == JOptionPane.YES_OPTION) {
      handleLevelsAdjustWithSplit();
    } else if (isSplit == JOptionPane.NO_OPTION) {
      handleLevelsAdjustWithoutSplit();
    }
  }

  private void handleLevelsAdjustWithSplit() {
    String percentage = JOptionPane.showInputDialog("Enter split percentage (0-100):");
    if (percentage != null) {
      try {
        int value = Integer.parseInt(percentage);
        if (value < 0 || value > 100) {
          throw new NumberFormatException("Percentage out of range");
        }
        // Proceed to handle the levels adjust with split
        String black = JOptionPane.showInputDialog("Enter black level (0-255):");
        String mid = JOptionPane.showInputDialog("Enter mid level (0-255):");
        String white = JOptionPane.showInputDialog("Enter white level (0-255):");

        if (black != null && mid != null && white != null) {
          // Pass the split percentage and the level values to the controller
          controller.handleLevelsAdjust(new String[]{black, mid, white, percentage});
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid percentage between 0 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void handleLevelsAdjustWithoutSplit() {
    String black = JOptionPane.showInputDialog("Enter black level (0-255):");
    String mid = JOptionPane.showInputDialog("Enter mid level (0-255):");
    String white = JOptionPane.showInputDialog("Enter white level (0-255):");

    if (black != null && mid != null && white != null) {
      controller.handleLevelsAdjust(new String[]{black, mid, white});
    }
  }
  public static void main(String[] args) {
    // Create an instance of the controller
    EnhancedImage i1= new EnhancedImage();
    ImageController imageController = new ImageController(i1);
    ImageGUIController guiController = new ImageGUIController(i1, imageController);

    // Create the GUI and pass the controller
    SwingUtilities.invokeLater(() -> {
      new ImageProcessorGUI(guiController); // Initialize the GUI with the controller
    });
  }
}


