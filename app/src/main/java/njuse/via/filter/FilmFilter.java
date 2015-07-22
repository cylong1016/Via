package njuse.via.filter;

import android.graphics.Bitmap;

import njuse.via.filter.ImageBlender.BlendMode;

/**
 * 电影特效
 * @author ycmo
 *
 */
public class FilmFilter implements ImageFilterInterface{
	private GradientFilter gradient;
    private SaturationModifyFilter saturationFx;
    ImageData imageData;
    public FilmFilter(Bitmap bmp,float angle)
    {
    	imageData = new ImageData(bmp);
        gradient = new GradientFilter(imageData);
        gradient.Gradient = Gradient.Fade();
        gradient.OriginAngleDegree = angle;
    }
	public ImageData imageProcess() {
		// TODO Auto-generated method stub
		ImageData clone = imageData.clone();
		imageData = gradient.imageProcess();
        ImageBlender blender = new ImageBlender();
        blender.Mode = BlendMode.Multiply;
        saturationFx = new SaturationModifyFilter(blender.Blend(clone, imageData));
        saturationFx.SaturationFactor = -0.6f;
        return saturationFx.imageProcess();
	}

}
