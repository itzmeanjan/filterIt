# filterIt

**Image processing algorithms implemented from scratch in Java, with in-built concurrency support** :heavy_check_mark:

Banner_1 | Banner_2
--- | ---
![banner](examples/banner.jpg) | ![banner_2](examples/banner_2.jpg)

_Help me in improving it :)_

## usage

This Java library can be used for image processing ops, it has a lot of algorithms implemented
with built-in concurrency support _( using Java thread pool )_. Consider using it in your next project.

Check API documentation, _made with javadoc_, [here](./README.md/#api-documentation).

_Usage example can be found [here](./docs/example.md)._

## download

Get precompiled jar [here](./release/in.itzmeanjan.filterit.jar).

## implementation(s)

Following list denotes algorithms implemented in `filterIt`.

### Spatial Filters _( Concurrency incorporated )_

- [x] [Mean Filter](./docs/meanFilter.md)
- [x] [Median Filter](./docs/medianFilter.md)
- [x] [Mode Filter](./docs/modeFilter.md)
- [x] [Min Filter](./docs/minFilter.md)

### Edge Detection Filters _( Concurrency incorporated )_

- [x] [Sobel Filter](./docs/sobelFilter.md)
- [x] [Prewitt Filter](./docs/prewittFilter.md)
- [ ] Laplacian Filter
- [x] [using Dilation](./docs/edgeDetectionUsingDilation.md)
- [x] [using Erosion](./docs/edgeDetectionUsingErosion.md)

### Gray Scaling

- [x] [Gray Scaling](./docs/grayscaling.md)

### Pixel Transformation _( Concurrency incorporated )_

- [x] [Inverse Transformation](./docs/inverseTransformation.md)
- [x] [Gamma Correction](./docs/gammaCorrection.md)
- [x] [Log Transformation](./docs/logTransformation.md)
- [x] [Inverse Log Transformation](./docs/inverseLogTransformation.md)
- [x] [Histogram Equalization](./docs/histogramEqualization.md)
- [x] [Contrast Stretching](./docs/contrastStretching.md)

### Image Transposition

- [x] [Transposed Image](./docs/transpose.md)

### Rotation _( Concurrency incorporated )_

- Basic Rotation
    - [x] [Horizontal Rotation](./docs/horizontalRotation.md)
    - [x] [Vertical Rotation](./docs/verticalRotation.md)
- Compound Rotation
    - [x] [Clockwise Rotation](./docs/clockwiseRotation.md)
    - [x] [Anti-Clockwise Rotation](./docs/antiClockwiseRotation.md)

### Arithmetic Operator(s) _( Concurrency incorporated )_

- [x] [Addition](./docs/additionOp.md)
- [x] [Subtraction](./docs/subtractionOp.md)
- [x] [Multiplication](./docs/multiplicationOp.md)
- [x] [Division](./docs/divisionOp.md)

### Bitwise Operator(s) _( Concurrency incorporated )_

- [x] [Bitwise OR](./docs/bitwiseOROp.md)
- [x] [Bitwise AND](./docs/bitwiseANDOp.md)
- [x] [Bitwise XOR](./docs/bitwiseXOROp.md)
- [x] [Bitwise Right Shift](./docs/bitwiseRightShiftOp.md)
- [x] [Bitwise Left Shift](./docs/bitwiseLeftShiftOp.md)

### Affine Transformation _( Concurrency incorporated )_

- [x] [Translation](./docs/translation.md)
- [x] [Rotation](./docs/rotation.md)
- [x] [Scaling](./docs/scale.md)

### Segmentation

- [x] [Automatic Thresholding](./docs/automaticThresholding.md)
- [x] [Region Growing](./docs/regionGrowing.md)

### Morphological Ops

- [x] [Dilation](./docs/dilation.md)
- [x] [Erosion](./docs/erosion.md)

### Image Smoothing

- [x] [Opening](./docs/opening.md)
- [x] [Closing](./docs/closing.md)

## API documentation

Check [here](https://itzmeanjan.github.io/filterIt/javadoc/)

## contribution

If you want to help me in improving this package, you're very much welcome. 
First fork this repo & then clone it into your machine. 
Now you can start working on it by creating a new branch. 
Finally, submit a PR :wink:

What I'm currently interested in is, **improving concurrency in filters**. 
You might consider helping me there.

_Thanking you ..._
