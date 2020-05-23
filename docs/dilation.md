# Dilation

## Introduction

Here in this image dilation op, we take a kernel of size **3 x 3**,
which has its center at _(1, 1)_. This kernel to be applied on each pixel of image,
and if at least one pixel of image has intensity value 1 _( for binary images )_, then pixel at which
currently kernel centered at, will get a value 1 _( foreground pixel intensity value )_, 
resulting into increased size of foreground region of image.

**Here we consider 1 to be foreground color & 0 to be background color.**

But we're interested in applying dilation on color & grayscale images too. All we need to do is to consider
max pixel intensity from order-**X** neighborhood around current center pixel 
& put that as new intensity at current center pixel.

**Dilation is not exactly Mode Filter, because shape of kernel not confined to square matrices. 
Though square shaped masks only supported as of this writing.**

For color image, we need to do this same thing for three different color components. 
Only RGB color model supported at time of writing.

## Usage

- Make sure you download `in.itzmeanjan.filterit.jar` & add it as your project dependency.

- Code for applying Dilation

```java
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.morphology.Dilation;


public class Main{

	public static void main(String [] args){
		System.out.println(
            ImportExportImage.exportImage(
                new Dilation().dilate("dream.jpg", 1, 1), 
                "dilated.jpg"));
	}

}
```

## Result

Change may not be that much prominent, 
but taking difference of these two images 
reveals change in better fashion.

Original | Dilated
--- | ---
![dream](../examples/dream.jpg) | ![dilated](../examples/dilated.jpg)

Thanking you
