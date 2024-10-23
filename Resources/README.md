# Image Processing Application

## Overview
This application is built using the MVC (Model-View-Controller) architecture and supports various image processing operations, including format conversion, basic transformations, color adjustments, and filtering effects. Below is a detailed breakdown of each package and its components.

## Controller Package

### ImageController.java
**Purpose:** Main controller class implementing the command pattern.  
**Responsibilities:**
- Manages operation mappings
- Handles image loading/saving
- Executes image transformations
- Routes commands between the model and view

### TriConsumer.java (Interface)
**Purpose:** Functional interface for three-parameter operations.  
**Usage:** Used for operations like `brighten` that require multiple inputs.

## Model Package

### ColorScheme Package

#### AbstractPixel.java (Abstract)
**Purpose:** Base class for pixel representations.  
**Usage:** Provides foundation for different pixel formats.

#### RGBPixel.java
**Purpose:** Implementation of the RGB color model.  
**Responsibilities:**
- Stores RGB values
- Ensures color values stay within the 0-255 range
- Provides methods for accessing individual color components

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

## Main Model Class

### Image.java
**Purpose:** Core class for image processing.  
**Responsibilities:**
- Manages pixel data
- Coordinates image transformations
- Handles format conversions
- Maintains the image's state
- Processes various image operations

## View Package

### ImageView.java
**Purpose:** Handles the user interface.  
**Responsibilities:**
- Displays user prompts
- Processes user input
- Shows operation results
- Reports errors
- Manages the command-line interface

### Main.java
**Purpose:** Application entry point.  
**Responsibilities:**
- Initializes MVC components
- Sets up the application environment
- Launches the user interface

### ScriptReader.java
**Purpose:** Processes script files for batch operations.  
**Responsibilities:**
- Reads script files
- Parses commands
- Validates script syntax
- Executes the script commands

## Testing Package

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
**Purpose:** Tests PPM-specific operations.  
**Tests:**
- PPM loading/saving
- PPM transformations
- Raw format handling
- Format conversions

### Installation
- Run Main class.
- Type run-script and paste the script location.

### Script commands with examples:
- load <file-path> <image-name>
Eg: load Images/Landscape.png l1
- save <file-path> <image-name>
Eg: save res/landscape-red-component.png l1-red-component
- brighten <factor> <image-name> <dest-image-name>
Eg: brighten 20 l1 l1-brighter
- horizontal-flip <image-name> <dest-image-name>
Eg: horizontal-flip l1 l1-horizontal-flip
- vertical-flip <image-name> <dest-image-name>
Eg: vertical-flip l1 l1-vertical-flip
- rgb-split <image-name> <red-image> <green-image> <blue-image>
Eg: rgb-split l1 l1-red-split l1-green-split l1-blue-split
- rgb-combine <dest-image-name> <red-image> <green-image> <blue-image>
Eg: combine l1-combine l1-red-split l1-green-split l1-blue-split
- value-component <image-name> <dest-image-name>
Eg: value-component l1 l1-value
- luma-component <image-name> <dest-image-name>
Eg: luma-component l1 l1-luma
- intensity-component <image-name> <dest-image-name>
Eg: intensity-component l1 l1-intensity
- greyscale <image-name> <dest-image-name>
Eg: greyscale l1 l1-greyscale
- sepia <image-name> <dest-image-name>
Eg: save res/landscape-sepia.png l1-sepia
- blur <image-name> <dest-image-name>
Eg: blur l1 l1-blur
- sharpen <image-name> <dest-image-name>
Eg: sharpen l1 l1-sharper
- run-script <script-file-path>

