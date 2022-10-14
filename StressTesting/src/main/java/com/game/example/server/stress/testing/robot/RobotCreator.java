package com.game.example.server.stress.testing.robot;

import org.qiunet.data.util.ServerConfig;
import org.qiunet.game.test.robot.creator.IRobotAccountFactory;
import org.qiunet.utils.id.DefaultIdGenerator;

public enum RobotCreator implements IRobotAccountFactory {
    instance;

    private static DefaultIdGenerator idGenerator = new DefaultIdGenerator(ServerConfig.instance.getLong("robot.id"));

    @Override
    public String newAccount() {
        return idGenerator.makeId().toString();
    }
}
