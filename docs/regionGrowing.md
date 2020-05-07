# Region Growing

## Introduction

Given a grayscale image & a seed pixel location _( with in that image )_,
we'll try to segment this image using region growing mechanism.

- At very beginning, we'll have only seed pixel as active pixel
- In each of next iteration, we'll pick up inactive N8 _( pixels in order-1 neighbourhood )_, of current pixel under inspection
- Each of selected N8 to be made active, so that in next iteration we don't include it again
- If current pixel, is having intensity value within specified range, we'll simply add make it dead pixel

**Dead pixels to be included in sink image i.e. segmented image & they will be allowed to keep their intensity value as it was in original.**

All other pixels to be made *black* i.e. *rgb(0, 0, 0)*, that's selected background color.

We'll keep processing until we've zero active pixels in buffer.

**Selection of seed pixel is very important for good segmentation result.**

## Usage

- Code for applying region growing based segmentation

```java
// Main.java in some project, in which in.itzmeanjan.filterit.jar is set as dependency

import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.segmentation.RegionGrowing;


public class Main{

	public static void main(String [] args){
		System.out.println(
            ImportExportImage.exportImage(
                new RegionGrowing().segment("mask.png", 640, 452, 5), 
                "segmented.png"));
	}

}
```

- Compile & run _( make sure **in.itzmeanjan.filterit.jar** is set as project dependency )_

## Result

**In example below, sample image is of dimension _1280 x 904_, so I used center pixel i.e. (640, 452) as seed pixel, so that _MASK_ can be segmented**.

Sample | Segmented
--- | ---
![mask](../examples/mask.png) | ![segmentedUsingRegionGrowing](../examples/segmentedUsingRegionGrowing.png)


Thanking you :blush:
