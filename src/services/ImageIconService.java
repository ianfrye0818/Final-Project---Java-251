package services;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Service class for handling image icons
 * 
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class ImageIconService {
  // The path to the default logo image icon
  private static final String DEFAULT_LOGO_PATH = "src/assets/images/logo.png";

  /**
   * Get an image icon from a given path
   * 
   * @param path The path to the image icon
   * @return The image icon
   */
  public static ImageIcon getImageIcon(String path) {
    ImageIcon icon = null;
    try {
      URL imageUrl = ImageIconService.class.getResource(path);
      if (imageUrl != null) {
        icon = new ImageIcon(imageUrl);
      } else {
        File imageFile = new File(path);
        if (imageFile.exists()) {
          icon = new ImageIcon(imageFile.getAbsolutePath());
        }
      }
      return icon;
    } catch (Exception ex) {
      System.err.println("Error loading image icon: " + ex.getMessage());
      return null;
    }
  }

  /**
   * Get the default logo image icon
   * 
   * @return The default logo image icon
   */

  public static ImageIcon getImageIcon() {
    return getImageIcon(DEFAULT_LOGO_PATH);
  }

  /**
   * Resize an image icon to a new width and height
   * 
   * @param icon   The image icon to resize
   * @param width  The new width of the image icon
   * @param height The new height of the image icon
   * @return The resized image icon
   */
  public static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
    if (icon != null) {
      Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
      icon = new ImageIcon(img);
    }
    return icon;
  }

}
