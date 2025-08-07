package sprites;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class ImageLoader {
    public static final HashMap<String, BufferedImage> allImages = new HashMap<>();

    private static final String[] imagePaths = {
            /*Player*/
        "player/player.png",
            "player/player2.png",
            /*Particulas de Poeira*/
        "particles/dust/dustP1.png",
            "particles/dust/dustP2.png",
                "particles/dust/dustP3.png",
                    "particles/dust/dustP4.png",
                        "particles/dust/dustP5.png",
                            "particles/dust/dustP6.png",
            /*Segmentos do cachecol*/
        "player/scarfSegmentP1.png",
            "player/scarfSegmentP1v2.png",
                "player/scarfSegmentP2.png",
                    "player/scarfSegmentP2v2.png",
            /*Effects*/
        "particles/effects/smoke_landing.png",
            "particles/effects/smoke_jumping.png",
                /*dash smoke*/
        "particles/effects/dash_smoke.png"
    };

    public static void loadAllImages(){
        for(String path : imagePaths){
            try{
                BufferedImage img = ImageIO.read(ImageLoader.class.getResourceAsStream("/" + path));
                if (img != null) {
                    allImages.put(path, img);
                } else {
                    System.err.println("Imagem retornou null: " + path);
                }
            } catch (IOException | NullPointerException e) {
                System.err.println("Erro ao carregar imagem: " + path);
                e.printStackTrace();
            }
        }
    }

    public static BufferedImage getImage(String path){
        return allImages.get(path);
    }
}
