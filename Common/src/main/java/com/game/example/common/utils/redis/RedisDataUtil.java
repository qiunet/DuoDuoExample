package com.game.example.common.utils.redis;

import org.qiunet.data.core.support.redis.BasePoolRedisUtil;
import org.qiunet.data.core.support.redis.IJedis;
import org.qiunet.data.core.support.redis.IRedisCaller;
import org.qiunet.data.util.ServerConfig;
import org.qiunet.utils.json.JsonUtil;
import org.qiunet.utils.string.StringUtil;

/***
 * redis 工具类
 **/
public final class RedisDataUtil extends BasePoolRedisUtil {
	private static final RedisDataUtil instance = new RedisDataUtil();

	protected RedisDataUtil() {
		super(ServerConfig.getInstance(), "data");
	}

	public static RedisDataUtil getInstance() {
		return instance;
	}

    /**
     * 获得一个str对应的对象.
	 * @param key
     * @param clazz
     * @param <T>
     * @return
     */
	public static <T> T getObject(String key, Class<T> clazz) {
		return executorCommands(jedis -> {
			String json = jedis().get(key);
			if (StringUtil.isEmpty(json)) {
				return null;
			}
			return JsonUtil.getGeneralObj(json, clazz);
		});
	}

	public static IJedis jedis(){
		return instance.returnJedis();
	}

	public static IJedis nonLogJedis(){
		return instance.returnJedis(false);
	}

	public static <T> T executorCommands(IRedisCaller<T> caller) {
		return instance.execCommands(caller);
	}
}
