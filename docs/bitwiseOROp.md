# Bitwise OR Operation 

## Introduction

Say, we're given with two binary strings of equal width _( take it 8-bit )_ and asked to perform bitwise OR operation.

Bit String One | Bit String Two | OR-ed
--- | --- | ---
00010010 | 00100001 | 00110011

We know for a 24-bit image _( where each color component R, G & B of 8-bit width )_, it can have ~16M _( 2^24 )_ unique color values. And also we understand each pixel intensity value must be lying with in [0, 255] range. Even if we take two binary strings of 8-bit width, after application of OR operator resulting bitstring will be of width 8-bit, so its value will also lie in [0, 255] range i.e. after application of bitwise OR operator we don't need to scale pixel intensity value in [0, 255] range seperately.

Now we're given with two images of equal dimension, and asked to apply bitwise OR operator, **how do we do that ?**


## Implementation

So currently we've two images _( of course different )_ of equal dimension and we'll pick pixel intensity at _P[i, j]_ from each of those two images & apply OR operator on those two 8-bit values. As we'll also allow color images, we need to consider three color components _( R, G & B )_ seperately for each pixel P[i, j].

## Usage

Make sure you set classpath during compilation & running.

```java
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.bitwise.BitwiseOR;

class Main{
    public static void main(String [] args){
        ImportExportImage.exportImage(
        new BitwiseOR().operate("cloud.jpg", "gradient.jpg"),
        "bitwiseORed.jpg");
    }
}
```

## Operand Images 

Image 1 | Image 2
--- | ---
![operandOne](../examples/cloud.jpg) | ![operandTwo](../examples/gradient.jpg)

## Resuling Image = Image_1 | Image_2

![bitwiseORed](../examples/bitwiseORed.jpg)
