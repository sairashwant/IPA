package view;

import controller.ImageGUIController;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import model.imagetransformation.basicoperation.Flip.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcessorGUI extends JFrame implements ImageProcessorGUIInterface {

  private final ImageGUIController controller;
  private final JLabel imageLabel;
  private final JLabel histogramLabel;
  private final JCheckBox previewBlurCheckbox;
  private final JCheckBox previewSharpenCheckbox;
  private final JCheckBox previewSepiaCheckbox;
  private final JCheckBox previewGreyscaleCheckbox;
  private final JCheckBox previewLevelsAdjustCheckbox;
  String black;
  String white;
  String mid;


  public ImageProcessorGUI(ImageGUIController controller) {
    this.controller = controller;

    // Frame setup
    setTitle("Image Processor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 800);
    setLayout(new BorderLayout());

    // Buttons and preview checkboxes
    // Modified button panel to use GridLayout (vertical layout)
    JPanel buttonPanel = new JPanel(
        new GridLayout(0, 1, 10, 10)); // GridLayout for vertical arrangement

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
    JButton colorCorrectionButton = createButton("Color Correction");
    JButton downscale = createButton("Downscale");
    JButton exitButton = createButton("Exit");

    previewBlurCheckbox = new JCheckBox("Preview");
    previewSharpenCheckbox = new JCheckBox("Preview");
    previewSepiaCheckbox = new JCheckBox("Preview");
    previewGreyscaleCheckbox = new JCheckBox("Preview");
    previewLevelsAdjustCheckbox = new JCheckBox("Preview");

    loadButton.addActionListener(e -> handleLoad(this, "load1"));
    saveButton.addActionListener(
        e -> handleSave(new String[]{"save", "output.png", controller.getLatestKey()}));
    undoButton.addActionListener(e -> controller.handleUndo());
    originalImageButton.addActionListener(e -> controller.handleShowOriginalImage());
    brightenButton.addActionListener(e -> handleBrighten());
    horizontalFlipButton.addActionListener(e -> controller.handleFlip(
        new String[]{"flip", controller.getLatestKey(), "HORIZONTAL".toLowerCase()},
        Direction.HORIZONTAL));
    verticalFlipButton.addActionListener(e -> controller.handleFlip(
        new String[]{"flip", controller.getLatestKey(), "VERTICAL".toLowerCase()},
        Direction.VERTICAL));
    redComponentButton.addActionListener(e -> controller.applyOperation(
        new String[]{"red-component", controller.getLatestKey(), "red-component"}));
    greenComponentButton.addActionListener(e -> controller.applyOperation(
        new String[]{"green-component", controller.getLatestKey(), "green-component"}));
    blueComponentButton.addActionListener(e -> controller.applyOperation(
        new String[]{"blue-component", controller.getLatestKey(), "blue-component"}));
    compressButton.addActionListener(e -> handleCompression());
    blurButton.addActionListener(e -> handleOperationWithPreview("Blur"));
    sharpenButton.addActionListener(e -> handleOperationWithPreview("Sharpen"));
    greyscaleButton.addActionListener(e -> handleOperationWithPreview("Greyscale"));
    sepiaButton.addActionListener(e -> handleOperationWithPreview("Sepia"));
    levelsAdjustButton.addActionListener(e -> handleLevelAdjustWithPreview("levels-adjust"));
    colorCorrectionButton.addActionListener(e -> controller.applyOperation(
        new String[]{"color-correction", controller.getLatestKey(), "color-correction"}));
    downscale.addActionListener(e -> handleDownscale());
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
    buttonPanel.add(colorCorrectionButton);
    buttonPanel.add(downscale);
    buttonPanel.add(createOperationWithPreviewPanel(blurButton, previewBlurCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(sharpenButton, previewSharpenCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(sepiaButton, previewSepiaCheckbox));
    buttonPanel.add(createOperationWithPreviewPanel(greyscaleButton, previewGreyscaleCheckbox));
    buttonPanel.add(
        createOperationWithPreviewPanel(levelsAdjustButton, previewLevelsAdjustCheckbox));
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
    // Modified code to make button panel scrollable (after modification):
    JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
    buttonScrollPane.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // Enable vertical scroll
    add(buttonScrollPane, BorderLayout.WEST);

    add(imagePanel, BorderLayout.CENTER);

    setVisible(true);
  }

  // Modified createButton method (after modification):
  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(200, 40));  // Adjusted button size for better fit
    return button;
  }

  // Modified createOperationWithPreviewPanel method (after modification):
  private JPanel createOperationWithPreviewPanel(JButton operationButton,
      JCheckBox previewCheckbox) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel for button and checkbox
    panel.add(operationButton);
    panel.add(previewCheckbox);
    return panel;
  }



  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void displayImage(BufferedImage image) {
    imageLabel.setIcon(new ImageIcon(image));
    controller.applyHistogram(new String[]{"histogram"});
  }

  @Override
  public void displayHistogram(BufferedImage histogram) {
    histogramLabel.setIcon(new ImageIcon(histogram));
  }

  private void handleBrighten() {
    String factor = JOptionPane.showInputDialog("Enter brightness factor:");
    if (factor != null) {
      controller.handleBrighten(
          new String[]{"brighten", factor, controller.getLatestKey(), "brightened"});
    }
  }

  private void handleDownscale() {
    String width = JOptionPane.showInputDialog("Enter new width:");
    String height = JOptionPane.showInputDialog("Enter new height:");
    controller.handleDownscale(width, height);
  }

  private void handleOperationWithPreview(String operation) {
    JCheckBox previewCheckbox = getPreviewCheckboxForOperation(operation);
    if (previewCheckbox.isSelected()) {
      int splitPercentage = getSplitPercentage();
      // Check if the returned split percentage is valid
      if (splitPercentage != -1) {
        if (!operation.equals("levels-adjust")) {
          controller.handleSplit(
              new String[]{operation.toLowerCase(), String.valueOf(splitPercentage)});
        } else {
          handleLevelsAdjustSplit(splitPercentage);
        }
      } else {
        JOptionPane.showMessageDialog(this, "Operation canceled", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    } else {
      if (!operation.equals("levels-adjust")) {
        controller.handleSplit(new String[]{operation.toLowerCase()});
      } else {
        handleLevelsAdjust();
      }
    }
  }

  private void handleLevelAdjustWithPreview(String operation) {
    JCheckBox previewCheckbox = getPreviewCheckboxForOperation(operation);
    if (previewCheckbox.isSelected()) {
      int splitPercentage = getSplitPercentage();
      // Check if the returned split percentage is valid
      if (splitPercentage != -1) {
        handleLevelsAdjustSplit(splitPercentage);
      } else {
        JOptionPane.showMessageDialog(this, "Operation canceled", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    } else {
      handleLevelsAdjust();
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
      case "levels-adjust":
        return previewLevelsAdjustCheckbox;
      default:
        return null;
    }
  }

  private int getSplitPercentage() {
    JSlider slider = new JSlider(0, 100, 50);
    slider.setMajorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

    // Create a panel to hold the slider
    JPanel sliderPanel = new JPanel(new BorderLayout());
    sliderPanel.add(slider, BorderLayout.CENTER);

    // Create the buttons
    JButton okButton = new JButton("OK");
    JButton closeButton = new JButton("Close");

    // Create a sub-panel for buttons with right alignment
    JPanel buttonPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(0, 10, 0, 5); // Adjust margins between buttons
    buttonPanel.add(okButton, gbc);

    gbc.gridx = 1;
    buttonPanel.add(closeButton, gbc);

    // Combine slider and button panels
    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.add(sliderPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Create a JDialog instead of JOptionPane
    JDialog dialog = new JDialog(this, "Select Split Percentage", true);
    dialog.getContentPane().add(mainPanel);
    dialog.pack();
    dialog.setLocationRelativeTo(this);

    final int[] result = {50}; // Default value
    final boolean[] confirmed = {false}; // Track if the user clicked OK

    // Add action listeners
    okButton.addActionListener(e -> {
      result[0] = slider.getValue();
      confirmed[0] = true;
      dialog.dispose();
    });

    closeButton.addActionListener(e -> dialog.dispose());

    // Show the dialog
    dialog.setVisible(true);

    // Return the result or -1 if canceled
    return confirmed[0] ? result[0] : -1;
  }

  @Override
  public void addWindowListenerToGUI() {
    // Add a WindowListener to the GUI to detect when the user is about to close the application
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        String latest = controller.getLatestKey();
        // Before closing, prompt the user to save if there are unsaved changes
        if (latest != null && !latest.isEmpty()) {
          int option = JOptionPane.showConfirmDialog(null,
              "Do you want to save the current image before closing?",
              "Save Image", JOptionPane.YES_NO_OPTION);

          if (option == JOptionPane.YES_OPTION) {
            // Trigger the save functionality
            handleSave(new String[]{"save", "output.png", latest});  // Save the current image
          } else if (option == JOptionPane.NO_OPTION) {
            // Close without saving
            dispose();  // Close the application
          }
          // If the user selects CANCEL, do nothing (keep the application open)
        } else {
          // If there are no unsaved changes, simply close the application
          dispose();
        }
      }
    });
  }


  public void showPreview(BufferedImage image, String operation) {
    if (image != null) {
      JLabel previewLabel = new JLabel(new ImageIcon(image));
      JScrollPane scrollPane = new JScrollPane(previewLabel);
      scrollPane.setPreferredSize(new Dimension(600, 600));

      // Create buttons for Apply, Back, and Cancel
      JButton applyButton = new JButton("Apply");
      JButton backButton = new JButton("Back");
      JButton cancelButton = new JButton("Cancel");

      // Create a panel for buttons
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(applyButton);
      buttonPanel.add(backButton);
      buttonPanel.add(cancelButton);

      // Combine scrollPane and buttonPanel in a main panel
      JPanel mainPanel = new JPanel(new BorderLayout());
      mainPanel.add(scrollPane, BorderLayout.CENTER);
      mainPanel.add(buttonPanel, BorderLayout.SOUTH);

      // Create a dialog
      JDialog dialog = new JDialog(this, "Preview", true);
      dialog.getContentPane().add(mainPanel);
      dialog.pack();
      dialog.setLocationRelativeTo(this);

      // Action listener for Apply button
      if (operation != "levels-adjust") {
        applyButton.addActionListener(e -> {
          controller.applyOperation(new String[]{
              operation.toLowerCase(), controller.getLatestKey(), operation.toLowerCase()
          });
          dialog.dispose(); // Close the dialog after applying
        });
      } else {
        applyButton.addActionListener(e -> {
          controller.handleLevelsAdjust(new String[]{black, mid, white});
          dialog.dispose(); // Close the dialog after applying
        });
      }

      // Action listener for Back button
      backButton.addActionListener(e -> {
        dialog.dispose(); // Close the preview dialog
        int splitPercentage = getSplitPercentage(); // Reopen the slider to choose split percentage
        if (splitPercentage != -1) {
          controller.handleSplit(
              new String[]{operation.toLowerCase(), String.valueOf(splitPercentage)});
        }
      });

      // Action listener for Cancel button
      cancelButton.addActionListener(e -> dialog.dispose()); // Close the dialog on cancel

      // Show the dialog
      dialog.setVisible(true);
    } else {
      JOptionPane.showMessageDialog(this, "Unable to generate preview.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void handleLevelsAdjust() {
    black = JOptionPane.showInputDialog("Enter black level (0-255):");
    mid = JOptionPane.showInputDialog("Enter mid level (0-255):");
    white = JOptionPane.showInputDialog("Enter white level (0-255):");

    if (black != null && mid != null && white != null) {
      controller.handleLevelsAdjust(new String[]{black, mid, white});
    }
  }

  private void handleLevelsAdjustSplit(int percentage) {
    // Collect black, mid, and white levels
    String black = JOptionPane.showInputDialog("Enter black level (0-255):");
    String mid = JOptionPane.showInputDialog("Enter mid level (0-255):");
    String white = JOptionPane.showInputDialog("Enter white level (0-255):");
    String percentageStr = String.valueOf(percentage);
    if (black != null && mid != null && white != null) {
      controller.handleLevelsAdjust(new String[]{black, mid, white, percentageStr});
    }
  }

  private void handleCompression() {
    String ratio = JOptionPane.showInputDialog("Enter compression ratio (0-100):");
    if (ratio != null) {
      controller.handleCompression(
          new String[]{"compress", ratio, controller.getLatestKey(), "compressed"});
    }
  }

  public void showPreviewLevelAdj(BufferedImage image, String[] args) {
    if (image == null) {
      JOptionPane.showMessageDialog(this, "Unable to generate preview. Image is null.",
          "Error", JOptionPane.ERROR_MESSAGE);
      return; // Exit if image is null
    }

    // Create the preview label with the image
    JLabel previewLabel = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(previewLabel);
    scrollPane.setPreferredSize(new Dimension(600, 600));

    // Create buttons for Apply, Back, and Cancel
    JButton applyButton = new JButton("Apply");
    JButton backButton = new JButton("Back");
    JButton cancelButton = new JButton("Cancel");

    // Create a panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(applyButton);
    buttonPanel.add(backButton);
    buttonPanel.add(cancelButton);

    // Combine scrollPane and buttonPanel in a main panel
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(scrollPane, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Create a dialog
    JDialog dialog = new JDialog(this, "Preview Levels Adjustment", true);
    dialog.getContentPane().add(mainPanel);
    dialog.pack();
    dialog.setLocationRelativeTo(this);
    String[] updatedString = new String[]{args[0],args[1],args[2]};
    // Add action listener for Apply button
    applyButton.addActionListener(e -> {
      controller.handleLevelsAdjust(updatedString); // Pass args to controller
      dialog.dispose(); // Close the dialog after applying
    });

    // Add action listener for Back button
    backButton.addActionListener(e -> {
      dialog.dispose(); // Close the preview dialog
      int splitPercentage = getSplitPercentage();
      String[] backButtonString = new String[]{args[0],args[1],args[2],String.valueOf(splitPercentage)};
      if (splitPercentage != -1) {
        controller.handleLevelsAdjust(backButtonString);
      }
    });

    // Add action listener for Cancel button
    cancelButton.addActionListener(e -> dialog.dispose()); // Close the dialog on cancel

    // Display the dialog
    dialog.setVisible(true);
  }

  private void handleLoad(ImageProcessorGUI gui, String key) {
    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filename = selectedFile.getAbsolutePath();
      controller.handleLoad(this, key, filename);
    }
  }

  private void handleSave(String[] args) {
    if (controller.getLatestKey() == null || controller.getLatestKey().isEmpty()) {
      showError("No image loaded to save. Please load an image first.");
      return;
    }

    // Create a JFileChooser to let the user choose a directory and file name
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image");

    // Add file filters for different image formats
    javax.swing.filechooser.FileFilter pngFilter = new javax.swing.filechooser.FileNameExtensionFilter(
        "PNG Images", "png");
    javax.swing.filechooser.FileFilter jpgFilter = new javax.swing.filechooser.FileNameExtensionFilter(
        "JPG Images", "jpg");
    javax.swing.filechooser.FileFilter ppmFilter = new javax.swing.filechooser.FileNameExtensionFilter(
        "PPM Images", "ppm");

    // Add the file filters to the file chooser
    fileChooser.addChoosableFileFilter(pngFilter);
    fileChooser.addChoosableFileFilter(jpgFilter);
    fileChooser.addChoosableFileFilter(ppmFilter);

    // Set the default filter (optional, you can choose one to start with)
    fileChooser.setFileFilter(pngFilter); // Default filter, for example, PNG

    // Open the file chooser dialog to select the file to save
    int userSelection = fileChooser.showSaveDialog(null);
    controller.handleSave(userSelection, fileChooser, pngFilter, jpgFilter, ppmFilter);

  }
}
