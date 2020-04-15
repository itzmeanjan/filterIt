# Bitwise Right Shift Operator

## Introduction

Given an image _( color / grayscaled )_ & number of places to shift rightwards, it'll compute resulting image by shifting each pixel intensity value _P[x, y]_ by  given number of places.

Assume we've a pixel intensity P[x, y] for grayscale image I, with value 10.

_P[x, y] = 10_

If we apply right shift operator on P[x, y] by 2 places, then 

_P[x, y] = 2_

Well if we're asked deal with color image, then each pixel P[x, y] will have three color components & each of them to be shifted rightwards.

## Usage

- `Main.java` in another project, where we're using _filterIt_.

```java
// Main.java
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.bitwise.BitwiseRightShift;

class Main{
    public static void main(String [] args){
        ImportExportImage.exportImage(
        new BitwiseRightShift().operate("cloud.jpg", 1),
        "bitwiseOpRightShiftedby1.jpg");
    }
}
```

- Compilation & running

```bash
# in.itzmeanjan.filterit.jar & Main.java are present in same directory
$ javac -cp ".:in.itzmeanjan.filterit.jar" Main.java
$ java -cp ".:in.itzmeanjan.filterit.jar" Main
```

## Result

Source | Shift by | Sink
--- | --- | ---
![cloud](../examples/cloud.jpg) | 1 | ![cloud](../examples/bitwiseOpRightShiftedby1.jpg)
![cloud](../examples/cloud.jpg) | 2 | ![cloud](../examples/bitwiseOpRightShiftedby2.jpg)
![cloud](../examples/cloud.jpg) | 3 | ![cloud](../examples/bitwiseOpRightShiftedby3.jpg)
![cloud](../examples/cloud.jpg) | 4 | ![cloud](../examples/bitwiseOpRightShiftedby4.jpg)


Thanking you, :blush:
