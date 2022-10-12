package com.game.example.basic.logic.game.room;

import com.game.example.basic.logic.game.room.domain.RoomInfoData;
import com.game.example.basic.logic.game.room.handler.IRoomHandler;
import com.game.example.basic.logic.game.room.observer.IRoomDestroy;
import com.game.example.basic.logic.game.room.observer.IRoomEnterPlayer;
import com.game.example.basic.logic.game.room.observer.IRoomQuitPlayer;
import com.game.example.basic.logic.game.room.player.RoomPlayer;
import com.game.example.basic.logic.game.room.proto.*;
import com.game.example.basic.logic.scene.object.MovableObject;
import com.game.example.basic.logic.scene.object.Player;
import com.game.example.common.constants.GameStatus;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.qiunet.flash.handler.common.observer.IObserverSupportOwner;
import org.qiunet.flash.handler.common.observer.ObserverSupport;
import org.qiunet.flash.handler.common.player.AbstractUserActor;
import org.qiunet.flash.handler.common.player.observer.IPlayerDestroy;
import org.qiunet.flash.handler.context.request.data.IChannelData;
import org.qiunet.utils.args.ArgumentKey;
import org.qiunet.utils.exceptions.CustomException;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Room extends BaseRoom implements IObserverSupportOwner<Room> {
    // 玩家所在的房间 在Player身上的key
    public static final ArgumentKey<Room> ROOM = new ArgumentKey<>();
    /**
     * 监听
     */
    private final ObserverSupport<Room> observerSupport = new ObserverSupport<>(this);
    /**
     * 场景内玩家的列表
     */
    private final Map<Long, RoomPlayer> players = Maps.newConcurrentMap();
    /**
     * 房间的信息
     */
    private final RoomInfoData roomInfoData;

    public Room(IRoomHandler roomHandler, long roomId, String sceneId, boolean privacy) {
        super(roomHandler, roomId);
        (roomInfoData = new RoomInfoData(this, sceneId, privacy)).sendToRedis();
    }

    public static Room get(Player player) {
        return player.getVal(ROOM);
    }

    //进入场景
    public void enter(Player player) {
        if (this.players.containsKey(player.getId())) {
            throw new CustomException("Room[{}] Player[{}] already exists", roomId, player.getId());
        }

        this.addMessage(r -> {
            if(isDestroyed()){
                return;
            }
            try {
                // 绑定player
                ROOM.set(player, this);
                RoomPlayer roomPlayer = addPlayer(player);

                // 监听playerActor销毁事件
                ObserverSupport<? extends AbstractUserActor> observerSupport = player.getUserActor().getObserverSupport();
                observerSupport.attach(IPlayerDestroy.class, (p) -> this.playerDestroy(player));
                roomPlayer.sendMessage(RoomInfoPush.valueOf(getSceneId(), this.buildRoomData()));
            }catch (Exception e0 ) {
                this.quit0(player, true);
                player.sendMessage(JoinRoomFailRsp.valueOf(GameStatus.ROOM_JOIN_FAIL));
            }
        });
    }

    private RoomPlayer addPlayer(MovableObject player){
        RoomPlayer roomPlayer = new RoomPlayer(player);
        this.players.putIfAbsent(player.getId(), roomPlayer);

        this.roomInfoData.alterPlayerNum(1);

        this.broadcast(RoomPlayerEnterPush.valueOf(RoomPlayerTo.valueOf(roomPlayer)), player0 -> player0.isNotSelf(player.getId()));
        this.observerSupport.syncFire(IRoomEnterPlayer.class, i -> i.enter(this, roomPlayer));
        return roomPlayer;
    }

    private void playerDestroy(Player player){
        if (this.isDestroyed()){
            return;
        }
        this.addMessage(h -> h.quit0(player, false));
    }

    public RoomData buildRoomData(){
        List<RoomPlayerTo> playerDataList = Lists.newArrayListWithCapacity(this.playerSize());
        this.walkPlayer(player -> playerDataList.add(RoomPlayerTo.valueOf(player)));
        return RoomData.valueOf(roomId, playerDataList);
    }

    //退出场景
    public boolean quit(AbstractUserActor userActor) {
        Player player = userActor.getVal(Player.PLAYER_IN_ACTOR_KEY);
        if (!this.players.containsKey(player.getId())) {
            return false;
        }
        this.addMessage(r -> quit0(player, true));
        return true;
    }

    private void quit0(Player player, boolean needCallPlayerQuit) {
        RoomPlayer roomPlayer = this.players.remove(player.getId());
        if (roomPlayer == null) {
            return;
        }

        RoomPlayerQuitPush leaveRoomPush = RoomPlayerQuitPush.valueOf(player.getId());
        player.sendMessage(RoomQuitRsp.valueOf(true), true);
        if (needCallPlayerQuit) {
            player.getUserActor().logout();
        }
        ROOM.remove(player);
        this.roomInfoData.alterPlayerNum(-1);
        this.broadcast(leaveRoomPush, p -> p.isNotSelf(player.getId()));
        this.observerSupport.syncFire(IRoomQuitPlayer.class, i -> i.quit(this, roomPlayer));
        // 房间销毁
        if (players.isEmpty()) {
            this.destroy();
        }
    }

    public void broadcast(IChannelData channelData, Predicate<RoomPlayer> filter) {
        this.walkPlayer(player -> player.sendMessage(channelData), filter);
    }

    public void walkPlayer(Consumer<RoomPlayer> consumer) {
        this.walkPlayer(consumer, null);
    }

    public void walkPlayer(Consumer<RoomPlayer> consumer, Predicate<RoomPlayer> filter) {
        for (RoomPlayer player : players.values()) {
            if (filter != null && ! filter.test(player)) {
                continue;
            }
            consumer.accept(player);
        }
    }

    @Override
    public void destroy() {
        if (isDestroyed()) {
            return;
        }
        super.destroy();
        this.roomInfoData.remove();
        this.walkPlayer(actor -> actor.sendMessage(RoomDestroyPush.valueOf(), true));
        this.players.clear();
        observerSupport.syncFire(IRoomDestroy.class, o -> o.destroy(this));
    }

    //当前房间用户数
    public int playerSize(){
        return players.size();
    }

    public String getSceneId() {
        return roomInfoData.getSceneId();
    }

    public RoomInfoData getRoomInfoData() {
        return roomInfoData;
    }

    @Override
    public String msgExecuteIndex() {
        return String.valueOf(roomId);
    }

    @Override
    public ObserverSupport<Room> getObserverSupport() {
        return observerSupport;
    }
}
