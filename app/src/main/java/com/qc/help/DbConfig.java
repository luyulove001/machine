package com.qc.help;

import com.lidroid.xutils.DbUtils;
import com.qc.application.App;

public class DbConfig {
	private static DbConfig instance;
	private static DbUtils db;

	public static DbConfig getInstance() {
		if (instance == null) {
			instance = new DbConfig();
			db = DbUtils.create(App.getInstance().getApplicationContext(),
					"qchouse.db");
		}
		return instance;
	}

	public DbUtils getDb() {
		return db;
	}

}
