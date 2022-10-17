package com.game.example.basic.logic.scene.domain;

import com.game.example.basic.logic.scene.object.VisibleObject;
import com.game.example.basic.logic.scene.utils.MapUtil;
import com.google.common.collect.Maps;
import org.qiunet.flash.handler.common.MessageHandler;

import java.util.Map;
import java.util.function.Consumer;

/***
 * 地图的实例
 */
public final class SceneInstance extends MessageHandler<SceneInstance> {
    /**
     * 默认的格子大小
     */
    private static final int DEFAULT_REGION_GRID_SIZE = 10;
    /**
     * 默认的视野格子大小
     */
    private static final int DEFAULT_VIEW_REGION_GRID_SIZE = 2;
    /**
     * 场景的ID
     */
    private final String sceneId;
    /**
     * 包含的区域
     */
    private final Map<String, MapRegion> regions = Maps.newConcurrentMap();
    /**
     * 视野格子数
     */
    private final int viewRegionGridSize;
    /**
     * 格子大小
     */
    private final int regionGridSize;

    public SceneInstance(String sceneId) {
        this.sceneId = sceneId;
        this.viewRegionGridSize = DEFAULT_VIEW_REGION_GRID_SIZE;
        this.regionGridSize = DEFAULT_REGION_GRID_SIZE;
    }

    public SceneInstance(String sceneId, int viewRegionGridSize, int regionGridSize) {
        this.sceneId = sceneId;
        this.viewRegionGridSize = viewRegionGridSize;
        this.regionGridSize = regionGridSize;
    }

    // 可能为null
    public MapRegion returnRegion(String regionId) {
        return regions.get(regionId);
    }

    public MapRegion returnRegion(float x, float y) {
        return regions.computeIfAbsent(this.calRegionId(x, y), key -> new MapRegion(this, key));
    }

    // 根据坐标计算出来在哪块区域.
    public String calRegionId(float x, float z) {
        int xID = (int) Math.floor(x / this.regionGridSize);
        int zSize = (int) Math.floor(z / this.regionGridSize);
        return MapUtil.buildRegionId(xID, zSize);
    }

    // 计算周围的格子，并处理
    public void calAroundRegionId(Position<?> position, Consumer<MapRegion> consumer) {
        MapRegion currRegion = position.getCurrRegion();
        consumer.accept(currRegion);

        Integer[] integers = MapUtil.splitRegionId(currRegion.getRegionId());
        for (int xId = integers[0] - viewRegionGridSize; xId <= integers[0] + viewRegionGridSize; xId++) {
            for (int yId = integers[1] - viewRegionGridSize; yId <= integers[1] + viewRegionGridSize; yId++) {
                consumer.accept(position.getSceneInstance().returnRegion(MapUtil.buildRegionId(xId, yId)));
            }
        }
    }

    // 销毁
    @Override
    public void destroy(){
        super.destroy();
        // 清理现在场景的人. 至于人去哪. 由客户端再请求
        this.regions.values().forEach(region -> {
            for (VisibleObject object : region.data.values()) {
                object.getPosition().offline();
                object.destroy();
            }
        });
    }

    @Override
    public String msgExecuteIndex() {
        return sceneId;
    }

    public String getSceneId() {
        return sceneId;
    }
}
