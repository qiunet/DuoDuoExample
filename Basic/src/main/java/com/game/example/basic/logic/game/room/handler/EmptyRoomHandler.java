package com.game.example.basic.logic.game.room.handler;

import com.game.example.basic.logic.game.room.Room;
import org.qiunet.utils.logger.LoggerType;
import org.slf4j.Logger;

/**
 * 空房间
 */
public class EmptyRoomHandler extends BaseRoomHandler {
    private static final Logger logger = LoggerType.DUODUO_CROSS.getLogger();

    public EmptyRoomHandler(String sceneId) {
        super(sceneId);
    }

    @Override
    public void initialize(Room room) {
        logger.info("初始化空房间！");
    }

    @Override
    public void frameLogic(Room room, float ptf) {

    }
}
