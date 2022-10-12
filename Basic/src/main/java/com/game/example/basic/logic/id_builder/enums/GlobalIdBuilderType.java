package com.game.example.basic.logic.id_builder.enums;

import com.game.example.basic.logic.id_builder.global.entity.GlobalIdBuilderBo;
import com.game.example.basic.logic.id_builder.global.entity.GlobalIdBuilderDo;
import com.game.example.common.utils.redis.RedisDataUtil;
import org.qiunet.data.support.DbDataSupport;
import org.qiunet.data.util.ServerConfig;
import org.qiunet.utils.async.LazyLoader;
import org.qiunet.utils.exceptions.CustomException;

import java.io.IOException;

/***
 * 全局id生成类型
 */
public enum GlobalIdBuilderType {
	/**
	 * 房间ID
	 */
	ROOM(1) {
		@Override
		protected String buildRedisKey() {
			return redis_key_prefix + name() + "#" + ServerConfig.getServerGroupId();
		}

		@Override
		boolean prepare() throws IOException {
			String redisKey = this.redisKey.get();
			// 如果不能创建. 会抛出异常异常
			if (! RedisDataUtil.jedis().exists(redisKey)) {
				return RedisDataUtil.getInstance().redisLockRun(redisKey, () -> {
					if (RedisDataUtil.jedis().exists(redisKey)) {
						return true;
					}
					GlobalIdBuilderBo bo = dataSupport.getBo(getType());
					if (bo == null) {
						bo = dataSupport.insert(new GlobalIdBuilderDo(getType(), 0));
					}
					 RedisDataUtil.jedis().set(redisKey, String.valueOf(bo.getDo().getId_val()));
					return true;
				});
			}
			return true;
		}

		/**
		 * groupId 占位
		 */
		private static final int groupIdHolder = 10000;
		private static final long maxVal = Long.MAX_VALUE / groupIdHolder;
		@Override
		long makeId() {
			Long val = RedisDataUtil.jedis().incr(redisKey.get());
			if (val > maxVal) {
				// 从头再来. room id无所谓
				RedisDataUtil.jedis().set(redisKey.get(), "0");
			}

			return val * groupIdHolder + ServerConfig.getServerGroupId();
		}

		@Override
		void finish(long id) {
			GlobalIdBuilderBo bo = dataSupport.getBo(getType());
			bo.getDo().setId_val(id / groupIdHolder);
			bo.update();
		}
	},
	/**
	 * 场景对象id
	 */
	SCENE_OBJECT(2) {
		@Override
		boolean prepare() {
			return true;
		}

		@Override
		long makeId() {
			return RedisDataUtil.jedis().decr(redisKey.get());
		}

		@Override
		void finish(long id) {}
	},
	;
	private static final DbDataSupport<Integer, GlobalIdBuilderDo, GlobalIdBuilderBo> dataSupport = new DbDataSupport<>(GlobalIdBuilderDo.class, GlobalIdBuilderBo::new);
	private static final String redis_key_prefix = "GLOBAL_ID_BUILDER#";
	private final int type;

	GlobalIdBuilderType(int type) {
		this.type = type;
	}

	protected final LazyLoader<String> redisKey = new LazyLoader<>(this::buildRedisKey);

	/**
	 * redis key
	 */
	protected String buildRedisKey(){
		return redis_key_prefix + name();
	}

	/**
	 * 准备
	 */
	abstract boolean prepare() throws IOException;

	/**
	 * 生成ID
	 */
	abstract long makeId();
	/**
	 * 结束
	 */
	abstract void finish(long id);

	/**
	 * 生成一个ID
	 */
	public long generateId() {
		try {
			if (this.prepare()) {
				long id = this.makeId();
				this.finish(id);
				return id;
			}
		}catch (IOException e) {
			throw new CustomException("redis lock fail for type: {}!", getType());
		}
		throw new CustomException("build id type: {} error!", getType());
	}

	public int getType() {
		return type;
	}
}
