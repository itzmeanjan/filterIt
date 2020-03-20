# filterIt

![banner](examples/banner.jpg)

Another Order **n** _( >= 1 )_ Image Filtering implementation in Java. **Show some** :heart:

## usage

First clone this repo into a suitable directory in your computer

```bash
$ git clone https://github.com/itzmeanjan/filterIt.git
```

Make sure you've JDK _( >=8 )_ installed. Check using

```bash
$ javac -version
```

Time to compile Java classes

```bash
$ find . -name "*.java" | xargs javac
```

~~Now we can run it~~ [ **To be fixed soon enough** ]

```bash
$ java Driver sample.jpg 3 # generates each of mean, median & mode filtered images of order 1, 2, 3
```

Generated images will be in current working directory :wink:

## examples

### Spatial Filter(s)

Order | Mean | Median | Mode | Min
--- | --- | --- | --- | ---
0 | ![sample_image](examples/sample.jpg) | ![sample_image](examples/sample.jpg) | ![sample_image](examples/sample.jpg) | ![sample_image](examples/sample.jpg)
1 | ![order_1_MeanFiltered](examples/order_1_MeanFiltered.jpg) | ![order_1_MedianFiltered](examples/order_1_MedianFiltered.jpg) | ![order_1_ModeFiltered](examples/order_1_ModeFiltered.jpg) | ![order_1_MinFiltered](examples/order_1_MinFiltered.jpg)
2 | ![order_2_MeanFiltered](examples/order_2_MeanFiltered.jpg) | ![order_2_MedianFiltered](examples/order_2_MedianFiltered.jpg) | ![order_2_ModeFiltered](examples/order_2_ModeFiltered.jpg) | ![order_2_MinFiltered](examples/order_2_MinFiltered.jpg)
3 | ![order_3_MeanFiltered](examples/order_3_MeanFiltered.jpg) | ![order_3_MedianFiltered](examples/order_3_MedianFiltered.jpg) | ![order_3_ModeFiltered](examples/order_3_ModeFiltered.jpg) | ![order_3_MinFiltered](examples/order_3_MinFiltered.jpg)
4 | ![order_4_MeanFiltered](examples/order_4_MeanFiltered.jpg) | ![order_4_MedianFiltered](examples/order_4_MedianFiltered.jpg) | ![order_4_ModeFiltered](examples/order_4_ModeFiltered.jpg) | ![order_4_MinFiltered](examples/order_4_MinFiltered.jpg)
5 | ![order_5_MeanFiltered](examples/order_5_MeanFiltered.jpg) | ![order_5_MedianFiltered](examples/order_5_MedianFiltered.jpg) | ![order_5_ModeFiltered](examples/order_5_ModeFiltered.jpg) | ![order_5_MinFiltered](examples/order_5_MinFiltered.jpg)

### Edge Detection Filter(s)

Filter | Original | Horizontal Edges | Vertical Edges | Both Edges
--- | --- | --- | --- | ---
Sobel | ![sample_image](examples/sample.jpg) | ![sobelHorizontalEdges](examples/sobelH.jpg) | ![sobelVerticalEdges](examples/sobelV.jpg) | ![sobelAllEdges](examples/sobel.jpg)
Prewitt | ![sample_image](examples/sample.jpg) | ![prewittHorizontalEdges](examples/prewittH.jpg) | ![prewittVerticalEdges](examples/prewittV.jpg) | ![prewittAllEdges](examples/prewitt.jpg)

### GrayScaling

Original | GrayScaled
--- | ---
![gray_sample](examples/gray_sample.jpg) | ![grayscaled](examples/grayscaled.jpg)

### Inverse Pixel Transformation

Type | Original | Inversed
--- | --- | ---
Grayscaled | ![pulmonary_abscess](examples/pulmonary_abscess.jpg) | ![inverseTransformed](examples/inverseTransformed.jpg)
Color | ![gray_sample](examples/gray_sample.jpg) | ![inverseTransformedColor](examples/inverseTransformedColor.jpg)

### Gamma Correction

Type | γ | Original | Gamma Corrected
--- | --- | --- | ---
Grayscaled | 1/2 | ![pollen](examples/pollen.jpg) | ![gammaCorrected](examples/gammaCorrected_1_2.jpg)
Grayscaled | 1/3 | ![pollen](examples/pollen.jpg) | ![gammaCorrected](examples/gammaCorrected_1_3.jpg)
Grayscaled | 2 | ![pollen](examples/pollen.jpg) | ![gammaCorrected](examples/gammaCorrected_2.jpg)
Grayscaled | 3 | ![pollen](examples/pollen.jpg) | ![gammaCorrected](examples/gammaCorrected_3.jpg)
Color | 1/2 | ![gray_sample](examples/gray_sample.jpg) | ![gammaCorrectedColor](examples/gammaCorrectedColor_1_2.jpg)
Color | 1/3 | ![gray_sample](examples/gray_sample.jpg) | ![gammaCorrectedColor](examples/gammaCorrectedColor_1_3.jpg)
Color | 2 | ![gray_sample](examples/gray_sample.jpg) | ![gammaCorrectedColor](examples/gammaCorrectedColor_2.jpg)
Color | 3 | ![gray_sample](examples/gray_sample.jpg) | ![gammaCorrectedColor](examples/gammaCorrectedColor_3.jpg)

### Log Transformation

Type | Base | Original | Log Transformed
--- | --- | --- | ---
Grayscaled | e | ![pollen](examples/pollen.jpg) | ![logTransformed](examples/logTransformed_e.jpg)
Grayscaled | 10 | ![pollen](examples/pollen.jpg) | ![logTransformed](examples/logTransformed_10.jpg)
Color | e | ![abstract](examples/abstract.jpg) | ![logTransformedColor](examples/logTransformedColor_e.jpg)
Color | 19 | ![abstract](examples/abstract.jpg) | ![logTransformedColor](examples/logTransformedColor_10.jpg)

### Histogram Equalization

Original | Histogram Equalized
--- | ---
![circulatory_sys](examples/circulatory_sys.jpg) | ![histogramEqualized](examples/histogramEqualized.jpg)

**Thanking you ...**
