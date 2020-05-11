# Edge Detection - using _Dilation_

## Introduction

We're going to detect edges of an image, using combination of Dilation & Subtraction.

- First grayscale image
- Now dilate gray scaled image
- Then subtract gray scaled image from dilated one
- We've edge highlighted image

**Note :: Assumes foreground objects are in White i.e. rgb(255, 255, 255), which is to be edge detected, because we're using Dilation as our base op !!!**

## Usage

- Make sure you've downloaded `in.itzmeanjan.filterit.jar` & set it as your Java project dependency.

- Now add following code into a Java source file.

```java
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.edgedetection.EdgeDetectionUsingDilation;


public class Main{

        public static void main(String [] args){
                System.out.println(
                    ImportExportImage.exportImage(
                        new EdgeDetectionUsingDilation().detect("shoe.jpg"), "edgeDetectionUsingDilation.jpg"));
        }

}
```

- Compile & run code

## Result

Source | Edge Highlighted
--- | ---
![dream](../examples/dream.jpg) | ![edgeDetectionUsingDilation](../examples/edgeDetectionUsingDilation.jpg)


Thanking you :)
