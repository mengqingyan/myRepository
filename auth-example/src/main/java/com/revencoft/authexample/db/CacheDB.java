/**
 * 
 */
package com.revencoft.authexample.db;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.revencoft.authexample.vo.User;
import com.revencoft.validation.cache.Cache;
import com.revencoft.validation.cache.CachesHolder;

/**
 * 缓存简单模拟数据库
 * @author mengqingyan
 * @version 
 */
@Component
public class CacheDB {
	/**
	 * 模拟存储用户数据
	 * id---user
	 */
	public Cache<Integer, User> userDB = CachesHolder.getInstance().getNamedCache(getClass().getName()+".userDB");
	
	/**
	 * 权限信息
	 * id---角色名
	 */
	public Cache<Integer, String> auth = CachesHolder.getInstance().getNamedCache(getClass().getName()+".auth");
	
	/**
	 * 用户--权限信息
	 * id---角色id
	 */
	public Cache<Integer, UserAndAuth> userAuth = CachesHolder.getInstance().getNamedCache(getClass().getName()+".userAuth");
	
	/**
	 * 资源信息，正则字符串
	 */
	public Cache<Integer, String> resource =  CachesHolder.getInstance().getNamedCache(getClass().getName()+".resource");
	
	
	/**
	 * 资源和对应的角色
	 */
	public Cache<Integer, ResourceAndAuth> resourceAuth =  CachesHolder.getInstance().getNamedCache(getClass().getName()+".resourceAuth");
	
	@PostConstruct
	public void initDB() {
		/**
		 * 初始化用户
		 */
		userDB.put(1, new User(1, "adminUser", "admin123"));
		userDB.put(2, new User(2, "generalUser", "user123"));
		userDB.put(3, new User(3, "managerUser", "manager123"));
		userDB.put(4, new User(4, "mqyTest", "mqytest"));
		/**
		 * 初始化角色信息
		 */
		auth.put(1, "admin");
		auth.put(2, "manager");
		auth.put(3, "user");
		
		
		/**
		 * 初始化资源信息，正则字符串
		 */
		resource.put(1, "/user_accessTest\\.php$");
		
		
		/**
		 * 关联用户--角色
		 */
		userAuth.put(1, new UserAndAuth(1, 1));
		userAuth.put(2, new UserAndAuth(2, 3));
		userAuth.put(3, new UserAndAuth(3, 1));
		userAuth.put(4, new UserAndAuth(3, 2));
		userAuth.put(5, new UserAndAuth(3, 3));
		userAuth.put(6, new UserAndAuth(4, 3));
		
		
		/**
		 * 关联资源--角色
		 */
		resourceAuth.put(1, new ResourceAndAuth(1, 1));
		resourceAuth.put(2, new ResourceAndAuth(1, 2));
		
	}
	
	public final class UserAndAuth {
		private final Integer userId;
		private final Integer roleId;
		/**
		 * @param userId
		 * @param roleId
		 */
		public UserAndAuth(Integer userId, Integer roleId) {
			super();
			this.userId = userId;
			this.roleId = roleId;
		}
		public Integer getUserId() {
			return userId;
		}
		public Integer getRoleId() {
			return roleId;
		}
		
	}
	
	public final class ResourceAndAuth {
		private final Integer uriId;
		private final Integer roleId;
		/**
		 * @param uri
		 * @param roleId
		 */
		public ResourceAndAuth(Integer uriId, Integer roleId) {
			super();
			this.uriId = uriId;
			this.roleId = roleId;
		}
		public Integer getUriId() {
			return uriId;
		}
		public Integer getRoleId() {
			return roleId;
		}
	}
}
