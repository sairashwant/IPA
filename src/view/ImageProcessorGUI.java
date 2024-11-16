package view;

import controller.ImageController;
import controller.ImageControllerInterface;
import controller.ImageGUIController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import model.Image;
import model.ImageModel;
import model.imagetransformation.basicoperation.Flip.Direction;

public class ImageProcessorGUI extends JFrame {
  private JLabel imageLabel; // To display the image
  private JPanel histogramPanel; // To display the histogram
  private JScrollPane imageScrollPane; // For scrolling the image
  private BufferedImage currentImage; // Currently displayed image
  private ImageGUIController controller; // Reference to the controller

  public ImageProcessorGUI(ImageGUIController controller) {
    this.controller = controller; // Store the controller reference
    setTitle("Image Processor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLayout(new BorderLayout());

    // Image Display Area
    imageLabel = new JLabel();
    imageScrollPane = new JScrollPane(imageLabel);
    add(imageScrollPane, BorderLayout.CENTER);

    // Histogram Panel
    histogramPanel = new JPanel();
    histogramPanel.setPreferredSize(new Dimension(200, 600));
    add(histogramPanel, BorderLayout.EAST);

    // Control Panel
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new GridLayout(0, 2));

    JButton loadButton = new JButton("Load Image");
    JButton saveButton = new JButton("Save Image");
    JButton blurButton = new JButton("Blur");
    JButton sharpenButton = new JButton("Sharpen");
    JButton greyscaleButton = new JButton("Greyscale");
    JButton sepiaButton = new JButton("Sepia");
    JButton flipVerticalButton = new JButton("Flip-Vertical");
    JButton flipHorizontalButton = new JButton("Flip-Horizontal");
    JButton compressButton = new JButton("Compress");

    controlPanel.add(loadButton);
    controlPanel.add(saveButton);
    controlPanel.add(blurButton);
    controlPanel.add(sharpenButton);
    controlPanel.add(greyscaleButton);
    controlPanel.add(sepiaButton);
    controlPanel.add(flipVerticalButton);
    controlPanel.add(flipHorizontalButton);
    controlPanel.add(compressButton);

    add(controlPanel, BorderLayout.SOUTH);

    // Add Action Listeners using lambda functions
    loadButton.addActionListener(e -> controller.handleLoad(new String[]{})); // Pass appropriate arguments
    saveButton.addActionListener(e -> controller.handleSave(new String[]{})); // Pass appropriate arguments
    blurButton.addActionListener(e -> controller.applyOperation(new String[]{"blur", "sourceKey", "destKey"})); // Example arguments
    sharpenButton.addActionListener(e -> controller.applyOperation(new String[]{"sharpen", "sourceKey", "destKey"})); // Example arguments
    greyscaleButton.addActionListener(e -> controller.applyOperation(new String[]{"greyscale", "sourceKey", "destKey"})); // Example arguments
    sepiaButton.addActionListener(e -> controller.applyOperation(new String[]{"sepia", "sourceKey", "destKey"})); // Example arguments
    flipVerticalButton.addActionListener(e -> controller.handleFlip(new String[]{"flip", "sourceKey", "destKey"},Direction.VERTICAL)); // Example arguments
    flipHorizontalButton.addActionListener(e -> controller.handleFlip(new String[]{"flip", "sourceKey", "destKey"},Direction.HORIZONTAL)); // Example arguments
    compressButton.addActionListener(e -> controller.handleCompression(new String[]{"compress", "ratio", "sourceKey", "destKey"})); // Example arguments

    setVisible(true);
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
  }

  public static void main(String[] args) {
    ImageModel model = new Image(); // Create an instance of the Image model
    ImageControllerInterface controller = new ImageController(model); // Create the controller
    ImageGUIController guiController = new ImageGUIController((ImageController) controller); // Create the GUI controller
    SwingUtilities.invokeLater(() -> new ImageProcessorGUI(guiController)); // Launch the GUI
  }
}