# filterIt

![banner](banner.jpg)

Another Order **n** _( >= 1 )_ Image Filtering implementation in Java. **Show some** :heart:

## usage

First clone this repo into a suitable directory in your computer

```bash
$ git clone https://github.com/itzmeanjan/filterIt.git
```

Make sure you've JDK _( >=8 )_ installed. Check using

```bash
$ javac -version
```

Time to compile Java classes

```bash
$ find . -name "*.java" | xargs javac
```

Now we can run it

```bash
$ java Driver sample.jpg 3 # generates each of mean, median & mode filtered images of order 1, 2, 3
```

Generated images will be in current working directory :wink:

## samples

Order | Mean | Median | Mode
--- | --- | --- | ---
0 | ![sample_image](sample.jpg) | ![sample_image](sample.jpg) | ![sample_image](sample.jpg)
1 | ![order_1_MeanFiltered](order_1_MeanFiltered.jpg) | ![order_1_MedianFiltered](order_1_MedianFiltered.jpg) | ![order_1_ModeFiltered](order_1_ModeFiltered.jpg)
2 | ![order_2_MeanFiltered](order_2_MeanFiltered.jpg) | ![order_2_MedianFiltered](order_2_MedianFiltered.jpg) | ![order_2_ModeFiltered](order_2_ModeFiltered.jpg)
3 | ![order_3_MeanFiltered](order_3_MeanFiltered.jpg) | ![order_3_MedianFiltered](order_3_MedianFiltered.jpg) | ![order_3_ModeFiltered](order_3_ModeFiltered.jpg)
4 | ![order_4_MeanFiltered](order_4_MeanFiltered.jpg) | ![order_4_MedianFiltered](order_4_MedianFiltered.jpg) | ![order_4_ModeFiltered](order_4_ModeFiltered.jpg)
5 | ![order_5_MeanFiltered](order_5_MeanFiltered.jpg) | ![order_5_MedianFiltered](order_5_MedianFiltered.jpg) | ![order_5_ModeFiltered](order_5_ModeFiltered.jpg)


**Thanking you ...**
