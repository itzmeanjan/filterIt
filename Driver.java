import java.io.File;

class Driver {

    // given a image file name, obtains extension of that image
    String imageExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    void usage() {
        System.out.println("\n[+] Usage : java Driver `image.(jpg | png)` order ( <= filters to be applied )\n");
    }

    // this program requires user to pass
    // target image file name as command line argument
    // which is required to be a valid file name
    // present on system
    // if so, we'll return that name, else returns null
    String getFileFromCMD(String[] args) {
        try {
            if (args.length != 2) {
                return null;
            }
            File file = new File(args[0]);
            return file.exists() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) ? args[0]
                    : null;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    int getOrderFromCMD(String[] args) {
        try {
            return args.length != 2 ? 0 : Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.println(e);
            return 0;
        }
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        String fileName = driver.getFileFromCMD(args);
        if (fileName == null) {
            driver.usage();
            System.exit(-1);
        }
        int order = driver.getOrderFromCMD(args);
        if (order == 0) {
            System.err.println("[!] Invalid order value");
            System.exit(-1);
        }
        Thread[] thrdArr = new Thread[3 * order];
        int idx = 0;
        for (int i = 1; i <= order; i++) {
            thrdArr[idx] = new Thread(
                    new Worker(new MeanFilter(fileName),
                            "order_" + i + "_MeanFiltered." + driver.imageExtension(fileName), i),
                    "Thread_" + (idx + 1));
            idx++;
        }
        for (int i = 1; i <= order; i++) {
            thrdArr[idx] = new Thread(
                    new Worker(new MedianFilter(fileName),
                            "order_" + i + "_MedianFiltered." + driver.imageExtension(fileName), i),
                    "Thread_" + (idx + 1));
            idx++;
        }
        for (int i = 1; i <= order; i++) {
            thrdArr[idx] = new Thread(
                    new Worker(new ModeFilter(fileName),
                            "order_" + i + "_ModeFiltered." + driver.imageExtension(fileName), i),
                    "Thread_" + (idx + 1));
            idx++;
        }
        for (Thread thread : thrdArr) {
            thread.start();
        }
        for (Thread thread : thrdArr) {
            try {
                thread.join(0);
            } catch (InterruptedException ie) {
                System.err.println(ie);
            }
        }
        System.out.println("\n[+]Done");
        System.exit(0);
    }
}