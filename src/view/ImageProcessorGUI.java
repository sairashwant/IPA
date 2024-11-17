package view;

import controller.ImageController;
import controller.ImageGUIController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import model.Image;
import model.ImageModel;

public class ImageProcessorGUI {
  private ImageGUIController controller;
  private JLabel imageLabel; // JLabel to display the image

  public ImageProcessorGUI(ImageGUIController controller) {
    this.controller = controller;
    initializeUI();
  }

  private void initializeUI() {
    JFrame frame = new JFrame("Image Processor");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLayout(new BorderLayout());

    imageLabel = new JLabel(); // Initialize the JLabel
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    frame.add(imageLabel, BorderLayout.CENTER);

    JButton loadButton = new JButton("Load Image");
    loadButton.addActionListener(e -> controller.handleLoad(this)); // Pass the GUI instance
    frame.add(loadButton, BorderLayout.SOUTH);

    frame.setVisible(true);
  }

  public void displayImage(BufferedImage image) {
    // Create an ImageIcon from the BufferedImage
    ImageIcon imageIcon = new ImageIcon(image);
    imageLabel.setIcon(imageIcon); // Set the icon on the JLabel
    imageLabel.repaint(); // Repaint the JLabel to show the new image
  }

  public static void main(String[] args) {
    ImageModel model = new Image(); // Create an instance of the Image model
    ImageController imageController = new ImageController(model); // Create the controller
    ImageGUIController controller = new ImageGUIController(imageController); // Create the GUI controller with the image controller
    new ImageProcessorGUI(controller); // Launch the GUI
  }
}