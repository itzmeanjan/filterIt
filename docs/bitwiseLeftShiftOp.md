# Bitwise Left Shift Operator

## Introduction

Say we're asked to find out left shifted bit string of a given bit string B, **what do we do ?**

B = 10101011

Shift B by 2 places leftward

B = B << 2 = 1010101100

We already know this, for a 8-bit image, we can have 256 different color levels & for a color image we'll just keep three color components _( R, G & B )_ each of them will be 8-bit lengthy.

With 8-bits max unsigned integer that can be represented is 256 i.e. all pixel intensity values lie in [0, 255] range. 

But if we've one pixel intensity 255 _( in binary 11111111 )_ and asked to shift it by 1 place leftward, obtained value _( 510 )_ will definitely be not in valid pixel intensity range. So we need to put pixel intensity value back into range, by using either of following two mechanisms

- **Clip** pixel intensity values i.e. `I(x, y) = I(x, y) < 0 ? 0 : ( I(x, y) > 255 ? 255 : I(x, y) )`
- **Scale** pixel intensity value i.e. `I(x, y) = I(x, y) % 256`

So what we gonna do for an image is like below 

- Given an image, keep picking up each row of pixels and putting into an worker to be executed by different thread of execution that main.
- Each worker will apply bit wise left shift operator on each pixel intensity along provided row.
- Finally stores resulting image into sink buffer _( different that source image buffer )_.


## Result

Source | Shift By | Clipped | Sink
--- | --- | --- | ---
![nature](../examples/nature.jpg) | 1 | true | ![left_shifted](../examples/bitwiseOpLeftShiftedby1Clipped.jpg)
![nature](../examples/nature.jpg) | 1 | false | ![left_shifted](../examples/bitwiseOpLeftShiftedby1Scaled.jpg)
![nature](../examples/nature.jpg) | 2 | true | ![left_shifted](../examples/bitwiseOpLeftShiftedby2Clipped.jpg)
![nature](../examples/nature.jpg) | 2 | false | ![left_shifted](../examples/bitwiseOpLeftShiftedby2Scaled.jpg)
![nature](../examples/nature.jpg) | 3 | true | ![left_shifted](../examples/bitwiseOpLeftShiftedby3Clipped.jpg)
![nature](../examples/nature.jpg) | 3 | false | ![left_shifted](../examples/bitwiseOpLeftShiftedby3Scaled.jpg)
![nature](../examples/nature.jpg) | 4 | true | ![left_shifted](../examples/bitwiseOpLeftShiftedby4Clipped.jpg)
![nature](../examples/nature.jpg) | 4 | false | ![left_shifted](../examples/bitwiseOpLeftShiftedby4Scaled.jpg)

Thanking you
