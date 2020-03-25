# filterIt

![banner](examples/banner.jpg)

~~Another Order **n** _( >= 1 )_ Image Filtering implementation in Java.~~ **Show some** :heart:

![banner_2](examples/banner_2.jpg)

**Not anymore just an image filtering implementation, rather I planned to extend it to also include pixel transformation implementations _( where each of which is written from scratch )_**

**What's more interesting is, I'm converting all implementations into concurrent one, so that it can leverage power of modern multi-core machines** :wink:

_Help me in improving it :)_

## implementation(s)

### Spatial Filters

- [x] [Mean Filter](./docs/meanFilter.md)
- [x] [Median Filter](./docs/medianFilter.md)
- [x] [Mode Filter](./docs/modeFilter.md)
- [x] [Min Filter](./docs/minFilter.md)

### Edge Detection Filters

- [x] [Sobel Filter](./docs/sobelFilter.md)
- [x] [Prewitt Filter](./docs/prewittFilter.md)
- [ ] [Laplacian Filter](.)

### Gray Scaling

- [x] [Gray Scaling](./docs/grayscaling.md)

### Pixel Transformation

- [x] [Inverse Transformation](./docs/inverseTransformation.md)
- [x] [Gamma Correction](./docs/gammaCorrection.md)
- [x] [Log Transformation](./docs/logTransformation.md)
- [x] [Inverse Log Transformation](./docs/inverseLogTransformation.md)
- [x] [Histogram Equalization](./docs/histogramEqualization.md)
- [x] [Contrast Stretching](./docs/contrastStretching.md)

### Image Transposition

- [x] [Transposed Image](./docs/transpose.md)

## usage

- Make sure you've JDK installed on your machine. I've used `openjdk version "1.8.0_232-solus"`; check yours using

```bash
$ java -version
$ javac -version
```

- Clone this repo into your machine

```bash
$ git clone https://github.com/itzmeanjan/filterIt.git
```

- Get into `filterIt` directory

```bash
$ cd filterIt
```

- Create a Java source file in this directory

```bash
$ touch Main.java
```

- Open this file using your favourite text editor & first import `in.itzmeanjan.filterit.*`

```java
import in.itzmeanjan.filterit.*;
```

- Start defining your class, write a main method, inside which you can now call any implementation which are present in this package

```java
import in.itzmeanjan.filterit.*;

class Main {
    public static void main(String[] args) {
        InverseImageTransformation iTransformation = new InverseImageTransformation();
        // make sure you're x.jpg present in this directory
        System.out.println(ImportExportImage.exportImage(iTransformation.transform("./x.jpg"), "./y.jpg"));
    }
}
```

- Compile & run it

```bash
$ javac Main.java
$ java Main
```

## contribution

If you want to help me in improving this package, you're very much welcome. First fork this repo & then clone it into your machine. Now you can start working on it. Finally submit a PR :wink:

What I'm currently interested in is, implementing different pixel operations i.e. +, -, *, /, %, &, |, ! etc. If you can help me in getting those implemented, that'll be great.


_Thanking you ..._
