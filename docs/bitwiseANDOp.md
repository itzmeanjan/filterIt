# Bitwise AND Operation

Say, we're given with two binary strings of equal width _( take it 8-bit )_ and asked to perform bitwise AND operation.

Bit String One | Bit String Two | AND-ed
--- | --- | ---
00010010 | 00100001 | 00000000

So currently we've two images _( of course different )_ of equal dimension and we'll pick pixel intensity at _P[i, j]_ from each of those two images & apply AND operator on those two 8-bit values. As we'll also allow color images, we need to consider three color components _( R, G & B )_ seperately for each pixel P[i, j].

And result will be really interesting ... :wink:

## Usage

Make sure you set classpath during compilation & running.

```java
// Main.java
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.bitwise.BitwiseAND;

class Main{
    public static void main(String [] args){
        ImportExportImage.exportImage(
        new BitwiseAND().operate("cloud.jpg", "gradient.jpg"),
        "bitwiseANDed.jpg");
    }
}
```

During compilation

```bash
# in.itzmeanjan.filterit.jar & Main.java are present in same directory
$ javac -cp ".:in.itzmeanjan.filterit.jar" Main.java
$ java -cp ".:in.itzmeanjan.filterit.jar" Main
```

## Operand Images 

Image 1 | Image 2
--- | ---
![operandOne](../examples/cloud.jpg) | ![operandTwo](../examples/gradient.jpg)

## Resuling Image = Image_1 & Image_2

![bitwiseANDed](../examples/bitwiseANDed.jpg)
