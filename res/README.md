# Image Processing Application

## Overview
This application is built using the MVC (model-view-controller) architecture and supports various image processing operations, including format conversion, basic transformations, color adjustments, and filtering effects. Below is a detailed breakdown of each package and its components.


## Table of contents
- [Controller Package](#controller-package)
- [Model Package](#model-package)
- [view Package](#view-package)
- [Controller Test Package](#controller-test-package)
- [Model Test Package](#model-test-package)
- [Resource Package](#resources-package)
- [Change Log](#change-log)
- [Script Commands](#script-commands-with-examples)

## Controller Package

### ImageControllerInterface.java (Interface)
**Purpose:** Interface for managing image processing commands.

**Responsibilities:**
- Defines methods for loading, saving, and transforming images.
- Manages command mappings.
- Supports advanced operations like RGB manipulation and compression.

### ImageController.java
**Purpose:** Coordinates image processing commands and delegates tasks to the model.

**Responsibilities:**
- Initializes command and operation mappings.
- Manages image loading, saving, and transformations.
- Routes user inputs to corresponding image operations.
- Supports advanced features like RGB split/merge, compression, color adjustments, and script execution.


### ImageUtil.java
**Purpose:** Utility class for loading and saving image files in various formats.
**Responsibilities:**
- Loads images into a 2D array of Pixels from supported formats (PNG, JPG, PPM).
- Saves Pixels data to specified image formats (PNG, JPG, PPM).
- Handles unsupported formats and errors.

### ScriptReader.java
**Purpose:** Processes script files for batch operations.  
**Responsibilities:**
- Reads script files
- Parses commands
- Validates script syntax
- Executes the script commands

### ImageFormat Package

#### ImageFormat.java (Interface)
**Purpose:** Defines the contract for image format handlers.  
**Key Methods:**
- `load()`: Loads an image from a file
- `save()`: Saves an image to a file

#### AbstractCompressedImageFormat.java (Abstract)
**Purpose:** Base class for compressed formats (JPG/PNG).  
**Responsibilities:**
- Implements common loading functionality
- Uses `BufferedImage` for image processing

#### AbstractRawImageFormat.java (Abstract)
**Purpose:** Base class for raw formats (PPM).  
**Responsibilities:**
- Implements loading for raw formats
- Handles text-based image formats

### Format-Specific Classes

#### JPGImage.java
**Purpose:** Handles JPEG format operations.  
**Responsibilities:** Implements JPEG-specific saving functionality.

#### PNGImage.java
**Purpose:** Handles PNG format operations.  
**Responsibilities:** Implements PNG-specific saving functionality.

#### PPMImage.java
**Purpose:** Handles PPM format operations.  
**Responsibilities:** Implements PPM-specific saving functionality.

## Model Package

### ColorScheme Package

#### Pixels.java (Interface)
**Purpose:** Base class for pixel representations.  
**Usage:** Provides foundation for different pixel formats.

#### RGBPixel.java
**Purpose:** Implementation of the RGB color model.  
**Responsibilities:**
- Stores RGB values
- Ensures color values stay within the 0-255 range
- Provides methods for accessing individual color components



## ImageTransformation Package

### Transformation.java (Interface)
**Purpose:** Base interface for all transformations.  
**Key Method:** `apply()` for executing transformations.

### BasicOperation Package

#### AbstractBasicOperation.java (Abstract)
**Purpose:** Base class for basic image operations.  
**Responsibilities:** Implements common transformation logic.

### Operation Classes

#### Brighten.java
**Purpose:** Adjusts the brightness of the image.  
**Responsibilities:** Handles brightness factor calculations.

#### Combine.java
**Purpose:** Combines separate RGB channels into a single image.  
**Responsibilities:** Merges separate color components.

#### Flip.java
**Purpose:** Handles image flipping operations.  
**Responsibilities:** Supports horizontal and vertical flips.

#### Intensity.java
**Purpose:** Calculates the intensity component of the image.  
**Responsibilities:** Computes the average of the RGB values.

#### Split.java
**Purpose:** Separates the RGB channels of an image.  
**Responsibilities:** Creates individual color components.

#### Value.java
**Purpose:** Extracts the value component from the image.  
**Responsibilities:** Determines the maximum of the RGB values.

#### Luma.java
**Purpose:** Calculates the luma component of the image.  
**Responsibilities:** Uses a weighted sum of the RGB values.

## ColorTransformation Package

### AbstractColorTransformation.java (Abstract)
**Purpose:** Base class for color transformations.  
**Responsibilities:** Implements matrix-based color transformations.

### Transformation Classes

#### GreyScale.java
**Purpose:** Converts an image to greyscale.  
**Responsibilities:** Uses a specific color matrix for the transformation.

#### Sepia.java
**Purpose:** Applies a sepia effect to the image.  
**Responsibilities:** Uses a sepia transformation matrix.

## Filtering Package

### AbstractFiltering.java (Abstract)
**Purpose:** Base class for filter operations.  
**Responsibilities:** Implements kernel-based filtering techniques.

### Filter Classes

#### Blur.java
**Purpose:** Applies a Gaussian blur to the image.  
**Responsibilities:** Uses a blur kernel matrix for the effect.

#### Sharpen.java
**Purpose:** Applies a sharpening effect to the image.  
**Responsibilities:** Uses a sharpen kernel matrix for the effect.


### Advanced Operation

### AdjustLevel
**Purpose:** Adjusts image tonal range using shadow, midtone, and highlight points to enhance contrast.

**Responsibilities:**
- Initialize: Validates points, calculates quadratic curve coefficients.
- Transform: Adjusts each pixel’s RGB values based on the curve for refined tonal control.

### ColorCorrection
**Purpose:** Adjusts image color balance by aligning RGB channel peaks, enhancing color consistency.

**Responsibilities:**
- Analyze: Extracts histograms and finds peaks for RGB channels within a range.
- Adjust: Calculates target peak and shifts channels to match, clamping to valid RGB values.

### Compression
**Purpose:** The Compression class applies a Haar wavelet transform to compress an image based on a specified compression ratio.

**Responsibilities:**
-Pads the image to the nearest power of two for processing.
- Separates and compresses the red, green, and blue color channels independently.
- Applies the Haar transform, compresses coefficients, and reconstructs the image.
- Restores the image to its original dimensions after compression.

### Downscale
**Purpose:** Enhances image processing with advanced operations like selective transformations and resizing while maintaining core functionalities.

**Responsibilities:**
- Applies masked transformations to specific image regions.
- Downscales images to specified dimensions.
- Manages processed images using unique keys and retrieves the latest state.
- Converts pixel data to BufferedImage for GUI compatibility.
- Ensures robust input validation for reliable processing.

### Masked Operation
**Purpose:** Implements selective image transformations by applying an operation only to the regions defined by a mask.

**Responsibilities:**
- Applies the given transformation to pixels where the mask indicates, leaving others unchanged.
- Ensures the mask and source image dimensions are consistent for accurate processing.
- Handles pixel-level operations by integrating the transformation with the mask logic.
- Validates mask values, treating black pixels (RGB: 0,0,0) as the areas to transform.


## Main model Class

### ImageModel.java (Interface)
**Purpose:** Top level interface for the model.
**Responsibilities:**
- Image Loading and Storing
- Image Saving
- Image Transformations
- Component Extraction
- Advanced Operations
- Error Handling

### EnhancedImageModel.java (Interface)
**Purpose:** Extends the functionality of the base ImageModel interface to support advanced image processing operations and state management.

**Responsibilities:**
- Downscaling: Implements functionality to reduce the dimensions of an image to specified width and height while maintaining its quality.
- Masked Operations: Enables applying specific image transformations (e.g., filtering or enhancements) to selective regions of an image defined by a mask.
- State Management: Provides a mechanism to retrieve the most recent key associated with an image, facilitating efficient tracking of image transformations.

### Image.java
**Purpose:** Core class for image processing.  
**Responsibilities:**
- Manages pixel data
- Coordinates image transformations
- Handles format conversions
- Maintains the image's state
- Processes various image operations

### EnhancedImage.java
**Purpose:** Core class for advanced image processing with support for selective transformations and efficient image management.

**Responsibilities:**
- Applies masked image transformations to process specific regions based on a provided mask.
- Downscales images to desired dimensions while maintaining quality.
- Converts pixel data into a format suitable for graphical rendering (e.g., BufferedImage).
- Manages and tracks processed images using unique keys.
- Retrieves the most recently processed image for streamlined access.


## view Package

### ImageProcessorGUIInterface.java (Interface)

**Purpose:** Interface for managing graphical user interface (GUI) interactions in an image processing application.

**Responsibilities:**
- Displays error messages to notify users of issues during image processing.
- Renders processed images on the GUI for user interaction and visualization.
- Generates and displays histograms for analyzing image properties.
- Enables interactive features by adding window listeners to the GUI.
- Provides a preview of image transformations before finalizing operations.


### ImageProcessorGUI.java
**Purpose:** GUI for an image processing application.

**Responsibilities:**
- Provides a user interface for loading, editing, and saving images
- Displays images and their histograms in the application.
- Facilitates various image manipulation operations such as flipping, color adjustments, blurring, sharpening, and applying filters.
- Allows users to preview changes before applying them.
- Manages user interactions through buttons and checkboxes for different operations.
- Handles error messaging and user prompts for input.
- Integrates with a controller to execute image processing operations.
- Supports undo functionality and reverting to the original image.

## Main.java
**Purpose:** Entry point for the image processing application, managing initialization and mode selection.

**Responsibilities:**
- Determines the mode of operation (GUI, script execution, or interactive CLI) based on command-line arguments.
- Initializes the Model-View-Controller (MVC) components for the application.
- Sets up and launches the graphical user interface (GUI) for image processing.
- Executes scripts for batch processing of image transformations.
- Launches the interactive text-based interface for manual user commands.

## Testing Package

## Controller Test Package

### ImageControllerMockTest.java
**Purpose:** To validate that ImageController correctly processes image manipulation commands by testing a mock ImageModel. Ensures expected outputs for key image transformations.


### ImageControllerTest.java
**Purpose:** This test class validates the image manipulation functionalities of the ImageController, ensuring correct processing of operations like blur, flip, brighten, greyscale, RGB split/combine, and saving of results.

**Tests:**
- Verifies correct application of blur, flip (horizontal/vertical), brighten, greyscale, RGB split/combine, luma, sepia, and sharpen operations.
- Confirms that images are loaded, processed, and saved correctly.
- Ensures the expected output files are created during each transformation.
- Checks if exceptions are thrown if the given input in not valid.

### ImageGUIControllerTest.java
**Purpose:**  This test class validates the functionality of the ImageGUIController by using a mock implementation of the ImageControllerInterface. It ensures that the controller correctly handles various image manipulation commands, delegates operations appropriately, and produces the expected outputs for GUI-driven interactions.

**Tests:**
- Verifies correct handling of image-related commands such as load, save, brighten, flip, compression, RGB split/combine, levels adjustment, and scripted operations.
- Confirms that the getCommandMap() method is called and returns the expected output.
- Ensures proper interaction between the GUI and the underlying controller logic.
- Validates the behavior of the run and printMenu methods.
- Checks that each operation appends the correct message to the output log.
- Confirms that methods handle arguments appropriately and produce the expected output for valid inputs.


### ImageScriptTest.java
**Purpose:**  This test ensures that the ImageController processes image operation scripts correctly and outputs the expected messages.

**Tests:** Validates that the controller executes script commands properly, performing image operations and matching the expected console output.

## Model Test Package

### ImageExceptionTest.java
**Purpose:** Tests error handling in the application.  
**Tests:**
- Invalid file formats
- File loading errors
- Invalid operations
- Parameter validation
- Boundary conditions

### ImagePNGModelTest.java
**Purpose:** Tests PNG-specific operations.  
**Tests:**
- PNG loading/saving
- PNG transformations
- Color operations
- Format conversions

### ImagePPMModelTest.java
**Since the number of files was more than 125 and submission on the server was not possible, we had to
remove the PPM test images so none of these tests will pass. If needed, please do contact us so that we can
provide you the test images and all these tests will pass.**

**Purpose:** Tests PPM-specific operations.  
**Tests:**
- PPM loading/saving
- PPM transformations
- Raw format handling
- Format conversions

## Resources Package

### PNGScript.txt
- Contains the script that is text file.

### ReadMe.Md
- Has the description of the design of the whole project.

### UseMe.md
- Has the instructions on how to use the project and perform operation on the image.

### Class_Diagram.png
- It is the class diagram of the project representing the connection between each classes in the project.


## Change log

### Added GUI
- The GUI was developed using Swing in the view and integrated with a newly created controller, ImageGUIController. This controller facilitated communication with the model by leveraging the existing controller.

### Downscale and masking
- A new class implementing the Transformation interface was introduced, featuring logic specifically designed for downscaling operations.
- Additionally, another class was developed to implement the Transformation interface, enabling advanced functionality for performing masked operations.

### Adding a new controller
- A new interface was introduced in the controller, extending the existing ImageControllerInterface.
- A new class, ImageGUIController, was created to implement this interface and extend the functionality of the ImageController class.
- This new class managed the mapping of operations from the GUI by connecting and delegating tasks to the existing ImageController class.

### Installation
- Run Main class.
- Type run-script and paste the script location.

### Script commands with examples:
-       load 
  -    Syntax : <file-path> <image-name>
  - Eg: load Images/Landscape.png l1
-     save 
  - Syntax : <file-path> <image-name>
  - Eg: save res/landscape-red-component.png l1-red-component
-     brighten
  - Syntax :  <factor> <image-name> <dest-image-name>
  - Eg: brighten 20 l1 l1-brighter
-     horizontal-flip
  - Syntax : <image-name> <dest-image-name>
  -  Eg: horizontal-flip l1 l1-horizontal-flip
-     vertical-flip 
  - Syntax : <image-name> <dest-image-name>
  - Eg: vertical-flip l1 l1-vertical-flip
-     rgb-split 
  - Syntax : <image-name> <red-image> <green-image> <blue-image>
  - Eg: rgb-split l1 l1-red-split l1-green-split l1-blue-split
-     rgb-combine 
  - Syntax : <dest-image-name> <red-image> <green-image> <blue-image>
  - Eg: combine l1-combine l1-red-split l1-green-split l1-blue-split
-     value-component
  - Syntax : <image-name> <dest-image-name>
  - Eg: value-component l1 l1-value
-     luma-component 
  - Syntax : <image-name> <dest-image-name>
  - Eg: luma-component l1 l1-luma
-     intensity-component 
  - Syntax : <image-name> <dest-image-name>
  - Eg: intensity-component l1 l1-intensity
-     greyscale 
  - Syntax : <image-name> <dest-image-name>
  - Eg: greyscale l1 l1-greyscale
-     sepia 
  - Syntax : <image-name> <dest-image-name>
  - Eg: sepia l1 l1-sepia
-     blur 
  - Syntax : <image-name> <dest-image-name>
  - Eg: blur l1 l1-blur
-     sharpen 
  - Syntax : <image-name> <dest-image-name>
  - Eg: sharpen l1 l1-sharper
-     compression
  - Syntax: <ratio> <image-name> <dest-image-name>
  - Eg: compress 75 l1 l1-75-compress
-     histogram
  - Syntax: <image-name> <dest-image-name>
  - Eg: histogram l1 l1-histogram
-     color-correction
  - Syntax: <image-name> <dest-image-name>
  - Eg: color-correction l1 l1-cc
-     levels-adjust
  - Syntax: <black> <mid> <white> <image-name> <dest-image-name>
  - Eg: levels-adjust 20 100 255 l1 l1-levelsadjust
-     split and transform
  - Syntax: <operation> <image-name> <dest-image-name> split <splitPercentage>
  - Eg: blur l1 l1-split-blur split 50
-       Downscale
  - Syntax: <downscale> <srcKey> <newWidth> <newHeight> <destKey>
  - Eg: downscale l1 200 200 l1-downscale
-       Mask-Image
  - Load the mask image before performing this operation
  - Syntax: <Operation> <srcKey> <maskImageKey> <destKey>
  - Eg: blur l1 l1-mask l1-blur-mask
-     run-scipt 
  - Syntax : <script-file-path>
  - Eg: run-script Images/PNGScript.txt


## IMAGE CITATION ##
- Images(Wildlife.png and Landscape.png) taken by [Pranav Viswanathan](https://www.flickr.com/photos/199542081@N07/albums/with/72177720312735513)

