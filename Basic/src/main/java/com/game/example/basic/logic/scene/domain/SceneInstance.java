package com.game.example.basic.logic.scene.domain;

import com.game.example.basic.logic.scene.object.MObject;
import com.game.example.basic.logic.scene.object.Player;
import com.game.example.basic.logic.scene.object.VisibleObject;
import com.game.example.basic.logic.scene.utils.MapUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.qiunet.flash.handler.common.MessageHandler;
import org.qiunet.utils.collection.enums.ForEachResult;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
		this(sceneId, DEFAULT_VIEW_REGION_GRID_SIZE, DEFAULT_REGION_GRID_SIZE);
    }

    public SceneInstance(String sceneId, int viewRegionGridSize, int regionGridSize) {
		super(sceneId);
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
    public String calRegionId(float x, float y) {
        int xID = (int) Math.floor(x / this.regionGridSize);
        int ySize = (int) Math.floor(y / this.regionGridSize);
        return MapUtil.buildRegionId(xID, ySize);
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

    /**
     * 玩家动作广播
     */
    public void playerActionBroadcast() {
        // final Map<Long, List<SyncActionInfoData>> map = new HashMap<>(64);
        final Map<Long, List<Integer>> map = new HashMap<>(64);
        this.walkMObject(null, obj -> {
            // List list = obj.refreshAndGetActionInfoList(); // 玩家线程加入动作和位置信息，这里获取时会更换引用
            List list = new LinkedList();
            if (list.isEmpty()) {
                return ForEachResult.CONTINUE;
            }
            map.put(obj.getObjectId(), list);
            return ForEachResult.CONTINUE;
        });
        if (map.isEmpty()) {
            return;
        }

        this.walkMObject(MObject::isPlayer, p -> {
            p.addMessage(p0 -> {
                Player player = (Player) p0;
                // List<SyncActionInfoData> list = Lists.newLinkedList();
                // 将自己的动作和视野内对象的动作一起下发
                List<Integer> list = Lists.newLinkedList();
                if (map.containsKey(player.getId())) {
                    list.addAll(map.get(player.getObjectId()));
                }
                (player).getKnowList().walk(o -> {
                    // List<SyncActionInfoData> dataList = map.get(o.getObjectId());
                    List<Integer> dataList = map.get(o.getObjectId());
                    if (dataList != null) {
                        list.addAll(dataList);
                    }
                });
                if (list.isEmpty()) {
                    return;
                }

                // list.sort((o1, o2) -> ComparisonChain.start().compare(o1.getDt(), o2.getDt()).result());
                // SyncActionPush syncActionPush = SyncActionPush.valueOf(list);
                if (player.isKcpSessionPrepare()) {
                    // player.sendKcpMessage(syncActionPush, true);
                }else {
                    // player.sendMessage(syncActionPush, true);
                }
            });
            return ForEachResult.CONTINUE;
        });
    }

    public void walkMObject(Predicate<VisibleObject> filter, Function<VisibleObject, ForEachResult> consume) {
        b:
        for (MapRegion region : regions.values()) {
            for (VisibleObject object : region.data.values()) {
                if (filter != null && !filter.test(object)) {
                    continue;
                }
                ForEachResult result = consume.apply(object);
                if (result == ForEachResult.BREAK) {
                    break b;
                }
            }
        }
    }

    @Override
    public String msgExecuteIndex() {
        return sceneId;
    }

    public String getSceneId() {
        return sceneId;
    }
}
