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
  private JCheckBox previewBlurCheckbox, previewSharpenCheckbox, previewSepiaCheckbox, previewGreyscaleCheckbox, previewLevelsAdjustCheckbox;

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
    JButton loadButton = createButton("Load Image");
    JButton saveButton = createButton("Save Image");
    JButton undoButton = createButton("Undo");
    JButton originalImageButton = createButton("Revert to Original Image");
    JButton brightenButton = createButton("Brighten");
    JButton horizontalFlipButton = createButton("Horizontal Flip");
    JButton verticalFlipButton = createButton("Vertical Flip");
    JButton redComponentButton = createButton("Red Component");
    JButton greenComponentButton = createButton("Green Component");
    JButton blueComponentButton = createButton("Blue Component");
    JButton compressButton = createButton("Compress");
    JButton blurButton = createButton("Blur");
    JButton sharpenButton = createButton("Sharpen");
    JButton greyscaleButton = createButton("Greyscale");
    JButton sepiaButton = createButton("Sepia");
    JButton levelsAdjustButton = createButton("Levels Adjust");
    JButton exitButton = createButton("Exit");

    // Initialize preview toggle radio buttons for each operation
    previewBlurCheckbox = new JCheckBox("Preview");
    previewSharpenCheckbox = new JCheckBox("Preview");
    previewSepiaCheckbox = new JCheckBox("Preview");
    previewGreyscaleCheckbox = new JCheckBox("Preview");
    previewLevelsAdjustCheckbox = new JCheckBox("Preview");

    // Group the preview radio buttons together
    ButtonGroup previewGroup = new ButtonGroup();
    previewGroup.add(previewBlurCheckbox);
    previewGroup.add(previewSharpenCheckbox);
    previewGroup.add(previewSepiaCheckbox);
    previewGroup.add(previewGreyscaleCheckbox);
    previewGroup.add(previewLevelsAdjustCheckbox);

    // Add action listeners
    loadButton.addActionListener(e -> controller.handleLoad(this, "load1"));
    saveButton.addActionListener(e -> controller.handleSave(new String[]{"save", "output.png", controller.getLatestKey()}));
    brightenButton.addActionListener(e -> handleBrighten());
    blurButton.addActionListener(e -> handleOperationWithPreview("Blur"));
    sharpenButton.addActionListener(e -> handleOperationWithPreview("Sharpen"));
    greyscaleButton.addActionListener(e -> handleOperationWithPreview("Greyscale"));
    sepiaButton.addActionListener(e -> handleOperationWithPreview("Sepia"));
    levelsAdjustButton.addActionListener(e -> handleLevelsAdjust());
    horizontalFlipButton.addActionListener(e -> controller.handleFlip(new String[]{"horizontal-flip", controller.getLatestKey(), "flipped-horizontal"}, Direction.HORIZONTAL));
    verticalFlipButton.addActionListener(e -> controller.handleFlip(new String[]{"vertical-flip", controller.getLatestKey(), "flipped-vertical"}, Direction.VERTICAL));
    redComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"red-component", controller.getLatestKey(), "red"}));
    greenComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"green-component", controller.getLatestKey(), "green"}));
    blueComponentButton.addActionListener(e -> controller.applyOperation(new String[]{"blue-component", controller.getLatestKey(), "blue"}));
    compressButton.addActionListener(e -> handleCompression());
    undoButton.addActionListener(e -> controller.handleUndo());
    exitButton.addActionListener(e -> System.exit(0));
    originalImageButton.addActionListener(e -> controller.handleShowOriginalImage()); // Action listener for the new button

    // Add buttons to the button panel
    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(undoButton);
    buttonPanel.add(originalImageButton); // Add the new button

    // Group operation buttons with preview toggle buttons in a sub-panel
    buttonPanel.add(createOperationWithPreviewPanel(blurButton, previewBlurCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(sharpenButton, previewSharpenCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(sepiaButton, previewSepiaCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(greyscaleButton, previewGreyscaleCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(levelsAdjustButton, previewLevelsAdjustCheckbox));

    buttonPanel.add(horizontalFlipButton);
    buttonPanel.add(verticalFlipButton);
    buttonPanel.add(redComponentButton);
    buttonPanel.add(greenComponentButton);
    buttonPanel.add(blueComponentButton);
    buttonPanel.add(compressButton);
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

  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(200, 50)); // Set a fixed size for buttons
    button.setMinimumSize(new Dimension(200, 50));  // Ensure the minimum size for buttons
    button.setMaximumSize(new Dimension(200, 50));  // Ensure the maximum size for buttons
    return button;
  }

  private JPanel createOperationWithPreviewPanel(JButton operationButton, JCheckBox previewButton) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    panel.add(operationButton);
    panel.add(previewButton);
    return panel;
  }

  public void undo() {
    controller.handleUndo();
  }

  // Method to display the loaded image
  public void displayImage(BufferedImage image) {
    currentImage = image;
    imageLabel.setIcon(new ImageIcon(image));
    controller.applyHistogram(new String[]{"histogram"});
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

  private void handleOperationWithPreview(String operation) {
    JCheckBox previewButton = getPreviewButtonForOperation(operation);
    if (previewButton != null && previewButton.isSelected()) {
      // Generate the preview and show it in a popup
      BufferedImage previewImage = controller.getPreviewImage(operation);
      showPreviewInPopup(previewImage);
    } else {
      controller.applyOperation(new String[]{operation.toLowerCase(), controller.getLatestKey(), operation.toLowerCase()});
    }
  }

  private JCheckBox getPreviewButtonForOperation(String operation) {
    switch (operation.toLowerCase()) {
      case "blur":
        return previewBlurCheckbox;
      case "sharpen":
        return previewSharpenCheckbox;
      case "sepia":
        return previewSepiaCheckbox;
      case "greyscale":
        return previewGreyscaleCheckbox;
      case "levels adjust":
        return previewLevelsAdjustCheckbox;
      default:
        return null;
    }
  }

  private void showPreviewInPopup(BufferedImage previewImage) {
    // Create a new frame to show the preview image
    JFrame previewFrame = new JFrame("Preview");
    previewFrame.setSize(600, 600);
    JLabel previewLabel = new JLabel(new ImageIcon(previewImage));
    previewFrame.add(previewLabel, BorderLayout.CENTER);
    previewFrame.setVisible(true);
  }

  private void handleLevelsAdjust() {
    int isSplit = JOptionPane.showConfirmDialog(this, "Do you want to use split for levels adjust?", "Levels Adjust", JOptionPane.YES_NO_OPTION);

    if (isSplit == JOptionPane.YES_OPTION) {
      handleLevelsAdjustWithSplit();
    } else {
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


  private void handleCompression() {
    String ratio = JOptionPane.showInputDialog("Enter compression ratio (0-100):");
    if (ratio != null) {
      controller.handleCompression(new String[]{"compress", ratio, controller.getLatestKey(), "compressed"});
    }
  }

  public static void main(String[] args) {
    // Create an instance of the controller
    EnhancedImage i1 = new EnhancedImage();
    ImageController imageController = new ImageController(i1);
    ImageGUIController guiController = new ImageGUIController(i1, imageController);

    // Create the GUI and pass the controller
    SwingUtilities.invokeLater(() -> {
      new ImageProcessorGUI(guiController); // Initialize the GUI with the controller
    });
  }
}
