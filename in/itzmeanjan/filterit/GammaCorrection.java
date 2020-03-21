package in.itzmeanjan.filterit;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Given an image ( either color or grayscaled ), we'll transform each pixel
 * intensity of that image using power law transformation / gamma correction
 * function. Transformation can be controlled using `gamma` value.
 */
class GammaCorrection {

        /**
         * Given intensity of certain pixel & max intensity value ( for 8-bit image
         * it'll be 255 ), we'll simply divide intensity by max intensity, which will
         * give us normalized intensity value ∈ [0, 1]
         */
        private double normalizeIntensity(int intensity, int maxIntensity) {
                return (double) intensity / (double) maxIntensity;
        }

        /**
         * Transforms single color component's ( i.e. R, G or B ) intensity value for a
         * pixel, using I(x, y) = 255 * e ^ ( γ * ln(I(x, y)) ), returns rounded value
         * because pixel intensity can't be floating point value
         */
        private int transformPixel(double gamma, int intensity, int maxIntensity) {
                return (int) (255
                                * Math.pow(Math.E, gamma * Math.log(this.normalizeIntensity(intensity, maxIntensity))));
        }

        /**
         * Applies transformation function on each pixel of buffered image ( either
         * color or grayscaled image ) & returns modified image
         */
        BufferedImage transform(BufferedImage img, double gamma) {
                if (img == null) {
                        return null;
                }
                BufferedImage transformed = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
                for (int i = 0; i < transformed.getHeight(); i++) {
                        for (int j = 0; j < transformed.getWidth(); j++) {
                                Color color = new Color(img.getRGB(j, i));
                                transformed.setRGB(j, i,
                                                (new Color(this.transformPixel(gamma, color.getRed(), 255),
                                                                this.transformPixel(gamma, color.getGreen(), 255),
                                                                this.transformPixel(gamma, color.getBlue(), 255)))
                                                                                .getRGB());
                        }
                }
                return transformed;
        }

        /**
         * Another interface to talk to gamma correction transformation function
         */
        BufferedImage transform(String src, double gamma) {
                return this.transform(ImportExportImage.importImage(src), gamma);
        }

}