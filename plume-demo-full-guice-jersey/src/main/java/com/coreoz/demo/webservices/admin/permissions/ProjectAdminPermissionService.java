package com.coreoz.demo.webservices.admin.permissions;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.coreoz.plume.admin.services.permissions.AdminPermissionService;
import com.coreoz.plume.admin.services.permissions.AdminPermissionServiceBasic;
import com.google.common.base.Throwables;

@Singleton
public class ProjectAdminPermissionService implements AdminPermissionService {

	private final Set<String> permissionsAvailable;

	@Inject
	public ProjectAdminPermissionService(AdminPermissionServiceBasic adminPermissionServiceBasic) {
		this.permissionsAvailable = Stream
				.of(ProjectAdminPermissions.class.getDeclaredFields())
				.map(field -> {
					try {
						return (String) field.get(null);
					} catch (Exception e) {
						throw Throwables.propagate(e);
					}
				})
				.collect(Collectors.toSet());
		this.permissionsAvailable.addAll(adminPermissionServiceBasic.permissionsAvailable());
	}
	@Override
	public Set<String> permissionsAvailable() {
		return permissionsAvailable;
	}

}
