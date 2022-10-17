package com.game.example.basic.logic.scene.domain;

import com.game.example.basic.logic.scene.object.VisibleObject;
import com.game.example.common.logger.GameLogger;
import com.google.common.collect.Maps;
import org.qiunet.utils.collection.enums.ForEachResult;
import org.slf4j.Logger;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/***
 * 地图的区域(格子)
 */
public class MapRegion {
    private static final Logger logger = GameLogger.COMM_LOGGER.getLogger();

    private final String regionId;

    // id -> 地图对象
    final Map<Long, VisibleObject> data = Maps.newConcurrentMap();

    // 场景，地图实例
    private final SceneInstance sceneInstance;

    public MapRegion(SceneInstance sceneInstance, String regionId) {
        this.sceneInstance = sceneInstance;
        this.regionId = regionId;
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

    public String getRegionId() {
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
