package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageProcessorGUI extends JFrame {
  private JLabel imageLabel; // To display the image
  private JPanel histogramPanel; // To display the histogram
  private JScrollPane imageScrollPane; // For scrolling the image
  private BufferedImage currentImage; // Currently displayed image

  public ImageProcessorGUI() {
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
    JButton flipButton = new JButton("Flip");
    JButton compressButton = new JButton("Compress");

    controlPanel.add(loadButton);
    controlPanel.add(saveButton);
    controlPanel.add(blurButton);
    controlPanel.add(sharpenButton);
    controlPanel.add(greyscaleButton);
    controlPanel.add(sepiaButton);
    controlPanel.add(flipButton);
    controlPanel.add(compressButton);

    add(controlPanel, BorderLayout.SOUTH);

    // Add Action Listeners
    loadButton.addActionListener(new LoadImageListener());
    saveButton.addActionListener(new SaveImageListener());
    blurButton.addActionListener(new BlurImageListener());
    sharpenButton.addActionListener(new SharpenImageListener());
    greyscaleButton.addActionListener(new GreyscaleImageListener());
    sepiaButton.addActionListener(new SepiaImageListener());
    flipButton.addActionListener(new FlipImageListener());
    compressButton.addActionListener(new CompressImageListener());

    setVisible(true);
  }

  private class LoadImageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
          currentImage = ImageIO.read(selectedFile);
          imageLabel.setIcon(new ImageIcon(currentImage));
          updateHistogram();
        } catch (Exception ex) {
          showError("Error loading image: " + ex.getMessage());
        }
      }
    }
  }

  private class SaveImageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        showError("No image to save.");
        return; }
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showSaveDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
          String filePath = selectedFile.getAbsolutePath();
          String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
          ImageIO.write(currentImage, fileExtension, selectedFile);
        } catch (Exception ex) {
          showError("Error saving image: " + ex.getMessage());
        }
      }
    }
  }

  private class BlurImageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        showError("No image to blur.");
        return;
      }
      // Implement blur logic here
      showMessage("Blur operation applied (not implemented).");
    }
  }

  private class SharpenImageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        showError("No image to sharpen.");
        return;
      }
      // Implement sharpen logic here
      showMessage("Sharpen operation applied (not implemented).");
    }
  }

  private class GreyscaleImageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        showError("No image to convert to greyscale.");
        return;
      }
      // Implement greyscale logic here
      showMessage("Greyscale operation applied (not implemented).");
    }
  }

  private class SepiaImageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        showError("No image to apply sepia.");
        return;
      }
      // Implement sepia logic here
      showMessage("Sepia operation applied (not implemented).");
    }
  }

  private class FlipImageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        showError("No image to flip.");
        return;
      }
      // Implement flip logic here
      showMessage("Flip operation applied (not implemented).");
    }
  }

  private class CompressImageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (currentImage == null) {
        showError("No image to compress.");
        return;
      }
      // Implement compression logic here
      showMessage("Compression applied (not implemented).");
    }
  }

  private void updateHistogram() {
    // Implement histogram drawing logic here
    histogramPanel.removeAll();
    histogramPanel.revalidate();
    histogramPanel.repaint();
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new ImageProcessorGUI());
  }
}