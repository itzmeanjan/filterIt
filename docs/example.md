## example

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

- You'll see one bash script, named `compile`, execute it

```bash
$ ls compile
$ chmod +x compile # making it executable
$ ./compile # compiles all java source files & builds *.jar from it
$ ls in.itzmeanjan.filterit.jar # generated jar; holding compiled classes
```

- Now you've whole package compiled into a single jar _( not runnable itself )_, which can be put into any directory where you want to use this package

- Let's copy `in.itmeanjan.filterit.jar` to immediate parent directory; then move to parent directory & create a java source file.

```bash
$ cp in.itzmeanjan.filterit.jar ..
$ cd ..
$ touch Main.java
```

- Open `Main.java` using your favourite text editor & start by importing required classes for applying inverse image transformation on a given image.

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

- Compile & run it, while setting classpath to `in.itzmeanjan.filterit.jar` file

```bash
$ javac -cp ".:in.itzmeanjan.filterit.jar" Main.java
$ java -cp ".:in.itzmeanjan.filterit.jar" Main
```

- And you've your result in `y.jpg`
- Check out `javadoc` generated API documentation [here](https://itzmeanjan.github.io/filterIt/javadoc/)
