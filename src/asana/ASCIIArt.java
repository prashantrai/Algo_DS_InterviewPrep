package asana;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/*
 * ASCII Art Printer: https://www.baeldung.com/ascii-art-in-java
 * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-console/src/main/java/com/baeldung/asciiart/AsciiArt.java
 */
public class ASCIIArt {

	public static void main(String[] args) {
		ASCIIArt asciiArt = new ASCIIArt();
        String text = "PRASH";
        Settings settings = asciiArt.new Settings(new Font("SansSerif", Font.BOLD, 24), text.length() * 30, 30); // 30 pixel width per character
        
        asciiArt.drawString(text, "*", settings);
	}
	
	public void drawString(String text, String artChar, Settings settings) {
        BufferedImage image = getImageIntegerMode(settings.width, settings.height);

        Graphics2D graphics2D = getGraphics2D(image.getGraphics(), settings);
        graphics2D.drawString(text, 6, ((int) (settings.height * 0.67)));

        for (int y = 0; y < settings.height; y++) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int x = 0; x < settings.width; x++) {
                stringBuilder.append(image.getRGB(x, y) == -16777216 ? " " : artChar);
            }

            if (stringBuilder.toString()
                .trim()
                .isEmpty()) {
                continue;
            }

            System.out.println(stringBuilder);
        }

    }

    private BufferedImage getImageIntegerMode(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    private Graphics2D getGraphics2D(Graphics graphics, Settings settings) {
        graphics.setFont(settings.font);

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        return graphics2D;
    }

    public class Settings {
        public Font font;
        public int width;
        public int height;

        public Settings(Font font, int width, int height) {
            this.font = font;
            this.width = width;
            this.height = height;
        }
    }

}
