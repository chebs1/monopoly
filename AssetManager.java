 



import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


public class AssetManager
{
    public AssetManager() throws IOException
    {
        
    }
    
    public static BufferedImage loadImage(AssetEnum asset) throws IOException
    {
        return loadImage(asset.getPath());
    }
    
    public static BufferedImage loadImage(String path) throws IOException
    {
        InputStream input = AssetManager.class.getResourceAsStream("images/" + path);

        if (input == null)
        {
            // Also load images when running directly from the project folder.
            return ImageIO.read(new File("images", path));
        }

        try (InputStream imageInput = input)
        {
            return ImageIO.read(imageInput);
        }
    }
}
