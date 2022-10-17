package com.game.example.basic.logic.scene.domain;

import com.game.example.basic.logic.scene.object.Player;
import com.game.example.basic.logic.scene.object.VisibleObject;
import com.game.example.basic.logic.scene.observer.IObjectPositionChange;
import com.game.example.basic.logic.scene.observer.IPlayerSceneChange;
import com.game.example.basic.logic.scene.observer.IPlayerSceneOffline;
import com.game.example.basic.logic.scene.observer.IPlayerSceneOnline;
import com.game.example.basic.logic.scene.proto.SceneChangePush;
import com.game.example.basic.logic.scene.proto.SceneOfflinePush;
import com.game.example.common.logger.GameLogger;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 地图中的位置
 */
public class Position<Owner extends VisibleObject<Owner>> {
    private static final Logger logger = GameLogger.COMM_LOGGER.getLogger();

    private final AtomicBoolean online = new AtomicBoolean();
    /**
     * 所在区域
     */
    private MapRegion mapRegion;
    // 所属的对象
    private final Owner owner;

    private Coordinate currPos;

    public Position(Owner owner) {
        this.owner = owner;
        currPos = new Coordinate();
    }

    public synchronized void update(float x, float y, float dir) {
        MapRegion preRegion = this.mapRegion;
        currPos.set(x, y, dir);
        this.mapRegion = this.getSceneInstance().returnRegion(x, y);
        if (preRegion != mapRegion) {
            preRegion.removeObj(owner.getObjectId());
            mapRegion.addObj(owner);

            owner.getKnowList().update();
        }
        owner.syncFireObserver(IObjectPositionChange.class, i -> i.change(owner));
    }

    // 离线
    public void offline() {
        if (!this.online.compareAndSet(true, false)) {
            return;
        }

        logger.info("Player [{}] offline from scene [{}]", owner.getObjectId(), mapRegion.getSceneInstance().getSceneId());
        if (owner.isPlayer()) {
            owner.syncFireObserver(IPlayerSceneOffline.class, i -> i.offline((Player) owner));
            ((Player) this.owner).sendMessage(SceneOfflinePush.valueOf(), true);
        }

        mapRegion.removeObj(owner.getObjectId());
        // 离线只通知别的玩家的视野中我消失了，自己的视野直接清空
        owner.getKnowList().walk(obj -> obj.getBehaviorController().notSee(owner));
        owner.getKnowList().clean();
    }

    // 上线
    public void online() {
        if (mapRegion != null && mapRegion.exist(owner.getObjectId())) {
            return;
        }
        if (! this.online.compareAndSet(false, true)) {
            return;
        }
        this.online0();
    }

    private void online0() {
        if (owner.isPlayer()) {
            owner.syncFireObserver(IPlayerSceneChange.class, i -> i.change((Player) owner, mapRegion.getSceneInstance().getSceneId()));
            ((Player) owner).sendMessage(SceneChangePush.valueOf(mapRegion.getSceneInstance().getSceneId(), currPos));
        }

        logger.info("Player {} online in scene [{}]", owner.getObjectId(), mapRegion.getSceneInstance().getSceneId());
        getCurrRegion().addObj(owner);
        owner.getKnowList().update();
        if (owner.isPlayer()) {
            owner.syncFireObserver(IPlayerSceneOnline.class, i -> i.online((Player) owner));
        }
    }

    /**
     * 在指定场景上线
     */
    public void onlineInSceneInstance(SceneInstance sceneInstance, Coordinate birthCoordinate) {
        if (! this.online.compareAndSet(false, true)) {
            this.offline();
        }
        this.mapRegion = sceneInstance.returnRegion(birthCoordinate.getX(), birthCoordinate.getY());
        this.currPos.set(birthCoordinate.getX(), birthCoordinate.getY(), birthCoordinate.getDir());
        this.online0();
    }

    // 是否在场景地图中
    public boolean isOnline() {
        return this.online.get();
    }

    // 获得现有坐标
    public Coordinate getCurrPos() {
        return currPos;
    }

    // 获得场景实例
    public SceneInstance getSceneInstance() {
        return mapRegion.getSceneInstance();
    }

    // 获得场景ID
    public String getSceneId() {
        return getSceneInstance().getSceneId();
    }

    // 获得当前的地图区块
    public MapRegion getCurrRegion() {
        return this.mapRegion;
    }
}
