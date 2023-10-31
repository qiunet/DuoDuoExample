package com.game.example.basic.logic.scene.domain;

import com.game.example.basic.logic.scene.object.VisibleObject;
import com.game.example.basic.logic.scene.utils.MapUtil;
import com.game.example.common.logger.GameLogger;
import com.google.common.collect.Maps;
import org.qiunet.utils.collection.enums.ForEachResult;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/***
 * 地图的区域(格子)
 */
public class MapRegion {
    private static final Logger logger = GameLogger.COMM_LOGGER.getLogger();
	// id -> 地图对象
	final Map<Long, VisibleObject> data = Maps.newConcurrentMap();
	/***
	 * 感兴趣的region id list
	 */
	protected final Set<Long> interestRegionIdList = new TreeSet<>();

	/**
	 * id
	 */
	protected final long regionId;

	protected final int x, z;

    // 场景，地图实例
    private final SceneInstance sceneInstance;

    public MapRegion(SceneInstance sceneInstance, long regionId) {
		int viewRegionGridSize = sceneInstance.getViewRegionGridSize();
		this.sceneInstance = sceneInstance;
		this.x = MapUtil.calX(regionId);
		this.z = MapUtil.calZ(regionId);
		this.regionId = regionId;

		for (int xId = x - viewRegionGridSize; xId <= x + viewRegionGridSize; xId++) {
			for (int zId = z - viewRegionGridSize; zId <= z + viewRegionGridSize; zId++) {
				interestRegionIdList.add(MapUtil.newBuildRegionId(xId, zId));
			}
		}
    }

    public void addObj(VisibleObject obj) {
        if (logger.isInfoEnabled() && obj.isPlayer()) {
            logger.info("==Object id[{}] enter scene id [{}] region id[{}]", obj.getObjectId(), sceneInstance.getSceneId(), regionId);
        }
        data.put(obj.getObjectId(), obj);
    }

    public void removeObj(long objectId) {
        if (logger.isInfoEnabled() && objectId > 0) {
            logger.info("==Object id[{}] leave scene id [{}] region id[{}]", objectId, sceneInstance.getSceneId(), regionId);
        }
        data.remove(objectId);
    }

    // 是否已经存在该区
    public boolean exist(long objectId) {
        return data.containsKey(objectId);
    }

    public void  walk(Consumer<VisibleObject> consumer){
        data.values().forEach(consumer);
    }

	/**
	 * 消费感兴趣的MapRegion
	 * @param consumer
	 */
	public void consumeInterestRegion(Consumer<MapRegion> consumer) {
		for (Long id : this.interestRegionIdList) {
			MapRegion mapRegion = this.getSceneInstance().returnRegion(id);
			if (mapRegion != null) {
				consumer.accept(mapRegion);
			}
		}
	}

    public void walkMObject(Predicate<VisibleObject> filter, Function<VisibleObject, ForEachResult> consume) {
        for (VisibleObject object : data.values()) {
            if (filter != null && filter.test(object)) {
                continue;
            }
            ForEachResult apply = consume.apply(object);
            if (apply == ForEachResult.BREAK) {
                break;
            }
        }
    }

    public long getRegionId() {
        return regionId;
    }

    public SceneInstance getSceneInstance() {
        return sceneInstance;
    }

    @Override
    public String toString() {
        return "{" + "sceneId='" + sceneInstance.getSceneId() + '\'' + "regionId='" + regionId + '\'' + '}';
    }
}
