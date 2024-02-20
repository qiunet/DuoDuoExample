package com.game.example.server.stress.testing.action.login;

import com.game.example.basic.http.response.LoginResponse;
import com.game.example.server.stress.testing.action.RobotRequestAction;
import com.google.common.collect.ImmutableMap;
import org.qiunet.data.util.ServerConfig;
import org.qiunet.function.ai.enums.ActionStatus;
import org.qiunet.function.condition.IConditions;
import org.qiunet.game.test.robot.Robot;
import org.qiunet.utils.http.HttpRequest;
import org.qiunet.utils.json.JsonUtil;

// @BehaviorAction(name = "http登录")
public class HttpLoginAction extends RobotRequestAction {

    public HttpLoginAction(IConditions<Robot> preConditions) {
        super(preConditions);
    }

    @Override
    protected ActionStatus execute() {
        HttpRequest.post(ServerConfig.getInstance().getString("server.address"))
                .withJsonData(ImmutableMap.of("uid", getOwner().getAccount()))
                .asyncExecutor((response) -> getRobotData().setHttpLoginResponse(
                        JsonUtil.getGeneralObj(response.body(), LoginResponse.class)));

        return ActionStatus.RUNNING;
    }

    @Override
    protected ActionStatus runningStatusUpdate() {
        return (getRobotData().getHttpLoginResponse() != null) ? ActionStatus.SUCCESS : ActionStatus.RUNNING;
    }
}
