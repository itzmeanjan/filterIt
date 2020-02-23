// all filters to be written must
// implement methods declared under following interface,
// which will finally help us in calling filters from
// different threads, by passing Filter type ( which is broader )
// while creating instance of Worker class
interface Filter {
    abstract void filterAndSave(String target, int order); // filters image & stores it in target file

    abstract String filterName(); // returns current filter name

    abstract String imageExtension(String fileName);
}