
dataSource {
	pooled = true
	// driverClassName = "org.hsqldb.jdbcDriver"
	driverClassName = "org.postgresql.Driver"
	// username = "sa"
	username = "postgres"
	password = ""
	// password = ""
	logSql=false
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:postgresql:quoddy_dev";
			// dbCreate = "create-drop"
			// url = "jdbc:hsqldb:mem:devDb
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:hsqldb:mem:quoddy_test"
		}
	}
	production {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:postgresql:quoddy"
		}
	}
}