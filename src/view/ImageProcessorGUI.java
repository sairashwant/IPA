package view;

import controller.ImageController;
import controller.ImageGUIController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import model.EnhancedImage;
import model.EnhancedImageModel;
import model.Image;
import model.ImageModel;
import model.colorscheme.Pixels;

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

    // Button to load image
    JButton loadButton = new JButton("Load Image");
    loadButton.addActionListener(e -> loadListener()); // Pass the GUI instance
    frame.add(loadButton, BorderLayout.NORTH);

    // Button to apply blur
    JButton blurButton = new JButton("Blur");
    blurButton.addActionListener(e -> blurListener()); // Replace with actual keys
    frame.add(blurButton, BorderLayout.SOUTH);

    frame.setVisible(true);
  }

  private void loadListener(){
    controller.handleLoad(this);
  }
  
  private void blurListener(){
    String[] args = {"blur"};
    controller.applyOperation(args);
    String latestKey = controller.getLatestKey();
  }

  public void displayImage(BufferedImage image) {
    // Create an ImageIcon from the BufferedImage
    ImageIcon imageIcon = new ImageIcon(image);
    imageLabel.setIcon(imageIcon); // Set the icon on the JLabel
    imageLabel.repaint(); // Repaint the JLabel to show the new image
  }

  public static void main(String[] args) {
    EnhancedImageModel model = new EnhancedImage(); // Create an instance of the Image model
    ImageController imageController = new ImageController(model); // Create the controller
    ImageGUIController controller = new ImageGUIController(imageController); // Create the GUI controller with the image controller
    new ImageProcessorGUI(controller); // Launch the GUI
  }
}