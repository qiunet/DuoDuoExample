package com.game.example.server.handler.player;

import com.game.example.basic.logic.player.PlayerService;
import com.game.example.basic.logic.player.cfg.PlayerCfgManager;
import com.game.example.basic.logic.player.cfg.data.RoleAvatarCfg;
import com.game.example.basic.logic.player.entity.PlayerBo;
import com.game.example.basic.logic.player.proto.RegisterReq;
import com.game.example.basic.logic.player.proto.RegisterRsp;
import com.game.example.common.constants.GameStatus;
import com.game.example.common.data.AvatarInfo;
import com.game.example.common.data.PlayerPlatformData;
import com.game.example.server.common.handler.GameHandler;
import org.qiunet.flash.handler.common.player.PlayerActor;
import org.qiunet.flash.handler.context.request.persistconn.IPersistConnRequest;
import org.qiunet.flash.handler.context.status.StatusResultException;
import org.qiunet.flash.handler.netty.server.config.adapter.message.StatusTipsRsp;

public class RegisterHandler extends GameHandler<RegisterReq> {

    @Override
    public void handler(PlayerActor playerActor, IPersistConnRequest<RegisterReq> context) throws Exception {
        // PlayerPlatformData是在登录服保存在redis中的
        PlayerPlatformData data = PlayerPlatformData.readData(playerActor.getPlayerId());
        if (data == null) {
            playerActor.sendMessage(RegisterRsp.valueOf(false));
            throw StatusResultException.valueOf(GameStatus.LOGIN_TICKET_ERROR);
        }

        PlayerBo playerBo = playerActor.getData(PlayerBo.class);
        if (playerBo != null) {
            throw StatusResultException.valueOf(GameStatus.LOGIN_ALREADY_REGISTER);
        }

        RegisterReq request = context.getRequestData();
        int roleId = PlayerCfgManager.instance.getDefaultRoleId();

        AvatarInfo info = null;
        if (roleId > 0) {
            RoleAvatarCfg roleAvatarCfg = PlayerCfgManager.instance.getRoleAvatarCfg(roleId);
            if (roleAvatarCfg == null) {
                throw StatusResultException.valueOf(GameStatus.AVATAR_ROLE_ABSENT);
            }
            info = AvatarInfo.valueOf(roleId, null);
        }
        register(playerActor, data, info);
    }

    private void register(PlayerActor playerActor, PlayerPlatformData playerPlatformData, AvatarInfo avatarInfo) {
        try {
            PlayerService.instance.doRegister(playerActor, playerPlatformData, avatarInfo);
        } catch (Exception ex) {
            if (ex instanceof StatusResultException) {
                playerActor.sendMessage(StatusTipsRsp.valueOf((StatusResultException) ex));
            } else {
                playerActor.sendMessage(StatusTipsRsp.valueOf(GameStatus.EXCEPTION));
            }
            throw ex;
        }
    }
}
