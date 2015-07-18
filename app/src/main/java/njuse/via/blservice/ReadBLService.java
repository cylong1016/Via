package njuse.via.blservice;

import java.util.ArrayList;

import njuse.via.po.ScreenSet;

/**
 * Created by zucewei on 2015/7/18.
 *
 * 用于读取已经存在的 之前创造好的作品
 */
public interface ReadBLService {
    /**
     * 按作品名读取制作的已经序列化保存的数据
     *
     * @return ScreenSet
     */
    ScreenSet getMakeResource(String fileName);
    /**
     * 读取制作的已经序列化保存的所有数据
     *
     * @return ArrayList<ScreenSet>
     */
    ArrayList<ScreenSet> getAllMakeRes();
}
