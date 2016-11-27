package org.plume.demo.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

/**
 * The global static instance of the application context
 */
public class Factory {
	
	private final static Injector injector = Guice.createInjector(
		Stage.PRODUCTION,
		new ApplicationModule()
	);
	
	public final static Injector injector() {
		return injector;
	}

}
