## example

- Make sure you've JDK _( >= 8 )_ installed, check using following command

```bash
$ java -version
$ javac -version
```

- Download precompiled jar from [here](../release/in.itzmeanjan.filterit.jar)
- Add `in.itmeanjan.filterit.jar` to your Java project
- Open `Main.java` there and start by importing required classes for applying inverse image transformation on a given image.

```java
import in.itzmeanjan.filterit.ImportExportImage; // for reading & writing images
import in.itzmeanjan.filterit.transform.InverseImageTransformation; // implementation of inverse image transformation
```

- Start defining your class; write a main method, inside which we'll apply InverseImageTransformation on an image.

```java
import in.itzmeanjan.filterit.ImportExportImage;
import in.itzmeanjan.filterit.transform.InverseImageTransformation;

class Main {
    public static void main(String[] args) {
        InverseImageTransformation iTransformation = new InverseImageTransformation();
        // make sure you've x.jpg present in this directory
        // modified image to be stored in y.jpg, in same directory
        System.out.println(ImportExportImage.exportImage(iTransformation.transform("./x.jpg"), "./y.jpg"));
    }
}
```

- Compile & run `Main.java`, by adding `in.itzmeanjan.filterit.jar` to classpath

```bash
$ javac -cp ".:in.itzmeanjan.filterit.jar" Main.java
$ java -cp ".:in.itzmeanjan.filterit.jar" Main
```

- Voila ! You've your result :)
- Check API documentation [here](https://itzmeanjan.github.io/filterIt/javadoc/)
