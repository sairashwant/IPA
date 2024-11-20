package view;

import controller.ImageController;
import controller.ImageGUIController;
import model.EnhancedImage;
import model.imagetransformation.basicoperation.Flip.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcessorGUI extends JFrame {
  private final ImageGUIController controller;
  private JLabel imageLabel;
  private JLabel histogramLabel;
  private BufferedImage currentImage;
  private final JCheckBox previewBlurCheckbox;
  private final JCheckBox previewSharpenCheckbox;
  private final JCheckBox previewSepiaCheckbox;
  private final JCheckBox previewGreyscaleCheckbox;
  private final JCheckBox previewLevelsAdjustCheckbox;

  public ImageProcessorGUI(ImageGUIController controller) {
    this.controller = controller;

    // Frame setup
    setTitle("Image Processor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 800);
    setLayout(new BorderLayout());

    // Buttons and preview checkboxes
    JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));

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

    previewBlurCheckbox = new JCheckBox("Preview");
    previewSharpenCheckbox = new JCheckBox("Preview");
    previewSepiaCheckbox = new JCheckBox("Preview");
    previewGreyscaleCheckbox = new JCheckBox("Preview");
    previewLevelsAdjustCheckbox = new JCheckBox("Preview");

    // Button actions
    loadButton.addActionListener(e -> controller.handleLoad(this, "load1"));
    saveButton.addActionListener(e -> controller.handleSave(new String[]{"save", "output.png", controller.getLatestKey()}));
    undoButton.addActionListener(e -> controller.handleUndo());
    originalImageButton.addActionListener(e -> controller.handleShowOriginalImage());
    brightenButton.addActionListener(e -> handleBrighten());
    horizontalFlipButton.addActionListener(e -> handleFlip(Direction.HORIZONTAL));
    verticalFlipButton.addActionListener(e -> handleFlip(Direction.VERTICAL));
    redComponentButton.addActionListener(e -> handleComponent("red-component"));
    greenComponentButton.addActionListener(e -> handleComponent("green-component"));
    blueComponentButton.addActionListener(e -> handleComponent("blue-component"));
    compressButton.addActionListener(e -> handleCompression());
    blurButton.addActionListener(e -> handleOperationWithPreview("Blur"));
    sharpenButton.addActionListener(e -> handleOperationWithPreview("Sharpen"));
    greyscaleButton.addActionListener(e -> handleOperationWithPreview("Greyscale"));
    sepiaButton.addActionListener(e -> handleOperationWithPreview("Sepia"));
    levelsAdjustButton.addActionListener(e -> handleLevelsAdjust());
    exitButton.addActionListener(e -> System.exit(0));

    // Add buttons to panel
    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(undoButton);
    buttonPanel.add(originalImageButton);
    buttonPanel.add(horizontalFlipButton);
    buttonPanel.add(verticalFlipButton);
    buttonPanel.add(redComponentButton);
    buttonPanel.add(greenComponentButton);
    buttonPanel.add(blueComponentButton);
    buttonPanel.add(compressButton);
    buttonPanel.add(createOperationWithPreviewPanel(blurButton, previewBlurCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(sharpenButton, previewSharpenCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(sepiaButton, previewSepiaCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(greyscaleButton, previewGreyscaleCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(levelsAdjustButton, previewLevelsAdjustCheckbox));
    buttonPanel.add(exitButton);

    // Image display area
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);

    // Histogram display area
    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(JLabel.CENTER);
    histogramLabel.setPreferredSize(new Dimension(400, 400));

    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    JScrollPane histogramScrollPane = new JScrollPane(histogramLabel);

    // Image and histogram panel
    JPanel imagePanel = new JPanel(new BorderLayout());
    imagePanel.add(imageScrollPane, BorderLayout.CENTER);
    imagePanel.add(histogramScrollPane, BorderLayout.EAST);

    // Add to frame
    add(new JScrollPane(buttonPanel), BorderLayout.WEST);
    add(imagePanel, BorderLayout.CENTER);

    setVisible(true);
  }

  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(200, 50));
    return button;
  }

  private JPanel createOperationWithPreviewPanel(JButton operationButton, JCheckBox previewCheckbox) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(operationButton);
    panel.add(previewCheckbox);
    return panel;
  }

  public void displayImage(BufferedImage image) {
    currentImage = image;
    imageLabel.setIcon(new ImageIcon(image));
    controller.applyHistogram(new String[]{"histogram"});
  }

  public void displayHistogram(BufferedImage histogram) {
    histogramLabel.setIcon(new ImageIcon(histogram));
  }

  private void handleBrighten() {
    String factor = JOptionPane.showInputDialog("Enter brightness factor:");
    if (factor != null) {
      controller.handleBrighten(new String[]{"brighten", factor, controller.getLatestKey(), "brightened"});
    }
  }

  private void handleFlip(Direction direction) {
    controller.handleFlip(new String[]{"flip", controller.getLatestKey(), direction.name().toLowerCase()}, direction);
  }

  private void handleComponent(String component) {
    controller.applyOperation(new String[]{component, controller.getLatestKey(), component});
  }

  private void handleOperationWithPreview(String operation) {
    JCheckBox previewCheckbox = getPreviewCheckboxForOperation(operation);
    if (previewCheckbox.isSelected()) {
      int splitPercentage = getSplitPercentage();
      // Check if the returned split percentage is valid
      if (splitPercentage != -1) {
        controller.handleSplit(new String[]{operation.toLowerCase(), String.valueOf(splitPercentage)});
      } else {
        // Handle invalid or canceled input (no operation)
        JOptionPane.showMessageDialog(this, "Operation canceled", "Error", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      controller.applyOperation(new String[]{operation.toLowerCase(), controller.getLatestKey(), operation.toLowerCase()});
    }
  }


  private JCheckBox getPreviewCheckboxForOperation(String operation) {
    switch (operation) {
      case "Blur":
        return previewBlurCheckbox;
      case "Sharpen":
        return previewSharpenCheckbox;
      case "Sepia":
        return previewSepiaCheckbox;
      case "Greyscale":
        return previewGreyscaleCheckbox;
      case "Levels Adjust":
        return previewLevelsAdjustCheckbox;
      default:
        return null;
    }
  }

  private int getSplitPercentage() {
    // Create the slider
    JSlider slider = new JSlider(0, 100, 50);
    slider.setMajorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

    // Create the "Close" button
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(e -> {
      // Close the dialog without performing any further action
      SwingUtilities.getWindowAncestor(closeButton).dispose();
    });

    // Create a JPanel to hold the slider and the button
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(slider, BorderLayout.CENTER);
    panel.add(closeButton, BorderLayout.SOUTH);

    // Create the JOptionPane with the panel
    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);

    // Create the dialog
    JDialog dialog = optionPane.createDialog(this, "Select Split Percentage");

    // Add a WindowListener to handle window closing correctly (close without error)
    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        // Simply close the dialog and prevent any further action
        dialog.dispose();
      }
    });

    // Display the dialog
    dialog.setVisible(true);

    // Return the value of the slider when the user closes the dialog
    int splitValue = slider.getValue();

    // Return a valid value (0-100) or -1 if not a valid interaction
    return splitValue != 50 ? splitValue : -1; // Only return a valid split percentage if it's not the default value
  }



  public void showPreview(BufferedImage image, String operation) {
    if (image != null) {
      JLabel previewLabel = new JLabel(new ImageIcon(image));
      JScrollPane scrollPane = new JScrollPane(previewLabel);
      scrollPane.setPreferredSize(new Dimension(600, 600));

      // Custom dialog with Apply and Cancel buttons
      int result = JOptionPane.showOptionDialog(
          this,
          scrollPane,
          "Preview",
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.PLAIN_MESSAGE,
          null,
          new String[]{"Apply", "Cancel"},
          "Apply"
      );

      if (result == JOptionPane.YES_OPTION) { // Apply button
        controller.applyOperation(new String[]{
            operation.toLowerCase(), controller.getLatestKey(), operation.toLowerCase()
        });
      }
      // Do nothing on Cancel
    } else {
      JOptionPane.showMessageDialog(this, "Unable to generate preview.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void handleLevelsAdjust() {
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
    EnhancedImage model = new EnhancedImage();
    ImageController imageController = new ImageController(model);
    ImageGUIController guiController = new ImageGUIController(model, imageController);

    SwingUtilities.invokeLater(() -> new ImageProcessorGUI(guiController));
  }
}
