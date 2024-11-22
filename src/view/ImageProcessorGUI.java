package view;

import controller.ImageGUIController;
import controller.ImageGUIControllerInterface;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import model.imagetransformation.basicoperation.Flip.Direction;

/**
 * A GUI-based image processor application that allows users to load, edit, and save images. This
 * class provides a graphical interface with various image manipulation operations such as flipping,
 * color adjustment, compression, and previews of edits before applying them.
 */
public class ImageProcessorGUI extends JFrame implements ImageProcessorGUIInterface {

  private final ImageGUIControllerInterface controller;
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


  /**
   * Constructs an ImageProcessorGUI object and initializes the GUI components.
   *
   * @param controller The controller to handle user operations and interactions with the model.
   */
  public ImageProcessorGUI(ImageGUIController controller) {
    this.controller = controller;

    setTitle("Image Processor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 800);
    setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel(
        new GridLayout(0, 1, 10, 10));

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

    loadButton.addActionListener(e -> handleLoad(this));
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
    levelsAdjustButton.addActionListener(e -> handleLevelAdjustWithPreview());
    colorCorrectionButton.addActionListener(e -> controller.applyOperation(
        new String[]{"color-correction", controller.getLatestKey(), "color-correction"}));
    downscale.addActionListener(e -> handleDownscale());
    exitButton.addActionListener(e -> handleExit());

    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(undoButton);
    buttonPanel.add(originalImageButton);
    buttonPanel.add(brightenButton);
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

    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);

    histogramLabel = new JLabel();
    histogramLabel.setHorizontalAlignment(JLabel.CENTER);
    histogramLabel.setPreferredSize(new Dimension(400, 400));

    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    JScrollPane histogramScrollPane = new JScrollPane(histogramLabel);

    JPanel imagePanel = new JPanel(new BorderLayout());
    imagePanel.add(imageScrollPane, BorderLayout.CENTER);
    imagePanel.add(histogramScrollPane, BorderLayout.EAST);

    JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
    buttonScrollPane.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    add(buttonScrollPane, BorderLayout.WEST);

    add(imagePanel, BorderLayout.CENTER);

    setVisible(true);
  }

  /**
   * Creates and returns a JButton with the specified label text.
   *
   * @param text The label text for the button.
   * @return A configured JButton object.
   */
  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(200, 40));
    return button;
  }

  /**
   * Creates a panel with an operation button and a preview checkbox.
   *
   * @param operationButton The button to perform the operation.
   * @param previewCheckbox The checkbox for enabling/disabling the preview.
   * @return A JPanel containing the button and checkbox.
   */
  private JPanel createOperationWithPreviewPanel(JButton operationButton,
      JCheckBox previewCheckbox) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(operationButton);
    panel.add(previewCheckbox);
    return panel;
  }


  /**
   * Displays an error message in a dialog box.
   *
   * @param message The error message to display.
   */

  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays the given image in the main image display area of the GUI.
   *
   * @param image The BufferedImage to be displayed.
   */
  @Override
  public void displayImage(BufferedImage image) {
    imageLabel.setIcon(new ImageIcon(image));
    controller.applyHistogram(new String[]{"histogram"});
  }

  /**
   * Displays the given histogram image in the histogram display area of the GUI.
   *
   * @param histogram The BufferedImage of the histogram to be displayed.
   */
  @Override
  public void displayHistogram(BufferedImage histogram) {
    histogramLabel.setIcon(new ImageIcon(histogram));
  }


  /**
   * Handles the brighten operation by prompting the user for a brightness factor and applying the
   * brighten operation through the controller.
   */
  private void handleBrighten() {
    String factor = JOptionPane.showInputDialog("Enter brightness factor:");
    if (factor != null) {
      controller.handleBrighten(
          new String[]{"brighten", factor, controller.getLatestKey(), "brightened"});
    }
  }

  /**
   * Handles the exit process by checking if there are unsaved changes. Prompts the user to save
   * before exiting or directly closes the application.
   */
  private void handleExit() {
    String latest = controller.getLatestKey(); // Retrieve the latest image key
    if (latest != null && !latest.isEmpty()) {
      int option = JOptionPane.showConfirmDialog(
          this,
          "You have unsaved changes. Do you want to save before exiting?",
          "Confirm Exit",
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.WARNING_MESSAGE
      );

      if (option == JOptionPane.YES_OPTION) {
        handleSave(new String[]{"save", "output.png", latest}); // Save the current image
        System.exit(0); // Exit the application
      } else if (option == JOptionPane.NO_OPTION) {
        System.exit(0); // Exit without saving
      }
      // Cancel option does nothing and keeps the application open
    } else {
      int confirm = JOptionPane.showConfirmDialog(
          this,
          "Are you sure you want to exit?",
          "Confirm Exit",
          JOptionPane.YES_NO_OPTION
      );
      if (confirm == JOptionPane.YES_OPTION) {
        System.exit(0); // Exit the application
      }
    }
  }


  /**
   * Handles the downscale operation by prompting the user for the new width and height, and
   * applying the downscale operation through the controller.
   */
  private void handleDownscale() {
    String width = JOptionPane.showInputDialog("Enter new width:");
    String height = JOptionPane.showInputDialog("Enter new height:");
    controller.handleDownscale(width, height);
  }

  /**
   * Handles image operations with optional preview, including blur, sharpen, sepia, and greyscale.
   *
   * @param operation The operation to perform (e.g., "Blur", "Sharpen").
   */
  private void handleOperationWithPreview(String operation) {
    JCheckBox previewCheckbox = getPreviewCheckboxForOperation(operation);
    assert previewCheckbox != null;
    if (previewCheckbox.isSelected()) {
      int splitPercentage = getSplitPercentage();
      if (splitPercentage != -1) {
        controller.handleSplit(
            new String[]{operation.toLowerCase(), String.valueOf(splitPercentage)});
      } else {
        JOptionPane.showMessageDialog(this, "Operation canceled", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    } else {
      controller.applyOperation(new String[]{operation.toLowerCase()});
    }
  }

  /**
   * Handles levels adjustment with optional preview. Prompts the user for adjustment levels and
   * applies the operation with or without a split preview.
   */
  private void handleLevelAdjustWithPreview() {
    JCheckBox previewCheckbox = getPreviewCheckboxForOperation("levels-adjust");
    assert previewCheckbox != null;
    if (previewCheckbox.isSelected()) {
      int splitPercentage = getSplitPercentage();
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


  /**
   * Returns the appropriate preview checkbox for the given operation.
   *
   * @param operation The name of the operation.
   * @return The JCheckBox corresponding to the operation.
   */
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

  /**
   * Displays a slider dialog for selecting a split percentage and returns the user's selection.
   *
   * @return The split percentage selected by the user, or -1 if the dialog is canceled.
   */
  private int getSplitPercentage() {
    JSlider slider = new JSlider(0, 100, 50);
    slider.setMajorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

    JPanel sliderPanel = new JPanel(new BorderLayout());
    sliderPanel.add(slider, BorderLayout.CENTER);

    JButton okButton = new JButton("OK");
    JButton closeButton = new JButton("Close");

    JPanel buttonPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(0, 10, 0, 5);
    buttonPanel.add(okButton, gbc);

    gbc.gridx = 1;
    buttonPanel.add(closeButton, gbc);

    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.add(sliderPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    JDialog dialog = new JDialog(this, "Select Split Percentage", true);
    dialog.getContentPane().add(mainPanel);
    dialog.pack();
    dialog.setLocationRelativeTo(this);

    final int[] result = {50};
    final boolean[] confirmed = {false};

    okButton.addActionListener(e -> {
      result[0] = slider.getValue();
      confirmed[0] = true;
      dialog.dispose();
    });

    closeButton.addActionListener(e -> dialog.dispose());

    dialog.setVisible(true);

    return confirmed[0] ? result[0] : -1;
  }

  /**
   * Adds a window listener to the GUI that prompts the user to save unsaved changes before exiting
   * the application.
   */
  @Override
  public void addWindowListenerToGUI() {
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        String latest = controller.getLatestKey();
        if (latest != null && !latest.isEmpty()) {
          int option = JOptionPane.showConfirmDialog(null,
              "Do you want to save the current image before closing?",
              "Save Image", JOptionPane.YES_NO_OPTION);

          if (option == JOptionPane.YES_OPTION) {
            handleSave(new String[]{"save", "output.png", latest});
          } else if (option == JOptionPane.NO_OPTION) {
            dispose();
          }
        } else {
          dispose();
        }
      }
    });
  }


  /**
   * Shows a preview of the specified image and operation in a dialog, with options to apply,
   * cancel, or go back to adjust settings.
   *
   * @param image     The preview image to display.
   * @param operation The operation associated with the preview.
   */
  public void showPreview(BufferedImage image, String operation) {
    if (image != null) {
      JLabel previewLabel = new JLabel(new ImageIcon(image));
      JScrollPane scrollPane = new JScrollPane(previewLabel);
      scrollPane.setPreferredSize(new Dimension(600, 600));

      JButton applyButton = new JButton("Apply");
      JButton backButton = new JButton("Back");
      JButton cancelButton = new JButton("Cancel");

      JPanel buttonPanel = new JPanel();
      buttonPanel.add(applyButton);
      buttonPanel.add(backButton);
      buttonPanel.add(cancelButton);

      JPanel mainPanel = new JPanel(new BorderLayout());
      mainPanel.add(scrollPane, BorderLayout.CENTER);
      mainPanel.add(buttonPanel, BorderLayout.SOUTH);

      JDialog dialog = new JDialog(this, "Preview", true);
      dialog.getContentPane().add(mainPanel);
      dialog.pack();
      dialog.setLocationRelativeTo(this);

      if (!Objects.equals(operation, "levels-adjust")) {
        applyButton.addActionListener(e -> {
          controller.applyOperation(new String[]{
              operation.toLowerCase(), controller.getLatestKey(), operation.toLowerCase()
          });
          dialog.dispose();
        });
      } else {
        applyButton.addActionListener(e -> {
          controller.handleLevelsAdjust(new String[]{black, mid, white});
          dialog.dispose();
        });
      }

      backButton.addActionListener(e -> {
        dialog.dispose();
        int splitPercentage = getSplitPercentage();
        if (splitPercentage != -1) {
          controller.handleSplit(
              new String[]{operation.toLowerCase(), String.valueOf(splitPercentage)});
        }
      });

      cancelButton.addActionListener(e -> dialog.dispose());

      dialog.setVisible(true);
    } else {
      JOptionPane.showMessageDialog(this, "Unable to generate preview.", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Handles levels adjustment by prompting the user for black, mid, and white levels, and applying
   * the adjustment through the controller.
   */
  private void handleLevelsAdjust() {
    black = JOptionPane.showInputDialog("Enter black level (0-255):");
    mid = JOptionPane.showInputDialog("Enter mid level (0-255):");
    white = JOptionPane.showInputDialog("Enter white level (0-255):");

    if (black != null && mid != null && white != null) {
      controller.handleLevelsAdjust(new String[]{black, mid, white});
    }
  }

  /**
   * Handles levels adjustment with a split preview, using a percentage of the image.
   *
   * @param percentage The percentage of the image to apply the levels adjustment to.
   */
  private void handleLevelsAdjustSplit(int percentage) {
    String black = JOptionPane.showInputDialog("Enter black level (0-255):");
    String mid = JOptionPane.showInputDialog("Enter mid level (0-255):");
    String white = JOptionPane.showInputDialog("Enter white level (0-255):");
    String percentageStr = String.valueOf(percentage);
    if (black != null && mid != null && white != null) {
      controller.handleLevelsAdjust(new String[]{black, mid, white, percentageStr});
    }
  }

  /**
   * Handles image compression by prompting the user for a compression ratio and applying the
   * compression operation through the controller.
   */
  private void handleCompression() {
    String ratio = JOptionPane.showInputDialog("Enter compression ratio (0-100):");
    if (ratio != null) {
      controller.handleCompression(
          new String[]{"compress", ratio, controller.getLatestKey(), "compressed"});
    }
  }

  /**
   * Displays a preview of levels adjustment, allowing the user to apply, go back, or cancel.
   *
   * @param image The preview image to display.
   * @param args  The levels adjustment parameters (black, mid, white).
   */
  public void showPreviewLevelAdj(BufferedImage image, String[] args) {
    if (image == null) {
      JOptionPane.showMessageDialog(this, "Unable to generate preview. Image is null.",
          "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    JLabel previewLabel = new JLabel(new ImageIcon(image));
    JScrollPane scrollPane = new JScrollPane(previewLabel);
    scrollPane.setPreferredSize(new Dimension(600, 600));

    JButton applyButton = new JButton("Apply");
    JButton backButton = new JButton("Back");
    JButton cancelButton = new JButton("Cancel");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(applyButton);
    buttonPanel.add(backButton);
    buttonPanel.add(cancelButton);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(scrollPane, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    JDialog dialog = new JDialog(this, "Preview Levels Adjustment", true);
    dialog.getContentPane().add(mainPanel);
    dialog.pack();
    dialog.setLocationRelativeTo(this);
    String[] updatedString = new String[]{args[0], args[1], args[2]};
    applyButton.addActionListener(e -> {
      controller.handleLevelsAdjust(updatedString);
      dialog.dispose();
    });

    backButton.addActionListener(e -> {
      dialog.dispose();
      int splitPercentage = getSplitPercentage();
      String[] backButtonString = new String[]{args[0], args[1], args[2],
          String.valueOf(splitPercentage)};
      if (splitPercentage != -1) {
        controller.handleLevelsAdjust(backButtonString);
      }
    });

    cancelButton.addActionListener(e -> dialog.dispose()); // Close the dialog on cancel

    dialog.setVisible(true);
  }

  /**
   * Handles the process of loading an image file into the application. Opens a file chooser dialog
   * for the user to select an image file, retrieves the selected file's path, and passes it to the
   * controller for further processing.
   *
   * @param gui The GUI instance to display dialogs and manage user interactions.
   */
  private void handleLoad(ImageProcessorGUI gui) {
    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filename = selectedFile.getAbsolutePath();
      controller.handleLoad(this, "load1", filename);
    }
  }


  /**
   * Handles the process of saving an image to a file. Prompts the user with a file chooser dialog
   * to select the save location, allows the user to specify a file name and format, and passes the
   * save request to the controller for execution.
   *
   * <p>The method supports multiple file formats (PNG, JPG, PPM) and ensures that an image has
   * been loaded before attempting to save. If no image is loaded, an error message is
   * displayed.</p>
   *
   * @param args Optional arguments (currently unused) for specifying save configurations.
   */
  private void handleSave(String[] args) {
    if (controller.getLatestKey() == null || controller.getLatestKey().isEmpty()) {
      showError("No image loaded to save. Please load an image first.");
      return;
    }

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image");

    javax.swing.filechooser.FileFilter pngFilter =
        new javax.swing.filechooser.FileNameExtensionFilter(
            "PNG Images", "png");
    javax.swing.filechooser.FileFilter jpgFilter =
        new javax.swing.filechooser.FileNameExtensionFilter(
            "JPG Images", "jpg");
    javax.swing.filechooser.FileFilter ppmFilter =
        new javax.swing.filechooser.FileNameExtensionFilter(
            "PPM Images", "ppm");

    fileChooser.addChoosableFileFilter(pngFilter);
    fileChooser.addChoosableFileFilter(jpgFilter);
    fileChooser.addChoosableFileFilter(ppmFilter);

    fileChooser.setFileFilter(pngFilter);

    int userSelection = fileChooser.showSaveDialog(null);
    controller.handleSave(userSelection, fileChooser, pngFilter, jpgFilter, ppmFilter);

  }
}
