package com.game.example.basic.logic.pack;

import com.game.example.basic.logic.pack.entity.PackBo;
import com.game.example.basic.logic.pack.entity.PackDo;
import org.qiunet.data.support.DbDataListSupport;
import org.qiunet.data.db.loader.DataLoader;
import java.util.Map;

public enum PackService {
	instance;
	@DataLoader(PackBo.class)
	private final DbDataListSupport<Long, Integer, PackDo, PackBo> dataSupport = new DbDataListSupport<>(PackDo.class, PackBo::new);
}
