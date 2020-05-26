package in.itzmeanjan.filterit.filter;

import java.awt.image.BufferedImage;

public interface Filter {
  BufferedImage filter(BufferedImage img, int order);

  BufferedImage filter(String src, int order);

  boolean isOrderValid(int order);

  String filterName();
}
